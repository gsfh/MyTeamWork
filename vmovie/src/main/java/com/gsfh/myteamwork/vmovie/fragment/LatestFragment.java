package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.FirstDetailActivity;
import com.gsfh.myteamwork.vmovie.adapter.LatestAdapter;
import com.gsfh.myteamwork.vmovie.bean.LatestBean;
import com.gsfh.myteamwork.vmovie.bean.MainBannerBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.squareup.picasso.Picasso;

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
public class LatestFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ExpandableListView expListView;
    private ConvenientBanner convenientBanner;
    private List<MainBannerBean.DataBean> bannerDataList = new ArrayList<>();
    private List<String> bannerUrlList = new ArrayList<>();
    private Map<String,List<LatestBean.DataBean>> map = new LinkedHashMap<>();
    private List<LatestBean.DataBean> latestList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private LatestAdapter latestAdapter;
    private int page = 1;
    private boolean isToButtom = false;
    private TextView loadMore;
    private static TabLayout mTabLayout;

    public static LatestFragment newInstance(TabLayout tabLayout) {

        mTabLayout = tabLayout;
        LatestFragment fragment = new LatestFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latest,null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.latest_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        expListView = (ExpandableListView) view.findViewById(R.id.latest_expandable_lv_);

        View bannerView = inflater.inflate(R.layout.main_header_view,null);
        convenientBanner = (ConvenientBanner) bannerView.findViewById(R.id.convenientBanner);
        View footerView = inflater.inflate(R.layout.latest_footer_view,null);
        loadMore = (TextView) footerView.findViewById(R.id.latest_footer_text);

        expListView.addHeaderView(bannerView);
        expListView.addFooterView(footerView);

        initAdapter();
        initData(1);
        initLinstener();

        return view;
    }

    private void initAdapter() {

        latestAdapter = new LatestAdapter(getContext(),map,dateList);
        expListView.setAdapter(latestAdapter);

//        设置ExpandableListView点击不收缩
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    private void initData(Integer page) {

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

                bannerDataList.clear();
                bannerDataList.addAll(bannerBean.getData());

                bannerUrlList.clear();
                for (MainBannerBean.DataBean data : bannerDataList) {
                    bannerUrlList.add(data.getImage());
                }

                initBanner();
            }
        });

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
                latestList.addAll(latestBean.getData());

                long firstTime = latestList.get(0).getPublish_time();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                String firstDate = sdf.format(new Date(firstTime*1000));
                //添加第一条日期到日期列表
                dateList.clear();
                dateList.add(firstDate);
                //创建第一个内容列表
                List<LatestBean.DataBean> childList = new ArrayList<>();
                //添加第一条数据到map
                map.clear();
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
                            expListView.expandGroup(i);
                        }
                    }
                });

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
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

    }

    public class LocalImageHolderView implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {

            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {

            Picasso.with(context).load(data).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String postid = bannerDataList.get(position).getExtra_data().getApp_banner_param();

                    Intent intent = new Intent(getActivity(),FirstDetailActivity.class);

                    intent.putExtra("id",postid);

                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始自动滚动
        convenientBanner.startTurning(4000);
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止滚动
        convenientBanner.stopTurning();
    }

    private void initLinstener() {

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                latestList.clear();
                initData(1);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //上拉加载
        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE && isToButtom){

                    page ++;
                    initData(page);
                }
            }
            int count = 0;
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                isToButtom = totalItemCount == view.getLastVisiblePosition() + 1;

                int position = firstVisibleItem;
                for (int i = 0; i < dateList.size(); i++) {

                    String groupName = dateList.get(i);

                    count += map.get(groupName).size();
                    position -= ( count+ 1);
                    if (position < 0){

                        String numDate = dateList.get(i);
                        String numMounth = numDate.substring(0,2);
                        String numDay = numDate.substring(3,5);
                        String mounth = "Jan.";
                        switch (numMounth){
                            case "01":
                                mounth = "Jan.";
                                break;
                            case "02":
                                mounth = "Feb.";
                                break;
                            case "03":
                                mounth = "Mar.";
                                break;
                            case "04":
                                mounth = "Apr.";
                                break;
                            case "05":
                                mounth = "May.";
                                break;
                            case "06":
                                mounth = "Jun.";
                                break;
                            case "07":
                                mounth = "Jul.";
                                break;
                            case "08":
                                mounth = "Aug.";
                                break;
                            case "09":
                                mounth = "Sept.";
                                break;
                            case "10":
                                mounth = "Oct.";
                                break;
                            case "11":
                                mounth = "Nov.";
                                break;
                            case "12":
                                mounth = "Dec.";
                                break;
                        }
//                        mTabLayout.getTabAt(0).setText(mounth+numDay);
                    }
                }
            }
        });


        //点击跳转
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String postid = map.get(dateList.get(groupPosition)).get(childPosition).getPostid();

                Intent intent = new Intent(getActivity(), FirstDetailActivity.class);

                intent.putExtra("id",postid);

                startActivity(intent);

                return true;
            }
        });


    }

}
