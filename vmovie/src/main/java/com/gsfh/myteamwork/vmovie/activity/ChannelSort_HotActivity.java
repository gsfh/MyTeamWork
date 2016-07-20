package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.ChannelSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.ChannalBean;
import com.gsfh.myteamwork.vmovie.bean.ChannelSortBean;
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
public class ChannelSort_HotActivity extends AppCompatActivity {
    //按钮名字
    private String tabname;
    private String tab;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private List<ChannelSortBean.DataBean> sortList = new ArrayList<>();

    //控件
    private ImageView imageViewback;
    private ListView mListView;
    private TextView text_tab;
    private ChannelSortLvAdapter mLVAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_channelsort_hot);

        //初始化数据
        initData();
        //初始化控件
        initView();
        //绑定适配器
        bindAdapter();

    }

    /**
     *  @ 董传亮
     *  创建绑定适配器
     */
    private void bindAdapter() {
         mLVAdapter=new ChannelSortLvAdapter(ChannelSort_HotActivity.this,sortList);
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        text_tab = (TextView) findViewById(R.id.channelsort_hot_tab_tv);
        imageViewback = (ImageView) findViewById(R.id.channelsort_hot_back_im);
        mListView = (ListView) findViewById(R.id.channelsort_hot_show_lv);
        text_tab.setText(tabname);

    }

    /**
     * @ 董传亮
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        tabname = intent.getStringExtra("type");
        tab = intent.getStringExtra("tab");

        //网络数据
        asyncRequest(tab, 1);
    }

    /**
     * @param id
     * @ 董传亮
     * post 请求数据
     */
    private void asyncRequest(String id, int p) {
        String page = p + "";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("tab", id)
                .add("p", page)
                .add("size", "10")
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
                ChannelSortBean sortBean = gson.fromJson(result, ChannelSortBean.class);
                sortList.addAll(sortBean.getData());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLVAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}

