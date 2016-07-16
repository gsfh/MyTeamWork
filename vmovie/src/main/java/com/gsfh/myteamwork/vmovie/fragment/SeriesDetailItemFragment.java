package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.SeriesDetail;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailitemLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.BackStageBean;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
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
 * 系列详情页的listView
 * Created by admin on 2016/7/13.
 */
public class SeriesDetailItemFragment extends Fragment {

    //网络Post请求
    private OkHttpClient okHttpClient = new OkHttpClient();
    //需要数据
    private SeriesDetail mContext;
    private int index;
    private String id;
    private int p;
    //适配器需要的数据
    private List<SeriesDetailBean.DataBean.PostsBean.ListBean> mlist = new ArrayList<>();
    //需求控件
    private ListView mListView;
    private SeriesDetailitemLvAdapter mLVAdapter;
    //创建fragment需要进入适配器的数据
    List<BackStageBean.DataBean> datalist = new ArrayList<>();

    /**
     * 董传亮 静态工厂
     *
     * @param args
     * @return
     */
    public static SeriesDetailItemFragment newInstance(Bundle args) {

        SeriesDetailItemFragment fragment = new SeriesDetailItemFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = (SeriesDetail) getActivity();//获取上下文
        index = getArguments().getInt("index");//页面位置值
        id = getArguments().getString("id");
        p = getArguments().getInt("p");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seriesdetailitem_lv, null);//展示listview
        //初始化数据
        initData();
        //初始化控件
        initView(view);
        //绑定适配器
        bindAdapter();
        //设置监听事件
        initListener();
        return view;
    }

    /**
     * @ 董传亮
     * 监听事件跳转
     */
    private void initListener() {
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("ddsfec", "onItemClick: "+"ddddddddddddd");
//                Toast.makeText(mContext,"ddd", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mLVAdapter = new SeriesDetailitemLvAdapter(mContext, mlist);
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * @ 董传亮
     * 数据加载 网络数据
     */
    private void initData() {
        //进入适配器的数据更改
        chooseData();
    }

    /**
     * @ 董传亮
     * 初始化当前页控件
     */
    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.seriesdetailitem_tv);
    }

    /**
     * @ 董传亮
     * 进入Fragment 的数据
     * 根据位置值重新请求数据
     */
    private void chooseData() {

        asyncRequest(index, id, p);

    }


    /**
     * @param index
     * @param id
     * // id=45  p=1
     * @ 董传亮
     */
    private void asyncRequest(final int index, String id, int p) {

        String page = p + "";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("seriesid", id)//添加Post请求的参数
                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_SERIESVIEW) //网络请求地址
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
                SeriesDetailBean seriesDetailBean = gson.fromJson(result, SeriesDetailBean.class);

                List<SeriesDetailBean.DataBean.PostsBean> lvlist = new ArrayList<>();
                lvlist.addAll(seriesDetailBean.getData().getPosts());
                if (null != mlist) {
                    mlist.clear();
                }
                mlist.addAll(lvlist.get(index).getList());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLVAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }


}