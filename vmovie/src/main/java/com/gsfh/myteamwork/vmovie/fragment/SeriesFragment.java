package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.ChannelSort_HotActivity;
import com.gsfh.myteamwork.vmovie.adapter.SeriesLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.SeriesBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ 董传亮
 * 系列 页面展示 listView
 * Created by admin on 2016/7/13.
 */
public class SeriesFragment extends Fragment {
    //需要数据
    private Context mContext;

    private List<SeriesBean.DataBean> mList = new ArrayList<>();
    ;
    //需求控件
    private ListView mListView;
    private SeriesLvAdapter mLVAdapter;
    //刷新
    private SwipeRefreshLayout refresh_layout = null;//刷新控件
    private TextView textrefrush;
    private int pageIndex = 0;
    private String cateid;

    /**
     * 董传亮 静态工厂
     *
     * @param args
     * @return
     */
    public static SeriesFragment newInstance(Bundle args) {

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();//获取上下文

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, null);//展示listview
        //初始化数据
        initData();
        //初始化控件
        initView(view);

        //绑定适配器
        bindAdapter();
        //刷新监听
        initListener();
        return view;
    }

    /**
     * @ 董传亮
     * 监听事件
     */
    private void initListener() {
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_layout.setRefreshing(true);
                initData();
                pageIndex = 0;


            }
        });
    }

    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mLVAdapter = new SeriesLvAdapter(mContext, mList, getActivity());
        mListView.addFooterView(getfootView());
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getCount() - 1) {
                    // 判断是否滚动到底部
                    if (true) {
                        //加载更多功能的代码
                        pageIndex++;
                        initPostData(1+pageIndex);
                        refresh_layout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * @ 董传亮
     * 数据加载 网络数据
     */
    private void initData() {
        String mUrl = URLConstants.URL_SERIES;
        OkHttpTool.newInstance().start(mUrl).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                if (null == result) {
                    return;
                }
                if (mList != null) {
                    mList.clear();
                }
                Gson gson = new Gson();
                SeriesBean seriesBean = gson.fromJson(result, SeriesBean.class);
                mList.addAll(seriesBean.getData());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLVAdapter.notifyDataSetChanged();
                        refresh_layout.setRefreshing(false);
                    }
                });
            }
        });
    }


    /**
     * @ 董传亮
     * 数据加载 网络数据
     */
    private void initPostData(int p) {
        /**
         * 列表数据的网络请求
         */
        String page = p + "";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("p", page);
        paramMap.put("size", "10");
        String mUrl = URLConstants.URL_SERIES;
        OkHttpTool.newInstance().post(paramMap).start(mUrl).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                if (null != result) {
                    Gson gson = new Gson();
                    SeriesBean seriesBean = gson.fromJson(result, SeriesBean.class);
                    mList.addAll(seriesBean.getData());

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLVAdapter.notifyDataSetChanged();
                        refresh_layout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * @ 董传亮
     * 初始化当前页控件
     */

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.series_show_lv2);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_series);
        refresh_layout.setColorSchemeResources(R.color.colorblue);

    }

    /**
     * @ 董传亮
     * 底部刷新按钮
     */
    private View getfootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.refrushfootbutton, null);
        textrefrush = (TextView) view.findViewById(R.id.refrush_tv);
        textrefrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "没有更多了亲", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
