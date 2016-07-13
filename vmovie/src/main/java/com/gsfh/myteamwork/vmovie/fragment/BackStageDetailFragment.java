package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.BackStageDetailLvAdapter;
import com.gsfh.myteamwork.vmovie.adapter.BackStageVPAdapter;
import com.gsfh.myteamwork.vmovie.been.BeenBackStage;
import com.gsfh.myteamwork.vmovie.been.BeenBackStageTitle;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 董传亮
 * 幕后文章页面  第三页
 * Created by admin on 2016/7/13.
 */
public class BackStageDetailFragment extends Fragment {
   //需要数据
   private Context mContext;
    private int  pageIndex;
    private ArrayList<String> compassdatalist=new ArrayList<>();
    private ArrayList<String> compassdatamap=new ArrayList<>();
   //需求控件
    private ListView mListView;
    private BackStageDetailLvAdapter mLVAdapter;
    //创建fragment需要进入适配器的数据
    List<BeenBackStage.DataBean>datalist=new ArrayList<>();


    /**
     * 董传亮 静态工厂
     * @param args
     * @return
     */
    public static BackStageDetailFragment newInstance(Bundle args){

          BackStageDetailFragment fragment=new BackStageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getContext();//获取上下文
        this.compassdatalist=getArguments().getStringArrayList("list");//标题按钮的数组
        this.compassdatamap=getArguments().getStringArrayList("map");//id的数组
        pageIndex=getArguments().getInt("index");//偏移量

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_backstagedetail,null);//展示listview
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
        mLVAdapter=new BackStageDetailLvAdapter(mContext,datalist);
        mListView.setAdapter(mLVAdapter);
    }

    /**
     * @ 董传亮
     * 数据加载 网络数据
     */
    private void initData() {
        String mUrl= URLConstants.URL_BACKFRAGMENT;
        OkHttpTool.newInstance().start(mUrl).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                if (null != result) {
                    Gson gson = new Gson();
                    BeenBackStage beenBackStage = gson.fromJson(result, BeenBackStage.class);
                  //动态更改数据
                  chooseData(beenBackStage);
                    mLVAdapter.notifyDataSetChanged();
                  }

                }
            });
    }




    /**
     * @ 董传亮
     * 初始化当前页控件
     */
    private void  initView(View view) {
          mListView= (ListView) view.findViewById(R.id.backstagedetail_show_lv);
    }

    /**
     * @ 董传亮
     * 进入Fragment 的数据
     */
    private void chooseData( BeenBackStage beenBackStage) {
        if(pageIndex==0) {
            datalist.clear();
            datalist.addAll(beenBackStage.getData());
        }else {
      //获取每个按钮的id 例如 电影自习室 id是47 index=1；
        String id=compassdatamap.get(pageIndex);
            datalist.clear();
       for(int i=0;i<beenBackStage.getData().size();i++ ){
           if(beenBackStage.getData().get(i).getCates().get(0).getCateid().equals(id)){
               datalist.add(beenBackStage.getData().get(i));
           }

       }

        }
    }

}
