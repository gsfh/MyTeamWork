package com.gsfh.myteamwork.vmovie.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gsfh.myteamwork.vmovie.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private MyViewPagerAdapter mViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.main_fragment_tab);
        mViewPager = (ViewPager) view.findViewById(R.id.main_fragment_vp);

        //1.准备数据源
        initData();
        initTitleDatas();
        //2.创建适配器
        mViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        //3.关联适配器
        mViewPager.setAdapter(mViewPagerAdapter);
        //4.TabLayout初始化
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void initData() {

        fragmentList.add(LatestFragment.newInstance(mTabLayout));
        fragmentList.add(new ChannelFragment());
    }

    private void initTitleDatas() {

        mTitleDatas.add("最新");
        mTitleDatas.add("频道");
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleDatas.get(position);
        }
    }
}
