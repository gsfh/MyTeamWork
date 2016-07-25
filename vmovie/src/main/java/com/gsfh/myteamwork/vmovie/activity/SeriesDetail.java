package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailCommentEXLvAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailitemLvAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SeriesSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.CommentBean;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailVideoBean;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.gsfh.myteamwork.vmovie.widget.MyListView;
import com.gsfh.myteamwork.vmovie.widget.SlidingTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.gsfh.myteamwork.vmovie.R.id.activity_series_detail_comment_tv2;


/**
 * @ 董传亮
 * 系列页面的详情内容
 * Created by admin on 2016/7/14.
 */
public class SeriesDetail extends AppCompatActivity implements View.OnClickListener, SeriesDetailitemLvAdapter.SeriesDetailItemListener, SlidingTabLayout.SlidingTabClickListener {

    //展示的listView
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
    private ListView mListView;

    //  private ListView mListView;
    private SeriesDetailitemLvAdapter mLVAdapter;
    private Map<String, List<SeriesDetailBean.DataBean.PostsBean.ListBean>> mMap = new HashMap();
    private ArrayList<String> tabNameList = new ArrayList<>();
    private String tabName;

    //信息页面
    private TextView textTitle;
    private TextView textMany;
    private TextView textName;
    private TextView textUpdate;
    private TextView textType;
    private TextView textDetai;
    private String peoplecount;
    private TextView bluebtn;
    private  View  textLinesChangell ;
    private TextView textLinesChangeIv ;
    private TextView textLinesChangeTv ;


    // 评论部分
    private SeriesDetailCommentEXLvAdapter mEXLvAdapter;
    private TextView textShare;
    private View sharelayout;
    private View pluslayout;


    private TextView textComment;
    private TextView textCash;
    private List<CommentBean.DataBean> mCommentList = new ArrayList<>();
    private String sharecount;
    private String mCommentURL;
    private int mCommentPage = 0;
    //网络播放
    private ImageView ivback;
    private JCVideoPlayerStandard jcVideoPlayer;
    private int nestHeight;
    private int wHeight;
    private int buttomHerght;


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
     * 上个页面传入的数据
     */
    private void initData() {
        Intent intent = getIntent();
        seriesid = intent.getStringExtra("seriesid");
        peoplecount = intent.getStringExtra("readcount");
        //获取屏幕的高
        WindowManager mWmanger = getWindowManager();
        Display display = mWmanger.getDefaultDisplay();
        wHeight = display.getHeight();

        asyncRequest(seriesid, -1);//网咯选择
        //评论在上个网络线程里面开启
    }

    /**
     * @ 董传亮
     * 初始化控件 所有控件
     */
    private void initView() {

        //视频部分 part1
        initvideoview();


        //信息部分 part2
        initSortView();


        //列表部分  part3
        //initBanner();


        //评论部分  part4
        initComment();


    }

    /**
     * @ 董传亮
     * video控件的初始化  part1
     */
    private void initvideoview() {
        jcVideoPlayer = (JCVideoPlayerStandard) findViewById(R.id.activity_series_detail_videoplayer_standard);
        final NestedScrollView nestscrollview = (NestedScrollView) findViewById(R.id.nestscrollview);
        final View heightlayout = findViewById(R.id.heightlayout);
        ViewTreeObserver vto = heightlayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                heightlayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                wHeight = heightlayout.getHeight();
            }
        });
        ViewTreeObserver vto2 = nestscrollview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nestscrollview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                nestHeight = nestscrollview.getHeight();
            }
        });
        //返回键
        ivback = (ImageView) findViewById(R.id.seriesdetail_back_im);
        //分享按钮
    }

    /**
     * @ 董传亮
     * 初始化控件信息部分的控件 part2
     */
    private void initSortView() {
        textTitle = (TextView) findViewById(R.id.seriesdetail_itemsort_title_tv);
        pluslayout = findViewById(R.id.series_detail_plus_ll);

        textName = (TextView) findViewById(R.id.seriesdetail_itemsort_name_tv);
        textMany = (TextView) findViewById(R.id.seriesdetail_itemsort_many_tv);
        textUpdate = (TextView) findViewById(R.id.seriesdetail_itemsort_update_tv);
        textType = (TextView) findViewById(R.id.seriesdetail_itemsort_type_tv);
        textDetai = (TextView) findViewById(R.id.seriesdetail_itemsort_detail_tv);
        //底部按钮用于和detail交互
        textLinesChangell =  findViewById(R.id.seriesdetail_itemsort_showall_ll);
        textLinesChangeIv = (TextView)textLinesChangell.findViewById(R.id.seriesdetail_itemsort_showall_im);
        textLinesChangeTv = (TextView) textLinesChangell.findViewById(R.id.seriesdetail_itemsort_showall_tv);



        //蓝色按钮
        bluebtn = (TextView) findViewById(R.id.seriesdetail_itemsort_readcount_tv);
        bluebtn.setText(peoplecount + "人订阅");

    }

    /**
     * @ 董传亮
     * 初始化控件列表部分 part3
     */
    private void initBanner() {
        //列表部分  part3
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.activity_seriesdetail_tabs_tabll);
        mSlidingTabLayout.setDataChange(tabNameList, SeriesDetail.this);
        mListView = (MyListView) findViewById(R.id.series_show_lv);
        mLVAdapter = new SeriesDetailitemLvAdapter(SeriesDetail.this, mMap, tabNameList);
        mLVAdapter.setStartnum(0, tabNameList.get(0));//初始化进来的时候就给适配器传这两个值
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * @ 董传亮
     * 初始化评论部分的控件
     */
    private void initComment() {
        textShare = (TextView) findViewById(R.id.activity_series_detail_share_tv);
        sharelayout = findViewById(R.id.seriesdetail_comment_ll);
        textComment = (TextView) findViewById(activity_series_detail_comment_tv2);
        textCash = (TextView) findViewById(R.id.activity_series_detail_cach_tv);
        mEXLvAdapter = new SeriesDetailCommentEXLvAdapter(SeriesDetail.this, mCommentList);

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
                //评论按钮未点击前初始化评论url
                mCommentURL=mSerieslist.get(0).getPosts().get(0).getList().get(0).getSeries_postid();
                //所有的标题按钮的名字
                final List<SeriesDetailBean.DataBean.PostsBean> lvlist = new ArrayList<>();
                lvlist.addAll(mSerieslist.get(0).getPosts());                         //创建viewpager的fragment所需要的数据
                for (int i = 0; i < lvlist.size(); i++) {
                    tabNameList.add(lvlist.get(i).getFrom_to());
                    mMap.put(lvlist.get(i).getFrom_to(), lvlist.get(i).getList());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String id = mSerieslist.get(0).getPosts().get(0).getList().get(0).getSeries_postid();
                        asyncRequestVideoID(id, -1);//初始化播放数据
                        asyncRequestComment(id, 1);//初始化评论数据 第一页
                        setSortText(mSerieslist.get(0).getTitle(), mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getWeekly(), mSerieslist.get(0).getUpdate_to(),
                                mSerieslist.get(0).getTag_name(), mSerieslist.get(0).getContent(),
                                mSerieslist.get(0).getPosts().get(0).getList().get(0).getTitle(),
                                mSerieslist.get(0).getPosts().get(0).getList().get(0).getNumber()
                        );//初始化数据的标题信息
                        initBanner();
                        //    mSlidingTabLayout.setViewPager(mViewPager);
                        mLVAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
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
                //分享的网址在这里-------评论总数-----------getdata-- getshare_link----------------------------------
                SeriesDetailVideoBean videobean = gson.fromJson(result, SeriesDetailVideoBean.class);
                final String videoURL = videobean.getData().getVideo_link();
                sharecount = videobean.getData().getCount_comment();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jcVideoPlayer.setUp(videoURL, "");//视屏播放
                        textComment.setText(sharecount);                                 // 刷新评论数
                    }
                });
            }
        });
    }

    /**
     * @param id
     * @ 董传亮
     * 从子线程里面再来一个子线程来请求聊天 post 请求数据
     * postid 1792 从上层的解析的到  type 1  p 1  size 10          评论使用
     */
    private void asyncRequestComment(String id, int p) {
        String page = p + "";
        final int mP = p;
        //封装Post请求的参数
        FormBody formBody = new FormBody.Builder()
                .add("postid", id)//添加Post请求的参数
                .add("type", "1")//添加Post请求的参数
                .add("p", page)//添加Post请求的参数
                .add("size", "1")//添加Post请求的参数
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
                Gson gson = new Gson();
                //分享的网址在这里------------------getdata-- getshare_link----------------------------------

                CommentBean commentbean = gson.fromJson(result, CommentBean.class);
                mCommentList.addAll(commentbean.getData());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEXLvAdapter.notifyDataSetChanged();//通知刷新

                    }
                });
            }
        });
    }

    /**
     * @ 董传亮
     * 所有监听事件
     */
    private void initListener() {
        //showall按钮与detail交互 part2 和part1 的返回，分享按钮
        initDetailListener();
        // 评论功能   part4
        initCommentListener();
    }

    /**
     * @ 董传亮
     * 评论部分按钮 PART4
     */
    private void initCommentListener() {
        textCash.setOnClickListener(this);
        textComment.setOnClickListener(this);
        sharelayout.setOnClickListener(this);
        textShare.setOnClickListener(this);
    }

    /**
     * @ 董传亮
     * showall按钮与detail交互 part2
     */
    private void initDetailListener() {

        textLinesChangell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detaik = (String) textDetai.getText();
                if (detaik == null) {
                    return;
                }
                if(textLinesChangeTv.getText().equals("查看全部")){
                    textLinesChangeIv.setSelected(true);
                    textDetai.setMaxLines(10);
                    textLinesChangeTv.setText("收起简介");
                }else {
                    textLinesChangeTv.setText("查看全部");
                    textLinesChangeIv.setSelected(false);
                    textDetai.setMaxLines(2);

                }
            }
        });
        pluslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeriesDetail.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        textMany.setText(mmany);
        String mtype = "类型:" + type;
        textType.setText(mtype);
        textDetai.setText(detail);
        if (detail.length() > 65 && detail != null) {
            textLinesChangell.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        jcVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_series_detail_comment_cach_tv:
                break;
            case R.id.activity_series_detail_share_tv:

                break;
            case R.id.seriesdetail_comment_ll:
                showPopupWindow(v);
                // sendlayout.setVisibility(View.VISIBLE);
                break;
            case R.id.activity_series_detail_comment_tv2:
                showPopupWindow(v);
                // sendlayout.setVisibility(View.VISIBLE);
                break;
        }


    }

    @Override
    public void itemClick(String mTitle, String mURL, String mID) {//适配器过来的接口
        setDetailText(null, null, mTitle);//NestscrollView,设置
        //mURL=1792    series_postid    http://app.vmoiver.com/apiv3/series/getVideo
        asyncRequestVideoID(mURL, -1);
        jcVideoPlayer.setUp(mURL, "");                               //播放器开始播放
        //标题栏更改
        mCommentURL = mURL;        //初始化可能会为空 已经解决
        mCommentPage=0;//初始化评论页数
        mCommentList.clear();//之前的评论数据清空
        asyncRequestComment(mURL, 1);   // 评论数据   mUR记录到全局
    }


    //逻辑已经正确。值需要第一次进来后，吧dapter的  position设置为0 tabname 设置为第一个tabName其他早就初始化了。
    @Override
    public void giveYouTheTabName(String tabname, int nameIndex) {
        mLVAdapter.setStartnum(nameIndex, tabname);
        mLVAdapter.notifyDataSetChanged();
    }

    /**
     * @param view
     * @ 董传亮
     * pupupWidow
     */
    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(SeriesDetail.this).inflate(
                R.layout.seriesdetail_pupupwindow, null);

        //宽高
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, nestHeight + wHeight, true);
        TextView textview = (TextView) contentView.findViewById(R.id.seriesdetail_pupupwindow_commentperson_tv);
        textview.setText(textComment.getText() + "人评论");

       View editSendtext=  contentView.findViewById(R.id.seriesdetail_pupupwindow_editsend_ll);
             //editlayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,wHeight));
        RelativeLayout.LayoutParams mlayParams= (RelativeLayout.LayoutParams) editSendtext.getLayoutParams();
        mlayParams.height=wHeight;
        editSendtext.setLayoutParams(mlayParams);
        editSendtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SeriesDetail.this,LoginActivity.class);
                startActivity(intent);


            }
        });


        // 设置按钮的点击事件
        View button = contentView.findViewById(R.id.seriesdetail_pupupwindow_top_ll);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //评论部分

        final PullToRefreshExpandableListView mPullToRefreshExpandableListView = (PullToRefreshExpandableListView) contentView.findViewById(R.id.seriesdetail_pupupwindow_detail_lv);
        ExpandableListView mExpandableListView = mPullToRefreshExpandableListView.getRefreshableView();
        mExpandableListView.setGroupIndicator(null);


        mPullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);// 设置下拉刷新模式
        mPullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                mPullToRefreshExpandableListView.onRefreshComplete();
                mCommentPage++;
                if (mCommentURL != null) {
                    asyncRequestComment(mCommentURL, 1 + mCommentPage);
                }
                mPullToRefreshExpandableListView.onRefreshComplete();

            }
        });

        mExpandableListView.setAdapter(mEXLvAdapter);
        //设置ExpandableListView点击不收缩
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < mCommentList.size(); i++) {
            mExpandableListView.expandGroup(i);
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        popupWindow.setTouchable(true);
//        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
//        // 设置popWindow的显示和消失动画
        Animation anim1= AnimationUtils.loadAnimation(this,R.anim.popexpanlistviewtrans);
        Animation anim2= AnimationUtils.loadAnimation(this,R.anim.popexpanlistviewtrans);
        editSendtext.startAnimation(anim1);
        mExpandableListView.startAnimation(anim2);


        // popupWindow.

        // 在底部显示
//        popupWindow.showAtLocation(SeriesDetail.this.findViewById(R.id.activity_series_detail_comment_tv2),
//                Gravity.BOTTOM, 0, 0);
//        popupWindow.showAtLocation(view,
//                Gravity.NO_GRAVITY, location[0], location[1]-popupWindow.getHeight());
        popupWindow.showAtLocation(view,
                Gravity.NO_GRAVITY, location[0], location[1]);


        popupWindow.setOutsideTouchable(true);

        //移动
        popupWindow.showAsDropDown(view);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.mipmap.ic_launcher));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }


}
