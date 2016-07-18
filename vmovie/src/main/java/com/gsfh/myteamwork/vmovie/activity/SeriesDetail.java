package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailItemVPAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailitemLvAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailVideoBean;
import com.gsfh.myteamwork.vmovie.fragment.SeriesDetailItemFragment;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.gsfh.myteamwork.vmovie.widget.SlidingTabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
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
public class SeriesDetail extends AppCompatActivity implements View.OnClickListener, SeriesDetailitemLvAdapter.SeriesDetailItemListener {
    //展示的listView
    private ListView mlistView;
    private SeriesSortLvAdapter mSortAdapter;
    //上个页面传入的值 seriesid ="47"
    private String seriesid;
    //当前页所需所有数据
    private ArrayList<SeriesDetailBean.DataBean> mSerieslist = new ArrayList<>();
    //  private
    //网络Post请求
    private OkHttpClient okHttpClient = new OkHttpClient();
    //切换展示页
    private SlidingTabLayout mSlidingTabLayout;
    /*每个 tab 的 item*/
    //private List<PagerItem> mTab = new ArrayList<>() ;
    private ViewPager mViewPager;
    private SeriesDetailItemVPAdapter mVPAdapter;
    private ArrayList<String> tabNameList = new ArrayList<>();
    private List<Fragment> mfragmentList = new ArrayList<>();
    //信息页面
    private TextView textTitle;
    private TextView textMany;
    private TextView textName;
    private TextView textUpdate;
    private TextView textType;
    private TextView textDetai;
    private TextView textshowall;
    //评论部分
    private View mLinearLayout;
    private EditText mEditText;
    private TextView mCash;
    //网络播放
    private JCVideoPlayerStandard jcVideoPlayer;

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
        // 评论功能   part4
        initCommentListener();
    }

    /**
     * @ 董传亮
     * 评论部分按钮 PART4
     */
    private void initCommentListener() {
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
    }

    /**
     * @ 董传亮
     * showall按钮与detail交互 part2
     */
    private void initDetailListener() {

        textshowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detaik = (String) textDetai.getText();
                if (detaik == null) {
                    return;
                }
                if (detaik.length() > 60 && textshowall.getText().toString().equals("查看全部")) {
                    textDetai.setLines(10);
                    textshowall.setText("收起简介");
                    Drawable drawable1 = getResources().getDrawable(R.drawable.dropup);
                    drawable1.setBounds(0,0,100,100);
                    textshowall.setCompoundDrawables(null, null,drawable1 , null);
                } else {
                    textDetai.setLines(2);
                    textshowall.setText("查看全部");
                    Drawable drawable2 = getResources().getDrawable(R.drawable.dropdown);
                    drawable2.setBounds(0,0,100,100);
                    textshowall.setCompoundDrawables(null, null,drawable2, null);
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
        mSortAdapter = new SeriesSortLvAdapter(SeriesDetail.this);


        //信息部分 part2
        initSortView();


        //列表部分  part3
        initBanner();


        //评论部分  part4
        initComment();


    }

    /**
     * @ 董传亮
     * 初始化评论部分的控件
     */
    private void initComment() {
        mLinearLayout = findViewById(R.id.activity_series_buttom_bar2_ll);
        mEditText = (EditText) findViewById(R.id.activity_series_detail_editv);
        mCash = (TextView) findViewById(R.id.activity_series_detail_comment_cach_tv);
    }

    /**
     * @ 董传亮
     * video控件的初始化
     */
    private void initvideoview() {

        jcVideoPlayer = (JCVideoPlayerStandard) findViewById(R.id.activity_series_detail_videoplayer_standard);

    }

    /**
     * @ 董传亮
     * 初始化控件列表部分
     */
    private void initBanner() {
        //列表部分  part3
//        mTabLayout= (TabLayout) findViewById(R.id.activity_seriesdetail_title_tabll);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.activity_seriesdetail_tabs_tabll);
        mViewPager = (ViewPager) findViewById(R.id.activity_seriesdetail_title_vp);
        mVPAdapter = new SeriesDetailItemVPAdapter(tabNameList, mfragmentList, getSupportFragmentManager());
        mViewPager.setAdapter(mVPAdapter);
        //mViewPAGER在网络请求来初始化
    }

    /**
     * @ 董传亮
     * 初始化控件信息部分的控件
     */
    private void initSortView() {
        textTitle = (TextView) findViewById(R.id.seriesdetail_itemsort_title_tv);
        textName = (TextView) findViewById(R.id.seriesdetail_itemsort_name_tv);
        textMany = (TextView) findViewById(R.id.seriesdetail_itemsort_many_tv);
        textUpdate = (TextView) findViewById(R.id.seriesdetail_itemsort_update_tv);
        textType = (TextView) findViewById(R.id.seriesdetail_itemsort_type_tv);
        textDetai = (TextView) findViewById(R.id.seriesdetail_itemsort_detail_tv);
        //底部按钮用于和detail交互
        textshowall = (TextView) findViewById(R.id.seriesdetail_itemsort_showall_tv);
    }

    /**
     * @ 董传亮
     * 上个页面传入的数据
     */
    private void initData() {
        Intent intent = getIntent();
        seriesid = intent.getStringExtra("seriesid");
        asyncRequest(seriesid, 1);

    }

    /**
     * @param lvlist
     * @param id
     * @param p
     * @ 董传亮
     * 初始化 fragmentlist
     */
    private void makeFragmentList(List<SeriesDetailBean.DataBean.PostsBean> lvlist, String id, int p) {
        Bundle budle;
        for (int i = 0; i < lvlist.size(); i++) {
            budle = new Bundle();
            budle.putInt("index", i);
            budle.putString("id", id);
            budle.putInt("p", p);
            mfragmentList.add(SeriesDetailItemFragment.newInstance(budle));
        }
    }


    /**
     * @param id
     * @ 董传亮
     * post 请求数据 为了获取列表中的总数据
     */
    private void asyncRequest(final String id, final int p) {
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
                mSerieslist.add(seriesDetailBean.getData());//所有数据的bean对象
                //所有的标题按钮的名字
                List<SeriesDetailBean.DataBean.PostsBean> lvlist = new ArrayList<>();
                lvlist.addAll(mSerieslist.get(0).getPosts());                         //创建viewpager的fragment所需要的数据
                for (int i = 0; i < lvlist.size(); i++) {
                    tabNameList.add(lvlist.get(i).getFrom_to());
                }
                makeFragmentList(lvlist, id, p);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String id=mSerieslist.get(0).getPosts().get(0).getList().get(0).getSeries_postid();
                        asyncRequestVideoID( id, -1);//初始化播放

                        setSortText(mSerieslist.get(0).getTitle(), mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getWeekly(), mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getTag_name(), mSerieslist.get(0).getContent(),
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
    private void setSortText(String title, String name, String weekly, String many, String type, String detail, String posttitle, String postnum) {
        String post = posttitle;//85（集）后面的
        String num = postnum;//85(集)
        String realtitle = "第" + num + "集" + " " + post;
        textTitle.setText(realtitle);
        textName.setText(title);
        String mweekly = "更新:" + weekly;
        textUpdate.setText(mweekly);
        String mmany = "集数:更新至" + many + "集";
        textMany.setText(mmany);//ok
        String mtype = "类型:" + type;//0k
        textType.setText(mtype);//0k
        textDetai.setText(detail);//ok
        if (detail.length() > 60 && detail != null) {
            textshowall.setVisibility(View.VISIBLE);

        }

    }

    /**
     * @ 董传亮
     * 标题动态加载
     */
    private void setDetailText(String num, String title, String alltitle) {
        if (alltitle != null) {
            textTitle.setText(alltitle);
        } else {
            textTitle.setText(title);
        }
    }

    ;

    /**
     * @param id
     * @ 董传亮
     * 从子线程里面再来一个子线程来请求聊天 post 请求数据
     * postid 1792 从上层的解析的到  type 1  p 1  size 10
     */
    private void asyncRequestCommentID(String id, int p) {
        String page = p + "";
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
     * postid 1792 从上层的解析的到  type 1  p 1  size 10          视屏播放使用
     */
    private void asyncRequestVideoID(String id, int p) {
        String page = p + "";
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("series_postid", id)//添加Post请求的参数

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
                Gson gson = new Gson();
                //分享的网址在这里------------------getdata-- getshare_link----------------------------------
                SeriesDetailVideoBean videobean = gson.fromJson(result, SeriesDetailVideoBean.class);
                final String videoURL = videobean.getData().getVideo_link();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jcVideoPlayer.setUp(videoURL, "");//视屏播放
                    }
                });
            }
        });
    }

    /**
     * @ 董传亮
     * 点击事件
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seriesdetail_back_im:
                finish();
                ;
                break;
        }

    }

    @Override
    public void itemClick(String mTitle, String mURL, String mID) {

        setDetailText(null, null, mTitle);//NestscrollView,设置
        //mURL=1792    series_postid    http://app.vmoiver.com/apiv3/series/getVideo
        asyncRequestVideoID(mURL, -1);
        jcVideoPlayer.setUp(mURL, "");                               //播放器开始播放
        //标题栏更改

    }

    @Override
    protected void onPause() {
        super.onPause();

        jcVideoPlayer.releaseAllVideos();
    }


    //评论功能分割线------------------------------------------------------------------------------------------------------------------------>
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(SeriesDetail.this).inflate(
                R.layout.seriesdetail_pupupwindow, null);
        // 设置按钮的点击事件
//        LinearLayout button = (Button) contentView.findViewById(R.layout.mLinearLayout);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SeriesDetail.this, "button is pressed",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        //宽高
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);
//        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
//        window.setFocusable(true);
//        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 在底部显示
        popupWindow.showAtLocation(SeriesDetail.this.findViewById(R.id.activity_series_buttom_bar2_ll),
                Gravity.BOTTOM, 0, 0);


        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//        R.mipmap.ic_launcher));


        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }


}
