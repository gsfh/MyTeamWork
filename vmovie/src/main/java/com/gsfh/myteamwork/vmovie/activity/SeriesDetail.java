package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailItemVPAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailitemLvAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
import com.gsfh.myteamwork.vmovie.fragment.SeriesDetailItemFragment;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.gsfh.myteamwork.vmovie.widget.SlidingTabLayout;

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
 * 系列页面的详情内容
 * Created by admin on 2016/7/14.
 */
public class SeriesDetail extends AppCompatActivity implements View.OnClickListener ,SeriesDetailitemLvAdapter.SeriesDetailItemListener {
    //展示的listView
    private ListView mlistView;
    private SeriesSortLvAdapter mSortAdapter;
    //上个页面传入的值 seriesid ="47"
    private String seriesid;
    //当前页所需所有数据
     private ArrayList<SeriesDetailBean.DataBean> mSerieslist=new ArrayList<>();
     //  private
    //网络Post请求
    private OkHttpClient okHttpClient = new OkHttpClient();
    //切换展示页
    private SlidingTabLayout mSlidingTabLayout;
    /*每个 tab 的 item*/
    //private List<PagerItem> mTab = new ArrayList<>() ;
    private ViewPager mViewPager;
    private SeriesDetailItemVPAdapter mVPAdapter;
    private ArrayList<String>tabNameList=new ArrayList<>();
    private List<Fragment> mfragmentList=new ArrayList<>();
    //信息页面
    private TextView textTitle;
    private TextView textMany;
    private TextView textName;
    private TextView textUpdate;
    private TextView textType;
    private TextView textDetai;
    private TextView textshowall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        //c从上个页面获得数据
        initData();
        //初始化控件
        initView();
        //监听事件
        initListener();

    }

    /**
     * @ 董传亮
     * 所有监听事件
     */
    private void initListener() {
        //showall按钮与detail交互 part2
        initDetailListener();
    }

    /**
     * @ 董传亮
     * showall按钮与detail交互 part2
     */
    private void initDetailListener() {

        textshowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detaik= (String) textDetai.getText();
                if(detaik==null){
                return;
                }
                if(detaik.length()>60 && textshowall.getText().toString().equals("查看全部")){
                    textDetai.setLines(10);
                    textshowall.setText("收起简介");
                    textshowall.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.dropup),null);
                }else{
                    textDetai.setLines(2);
                    textshowall.setText("查看全部");
                    textshowall.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.dropdown),null);
                }
            }
        });

    }

    /**
     * @ 董传亮
     * 初始化控件 所有控件
     */
    private void initView() {

        //视频部分 part1
        initvideoview();
        mSortAdapter=new SeriesSortLvAdapter(SeriesDetail.this);



        //信息部分 part2
        initSortView();


        //列表部分  part3
        initBanner();
    }

    /**
     * @ 董传亮
     * video控件的初始化
     */
    private void initvideoview() {

    }

    /**
     * @ 董传亮
     * 初始化控件列表部分
     */
    private void initBanner() {
        //列表部分  part3
//        mTabLayout= (TabLayout) findViewById(R.id.activity_seriesdetail_title_tabll);
        mSlidingTabLayout= (SlidingTabLayout) findViewById(R.id.activity_seriesdetail_tabs_tabll);
        mViewPager= (ViewPager) findViewById(R.id.activity_seriesdetail_title_vp);
        mVPAdapter=new SeriesDetailItemVPAdapter(tabNameList,mfragmentList,getSupportFragmentManager());
        mViewPager.setAdapter(mVPAdapter);
  //mViewPAGER在网络请求来初始化
    }

    /**
     * @ 董传亮
     * 初始化控件信息部分的控件
     */
    private void initSortView() {
            textTitle= (TextView) findViewById(R.id.seriesdetail_itemsort_title_tv);
            textName= (TextView) findViewById(R.id.seriesdetail_itemsort_name_tv);
            textMany= (TextView) findViewById(R.id.seriesdetail_itemsort_many_tv);
            textUpdate= (TextView) findViewById(R.id.seriesdetail_itemsort_update_tv);
            textType= (TextView) findViewById(R.id.seriesdetail_itemsort_type_tv);
            textDetai= (TextView) findViewById(R.id.seriesdetail_itemsort_detail_tv);
           //底部按钮用于和detail交互
        textshowall= (TextView) findViewById(R.id.seriesdetail_itemsort_showall_tv);
    }

    /**
     * @ 董传亮
     * 上个页面传入的数据
     */
    private void initData() {
        Intent intent=getIntent();
        seriesid=intent.getStringExtra("seriesid");
        asyncRequest(seriesid,1);

    }
    /**
     * @ 董传亮
     * 初始化 fragmentlist
     * @param lvlist
     * @param id
     * @param p
     */
    private void makeFragmentList(List<SeriesDetailBean.DataBean.PostsBean> lvlist, String id, int p){
        Bundle budle;
        for (int i=0;i<lvlist.size();i++) {
            budle = new Bundle();
            budle.putInt("index",i);
            budle.putString("id",id);
            budle.putInt("p",p);
            mfragmentList.add(SeriesDetailItemFragment.newInstance(budle) );
        }
    }



    /**
     * @param id
     * @ 董传亮
     * post 请求数据
     */
    private void asyncRequest(final String id, final int p) {
        String page=p+"";
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
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (null == result) {
                    return;
                }
                Gson gson=new Gson();
                SeriesDetailBean seriesDetailBean=gson.fromJson(result,SeriesDetailBean.class);
                    mSerieslist.add(seriesDetailBean.getData());//所有数据的bean对象
                                          //所有的标题按钮的名字
                List<SeriesDetailBean.DataBean.PostsBean> lvlist=new ArrayList<>();
                lvlist.addAll( mSerieslist.get(0).getPosts());                         //创建viewpager的fragment所需要的数据
                for(int i=0;i<lvlist.size();i++){
                tabNameList.add(lvlist.get(i).getFrom_to());}
                Log.i("ddsfec", "onResponse: "+tabNameList.get(0));
                makeFragmentList(lvlist,id,p);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setSortText(mSerieslist.get(0).getTitle(),mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getWeekly(),mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getTag_name(),mSerieslist.get(0).getContent(),
                                mSerieslist.get(0).getPosts().get(0).getList().get(0).getTitle(),
                                mSerieslist.get(0).getPosts().get(0).getList().get(0).getNumber()
                        );
                        mSlidingTabLayout.setViewPager(mViewPager);
                        mVPAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    /**
     * @ 董传亮
     * 用于刷新显示页面
     */
    private void  setSortText(String title,String name ,String weekly,String many,String type,String detail,String posttitle,String postnum){
        String post=posttitle;//85（集）后面的
        String num=postnum;//85(集)
         String realtitle="第"+num+"集"+" "+post;
        textTitle.setText(realtitle);
        textName.setText(title);
        String mweekly="更新:"+weekly;
        textUpdate.setText(mweekly);
        String mmany="集数:更新至"+many+"集";
        textMany.setText(mmany);//ok
        String mtype="类型:"+type;//0k
        textType.setText(mtype);//0k
        textDetai.setText(detail);//ok
        if (detail.length()>60 && detail!=null){
            textshowall.setVisibility(View.VISIBLE);

        }

    }
    /**
     * @ 董传亮
     * 标题动态加载
     */
    private void setDetailText(String num,String title ,String alltitle ){
        if(alltitle!=null){
            textTitle.setText(alltitle);
        }else {
        textTitle.setText(title);}
    };

    /**
     * @param id
     * @ 董传亮
     * 从子线程里面再来一个子线程来请求聊天 post 请求数据
     * postid 1792 从上层的解析的到  type 1  p 1  size 10
     */
    private void asyncRequestCommentID(String id, int p) {
        String page=p+"";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("postid", "1792")//添加Post请求的参数
                .add("type", "1")//添加Post请求的参数
                .add("p", page)//添加Post请求的参数
                .add("size", "10")//添加Post请求的参数
                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_SERIESCOMMENT) //网络请求地址
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



            }
        });
    }

    /**
     * @param id
     * @ 董传亮
     * 从子线程里面再来一个子线程来请求聊天 post 请求数据
     * postid 1792 从上层的解析的到  type 1  p 1  size 10
     */
    private void asyncRequestVideoID(String id, int p) {
        String page=p+"";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("series_postid", "1792")//添加Post请求的参数

                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_SERIESVEDIOLIST) //网络请求地址
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

                Log.i("ddsfec", "onResponse:    "+result);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setSortText(mSerieslist.get(0).getTitle(),mSerieslist.get(0).getUpdate_to(),mSerieslist.get(0).getWeekly(),mSerieslist.get(0).getUpdate_to(),mSerieslist.get(0).getTag_name(),mSerieslist.get(0).getContent());
//                    }
//                });


            }
        });
    }

    /**
     * @ 董传亮
     * 点击事件
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.seriesdetail_back_im :  finish();  ;break;
        }

}

    @Override
    public void itemClick(String mTitle, String mURL, String mID) {

        setDetailText(null,null,mTitle);//NestscrollView,设置
                                         //播放器开始播放
                                         //标题栏更改

    }


}
