package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.BackStageDetail;
import com.gsfh.myteamwork.vmovie.activity.SecondDetailActivity;
import com.gsfh.myteamwork.vmovie.adapter.BackStageSortLvAdapter;
import com.gsfh.myteamwork.vmovie.bean.BackStageBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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
 * 幕后文章页面  第三页
 * Created by admin on 2016/7/13.
 */
public class BackStageSortFragment extends Fragment {
    //下拉刷新
    private PullToRefreshListView pullToRefreshListView;
    //网络Post请求
    private OkHttpClient okHttpClient = new OkHttpClient();
    //需要数据
    private Context mContext;
    private int pageIndex;//页面下标
    private int ADDP = 0;
    private int STARTPAGE = 1;
    private ArrayList<String> compassdatalist = new ArrayList<>();//TAB NAME
    private ArrayList<String> compassdatamap = new ArrayList<>();//ID
    //需求控件
    private ListView mListView;
    private BackStageSortLvAdapter mLVAdapter;
    //创建fragment需要进入适配器的数据
    List<BackStageBean.DataBean> datalist = new ArrayList<>();


    /**
     * 董传亮 静态工厂
     *
     * @param args
     * @return
     */
    public static BackStageSortFragment newInstance(Bundle args) {

        BackStageSortFragment fragment = new BackStageSortFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();//获取上下文
        this.compassdatalist = getArguments().getStringArrayList("list");//标题按钮的数组
        this.compassdatamap = getArguments().getStringArrayList("map");//id的数组
        pageIndex = getArguments().getInt("index");//偏移量

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backstagedetail, null);//展示listview
        //初始化数据
        initData();
        //初始化控件
        initView(view);
        //绑定适配器
        bindAdapter();
        //绑定下拉刷新
        initPulltoRefrush();
        //设置监听事件
        initListener();

        return view;
    }

    /**
     * @ 董传亮
     * 监听事件跳转
     */
    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  //              Intent intent = new Intent(getActivity(), BackStageDetail.class);
//                String webUrl = datalist.get(position).getRequest_url();
//                Log.i("ddsfec", "initWebView: "+webUrl);
//                intent.putExtra("backurl", webUrl);
                Intent intent=new Intent(getActivity(), SecondDetailActivity.class);
                String postid=datalist.get(position).getPostid();
                intent.putExtra("from","backstage");
                intent.putExtra("id",postid);
                startActivity(intent);

            }
        });
    }

    /**
     * @ 董传亮
     * 下拉刷新
     */
    private void initPulltoRefrush() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);// 设置下拉刷新模式
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase refreshView) {
                //获取每个按钮的id 例如 电影自习室 id是47 index=1；
                String id = compassdatamap.get(pageIndex);
                datalist.clear();
                ADDP = 0;
                int p = STARTPAGE + ADDP;//p=1
                //进行Post请求
                asyncRequest(id, p);
                pullToRefreshListView.onRefreshComplete();
            }
        });

        pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                //获取每个按钮的id 例如 电影自习室 id是47 index=1；
                String id = compassdatamap.get(pageIndex);
                ADDP += 1;
                int p= STARTPAGE + ADDP;
                //进行Post请求
                asyncRequest(id, p);
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }

    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mLVAdapter = new BackStageSortLvAdapter(mContext, datalist);
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
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.backstagesort_show_lv);
        //  mListView= (ListView) view.findViewById(R.id.backstagesort_show_lv);
        mListView = pullToRefreshListView.getRefreshableView();
    }

    /**
     * @ 董传亮
     * 进入Fragment 的数据
     */
    private void chooseData() {
        //获取每个按钮的id 例如 电影自习室 id是47 index=1；
        String id = compassdatamap.get(pageIndex);
        int page = STARTPAGE;
        //进行Post请求
        asyncRequest(id, page);

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
                .add("cateid", id)//添加Post请求的参数
                .add("size", "10")//添加Post请求的参数
                .add("p", page)//添加Post请求的参数
                .build();
        //创建一个Request对象
        Request request = new Request.Builder()
                .url(URLConstants.URL_BACKFRAGMENT) //网络请求地址
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
                    BackStageBean backStageBean = gson.fromJson(result, BackStageBean.class);
                    datalist.addAll(backStageBean.getData());

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