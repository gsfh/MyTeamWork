package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.ChannelRCYAdapter;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;

/**
 * @ 董传亮
 * 首页大页面的  频道子页面
 * Created by admin on 2016/7/13.
 */
public class ChannelFragment extends Fragment {
   //需要数据
   private Context mContext;


   //需求控件
    private RecyclerView mRecyclerView;
    private GridLayoutManager mManager;
    private ChannelRCYAdapter mRCYAdapter;


    /**
     * 董传亮 静态工厂
     * @param args
     * @return
     */
    public static ChannelFragment newInstance(Bundle args){

          ChannelFragment fragment=new ChannelFragment();
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
        View view =inflater.inflate(R.layout.fragment_channel,null);//展示recyclerView
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
        mRCYAdapter=new ChannelRCYAdapter(mContext);
        mRecyclerView.setAdapter(mRCYAdapter);
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
                 //还没建立been
                }
                mRCYAdapter.notifyDataSetChanged();
            }});}




    /**
     * @ 董传亮
     * 初始化当前页控件
     */
    private void  initView(View view) {
        mRecyclerView= (RecyclerView) view.findViewById(R.id.channel_show_lv);
         mManager = new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(mManager);

    }
}
