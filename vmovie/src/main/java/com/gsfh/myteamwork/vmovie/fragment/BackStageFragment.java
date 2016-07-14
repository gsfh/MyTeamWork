package com.gsfh.myteamwork.vmovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.adapter.BackStageVPAdapter;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.BackStageTitleBean;
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
public class BackStageFragment extends Fragment {
    //当前界面布局控件
    private ViewPager mViewPager;
    private TabLayout mTablaylout;
   // private SlidingTabLayout mTablaylout;
   //用于切换的fragment
    private List<Fragment>  fragmentList = new ArrayList<>();;
    //用于切换的按钮名字，需添加右侧斜杠
    public static ArrayList<String> tablist = new ArrayList<>();
    public static ArrayList<String> tabmap = new ArrayList<>();

     private BackStageVPAdapter mVPAdapter;
    /**
     * 董传亮 静态工厂
     * @param args
     * @return
     */
    public static  BackStageFragment newInstance(Bundle args){

          BackStageFragment fragment=new BackStageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_backstage,null);//一个tablay  和  vp  换页组合

        //初始化控件
        initView(view);
        //初始化数据
        initData();

        //绑定适配器
        bindAdapter();

                return view;
    }

    /**
     * @ 董传亮
     * 绑定适配器
     */
    private void bindAdapter() {
        mVPAdapter=new BackStageVPAdapter(tablist,fragmentList,getChildFragmentManager());
        mViewPager.setAdapter(mVPAdapter);
        mTablaylout.setupWithViewPager(mViewPager);
        mTablaylout.setTabGravity(TabLayout.GRAVITY_FILL);
//        mTablaylout.setViewPager(mViewPager);
//        mTablaylout.setDividerColors(Color.BLUE);
//        mTablaylout.setSelectedIndicatorColors(Color.GREEN);
//        mTablaylout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return Color.RED;
//            }
//
//            @Override
//            public int getDividerColor(int position) {
//                return Color.RED;
//            }
//        });


    }

    /**
     * @ 董传亮
     * 数据加载
     */
    private void initData() {

        //初始化网络数据，并刷新
        initUrlDATA();

    }

    /**
     * @ 董传亮
     * 初始化网络数据
     */
    private void initUrlDATA() {
        String mUrl= URLConstants.URL_BACKTITLE;
        OkHttpTool.newInstance().start(mUrl).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                if(null!=result){
                    Gson gson=new Gson();
                    BackStageTitleBean beenBackStageTitle=gson.fromJson(result,BackStageTitleBean.class);
                   for (int i=0;i<beenBackStageTitle.getData().size();i++){
                       tablist.add(beenBackStageTitle.getData().get(i).getCatename()+"   |");
                    tabmap.add(beenBackStageTitle.getData().get(i).getCateid());}
                }
                //初始化fragment列表
                initFragment();
                mVPAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * @ 董传亮
     * 加载detail子fragment
     */
    private void initFragment() {

        Bundle bundle;

        for (int i = 0; i < tablist.size(); i++) {
            bundle = new Bundle();
            bundle.putStringArrayList("list", tablist);
            bundle.putInt("index", i);
            bundle.putStringArrayList("map", tabmap);
            fragmentList.add(BackStageSortFragment.newInstance(bundle));

        }

    }

    /**
     * @ 董传亮
     * 初始化当前页控件
     */
    private void  initView(View view) {
           mViewPager= (ViewPager) view.findViewById(R.id.backstage_tab_page_show_vp);
           mTablaylout= (TabLayout) view.findViewById(R.id.backstage_tab_titles_tbll);
          // mTablaylout= (SlidingTabLayout) view.findViewById(R.id.backstage_tab_titles_tbll);
    }
}
