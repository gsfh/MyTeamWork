package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.FirstDetailActivity;
import com.gsfh.myteamwork.vmovie.activity.SecondDetailActivity;
import com.gsfh.myteamwork.vmovie.adapter.LatestAdapter;
import com.gsfh.myteamwork.vmovie.bean.LatestBean;
import com.gsfh.myteamwork.vmovie.bean.MainBannerBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GSFH on 2016-7-13.
 */
public class MainFragment extends Fragment {

    private ConvenientBanner convenientBanner;
    private List<MainBannerBean.DataBean> bannerDataList = new ArrayList<>();
    private List<String> bannerUrlList = new ArrayList<>();
    private List<LatestBean.DataBean> latestList = new ArrayList<>();
    private PullToRefreshExpandableListView listView;
    private ExpandableListView reFreshListView;
    private ArrayList<String> dateList = new ArrayList<>();
    private Map<String,List<LatestBean.DataBean>> map = new LinkedHashMap<>();
    private LatestAdapter latestAdapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,null);
        listView = (PullToRefreshExpandableListView) view.findViewById(R.id.pe_lv_main);
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        View bannerView = inflater.inflate(R.layout.main_header_view,null);
        convenientBanner = (ConvenientBanner) bannerView.findViewById(R.id.convenientBanner);

        reFreshListView = listView.getRefreshableView();

//        reFreshListView.addHeaderView(bannerView);

        initAdapter();
        initData(1);
        initLinstener();

        return view;
    }

    private void initAdapter() {

        latestAdapter = new LatestAdapter(getContext(),map,dateList);
        reFreshListView.setAdapter(latestAdapter);

//        设置ExpandableListView点击不收缩
        reFreshListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    private void initData(Integer page) {

        /**
         * 列表数据的网络请求
         */
        String p = page.toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("p", p);
        paramMap.put("size","20");
        paramMap.put("tab","latest");

        OkHttpTool.newInstance().post(paramMap).start(URLConstants.LATEST_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {

                if (null == result){
                    return;
                }

                Gson gson = new Gson();
                LatestBean latestBean = gson.fromJson(result,LatestBean.class);
                latestList.clear();
                latestList.addAll(latestBean.getData());

                long firstTime = latestList.get(0).getPublish_time();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                String firstDate = sdf.format(new Date(firstTime*1000));
                //添加第一条日期到日期列表
                dateList.add(firstDate);
                //创建第一个内容列表
                List<LatestBean.DataBean> childList = new ArrayList<>();
                //添加第一条数据到map
                map.put(firstDate,childList);
                //遍历所有数据
                for (int i = 0; i < latestList.size(); i++) {

                    LatestBean.DataBean bean = latestList.get(i);
                    long time = bean.getPublish_time();
                    String date = sdf.format(new Date(time*1000));

                    if (date.equals(firstDate)){
                        //添加一个日期内的内容
                        childList.add(bean);
                    }else {
                        //不同日期则创建一个新的内容列表
                        childList = new ArrayList<>();
                        //添加新的数据到map中
                        map.put(date,childList);
                        //添加新的内容数据
                        childList.add(bean);
                        //添加新的日期到日期列表
                        dateList.add(date);
                        firstDate = date;
                    }

                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        latestAdapter.notifyDataSetChanged();
                        //默认所有的Group全部展开
                        for (int i = 0; i < dateList.size(); i++) {
                            reFreshListView.expandGroup(i);
                        }
                    }
                });

            }
        });

        /**
         * 头部视图的网络数据请求
         */
        OkHttpTool.newInstance().start(URLConstants.LATEST_BANNER_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {

                if (null == result){
                    return;
                }

                Gson gson = new Gson();
                MainBannerBean bannerBean = gson.fromJson(result,MainBannerBean.class);
                bannerDataList.addAll(bannerBean.getData());

                for (MainBannerBean.DataBean data : bannerDataList) {

                    bannerUrlList.add(data.getImage());
                }

                initBanner();
            }
        });
    }

    private void initBanner() {

        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {

            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        },bannerUrlList)
                .setPageIndicator(new int[]{R.drawable.main_header_dot_n,R.drawable.main_header_dot_s})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

    }

    public class LocalImageHolderView implements Holder<Integer> {


        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }

    }

    private void initLinstener() {

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

                listView.onRefreshComplete();
                dateList.clear();
                map.clear();
                initData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

                page ++;
                initData(page);
                listView.onRefreshComplete();
            }
        });


        reFreshListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String postid = latestList.get(childPosition).getPostid();
                String cateId = latestList.get(childPosition).getCates().get(0).getCateid();

                Intent intent = new Intent();

                if("6".equals(cateId)){

                    intent.setClass(getActivity(), SecondDetailActivity.class);
                }else {

                    intent.setClass(getActivity(), FirstDetailActivity.class);
                }

                intent.putExtra("id",postid);
                intent.putExtra("from","latest");

                startActivity(intent);

                return true;
            }
        });
    }

}
