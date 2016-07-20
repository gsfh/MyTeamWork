package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.ChannelSort_HotActivity;
import com.gsfh.myteamwork.vmovie.adapter.ChannelRCYAdapter;
import com.gsfh.myteamwork.vmovie.bean.ChannalBean;
import com.gsfh.myteamwork.vmovie.util.URLConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ 董传亮
 * 首页大页面的  频道子页面
 * Created by admin on 2016/7/13.
 */
public class ChannelFragment extends Fragment {
    //需要数据
    private Context mContext;
    private OkHttpClient okHttpClient = new OkHttpClient();

    //需求控件
    private RecyclerView mRecyclerView;
    private GridLayoutManager mManager;
    private ChannelRCYAdapter mRCYAdapter;
    //标题数据
    private List<ChannalBean.DataBean> mChannalList = new ArrayList<>();

    /**
     * 董传亮 静态工厂
     *
     * @param args
     * @return
     */
    public static ChannelFragment newInstance(Bundle args) {

        ChannelFragment fragment = new ChannelFragment();
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
        View view = inflater.inflate(R.layout.fragment_channel, null);//展示recyclerView
        //初始化数据
       // initData();
        asyncRequest( null,-1);
        //初始化控件
        initView(view);
        //绑定适配器
     //   bindAdapter();

        return view;
    }

    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mRCYAdapter = new ChannelRCYAdapter(mContext, mChannalList);
        mRecyclerView.setAdapter(mRCYAdapter);
        mRCYAdapter.setOnItemClickListener(new ChannelRCYAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String tab, String type, String cate_id) {
                Log.i("ddsfec", "onItemClick: "+tab+" "+type+" "+cate_id);
                Intent intent = new Intent(getActivity(), ChannelSort_HotActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("tab", tab);
                intent.putExtra("cateid", cate_id);
                startActivity(intent);
            }
        });

    }

    /**
     * @param id
     * @ 董传亮
     * post 请求数据
     */
    private void asyncRequest(String id, int p) {
        String page=p+"";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_CHANNEL) //网络请求地址
                .post(formBody) //Post请求，并且将Post请求需要的参数封装到FormBody中
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (null == result) {
                    return;
                }

                Gson gson = new Gson();
                ChannalBean channal = gson.fromJson(result, ChannalBean.class);
                mChannalList = channal.getData();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bindAdapter();
//                        mRCYAdapter.notifyItemChanged();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.channel_show_lv);
        mManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setHasFixedSize(true);//设置无需便利检测item高度

    }
}
