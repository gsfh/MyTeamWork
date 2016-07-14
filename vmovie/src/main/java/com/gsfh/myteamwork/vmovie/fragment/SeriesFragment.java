package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.SeriesBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 董传亮
 * 系列 页面展示 listView
 * Created by admin on 2016/7/13.
 */
public class SeriesFragment extends Fragment {
   //需要数据
   private Context mContext;

    private List<SeriesBean.DataBean> mList =new ArrayList<>();;
   //需求控件
    private ListView mListView;
    private SeriesLvAdapter mLVAdapter;


    /**
     * 董传亮 静态工厂
     * @param args
     * @return
     */
    public static SeriesFragment newInstance(Bundle args){

          SeriesFragment fragment=new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getContext();//获取上下文

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_series,null);//展示listview
        //初始化数据
        initData();
        //初始化控件
        initView(view);

        //绑定适配器
        bindAdapter();

                return view;
    }

    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mLVAdapter=new SeriesLvAdapter(mContext,mList);
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * @ 董传亮
     * 数据加载 网络数据
     */
    private void initData() {
        String mUrl= URLConstants.URL_SERIES;
        OkHttpTool.newInstance().start(mUrl).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                if (null != result) {
                    Gson gson = new Gson();
                    SeriesBean beenSeries = gson.fromJson(result, SeriesBean.class);
                    mList.addAll(beenSeries.getData());

                }
                mLVAdapter.notifyDataSetChanged();
            }});}




    /**
     * @ 董传亮
     * 初始化当前页控件
     */
    private void  initView(View view) {
        mListView= (ListView) view.findViewById(R.id.series_show_lv);
    }
}
