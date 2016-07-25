package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.ChannelSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.ChannelDetailBean;
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
 * 频道的跳转页面
 * Created by admin on 2016/7/20.
 */
public class ChannelSort_HotActivity extends AppCompatActivity implements ChannelSortLvAdapter.OnitemClickListener {
    //按钮名字
    private String tabname;
    private String tab;
    private String cateid;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private List<ChannelDetailBean.DataBean> sortList = new ArrayList<>();

    //控件
    private ImageView imageViewback;
    private ListView mListView;
    private TextView text_tab;
    private ChannelSortLvAdapter mLVAdapter;
    //刷新
    private SwipeRefreshLayout refresh_layout = null;//刷新控件
    private TextView textrefrush;
    private int pageIndex = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channelsort_hot);
        //初始化数据
        initData();
        //初始化控件
        initView();
        //绑定适配器
        bindAdapter();
        //点击事件
        initListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //监听跳转
    private void initListener() {
        mLVAdapter.setOnitemClickListener(ChannelSort_HotActivity.this);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh_layout.setRefreshing(true);
                asyncRequest(cateid, 1);


            }
        });
    }

    /**
     * @ 董传亮
     * 创建绑定适配器
     */
    private void bindAdapter() {
        mLVAdapter = new ChannelSortLvAdapter(ChannelSort_HotActivity.this, sortList);
        mListView.addFooterView(getFootView());
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getCount() - 1) {
                    // 判断是否滚动到底部
                    if (true) {
                        //加载更多功能的代码
                        pageIndex++;
                        asyncRequest(cateid, 1 + pageIndex);
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
     * @return
     * @ 董传亮
     * 底部刷新按钮
     */
    private View getFootView() {
        View view = LayoutInflater.from(ChannelSort_HotActivity.this).inflate(R.layout.refrushfootbutton, null);
        textrefrush = (TextView) view.findViewById(R.id.refrush_tv);
        textrefrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncRequest(cateid, 1);
                Toast.makeText(ChannelSort_HotActivity.this, "没有更多了亲", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        text_tab = (TextView) findViewById(R.id.channelsort_hot_tab_tv);
        imageViewback = (ImageView) findViewById(R.id.channelsort_hot_back_im);
        mListView = (ListView) findViewById(R.id.channelsort_hot_show_lv);
        text_tab.setText(tabname);
//刷新
        refresh_layout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_container);
        refresh_layout.setColorSchemeResources(R.color.colorblue);
    }

    /**
     * @ 董传亮
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        tabname = intent.getStringExtra("type");
        tab = intent.getStringExtra("tab");
        cateid = intent.getStringExtra("cateid");
        //网络数据
        asyncRequest(cateid, 1);
    }

    /**
     * @param id
     * @ 董传亮
     * post 请求数据
     */
    private void asyncRequest(String id, final int p) {
        String page = p + "";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("cateid", id)
                .add("p", page)
                .add("size", "10")
                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_INCATE) //网络请求地址
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
                if (sortList != null && p == 1) {
                    sortList.clear();
                }
                Gson gson = new Gson();
                ChannelDetailBean detailBean = gson.fromJson(result, ChannelDetailBean.class);
                sortList.addAll(detailBean.getData());
                Log.i("ddsfec", "onResponse: " + sortList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLVAdapter.notifyDataSetChanged();
                        refresh_layout.setRefreshing(false);
                    }
                });

            }
        });
    }

    @Override
    public void itemClick(String postid) {
        Intent intent = new Intent(ChannelSort_HotActivity.this, FirstDetailActivity.class);
        intent.putExtra("id", postid);
        startActivity(intent);

    }

    public void back(View view) {
        finish();
    }


}

