package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.gsfh.myteamwork.vmovie.MainActivity;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesSortLvAdapter;

/**
 * @ 董传亮
 * 系列页面的详情内容
 * Created by admin on 2016/7/14.
 */
public class SeriesDetail extends AppCompatActivity {
    //展示的listView
    private ListView mlistView;
    private SeriesSortLvAdapter mSortAdapter;
    //上个页面传入的值
    private String seriesid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriesdetail);
        //c从上个页面获得数据
        initData();
        //初始化控件
        initView();
    }

    /**
     * @ 董传亮
     * 初始化控件
     */
    private void initView() {
        mlistView= (ListView) findViewById(R.id.activity_seriesdetail_show_lv);
        mSortAdapter=new SeriesSortLvAdapter(SeriesDetail.this);
        mlistView.setAdapter(mSortAdapter);

    }

    /**
     * @ 董传亮
     * 上个页面传入的数据
     */
    private void initData() {
        Intent intent=getIntent();
        seriesid=intent.getStringExtra("seriesid");

    }
}
