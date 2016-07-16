package com.gsfh.myteamwork.vmovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.gsfh.myteamwork.vmovie.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 董传亮
 * 系列 第一次跳转进去后 的列表分页
 * Created by admin on 2016/7/13.
 */
public class SeriesDetailItemVPAdapter extends FragmentPagerAdapter implements SlidingTabLayout.TabItemName {
    private List<Fragment> fragmentList;
    private ArrayList<String> titleDatas;


    public SeriesDetailItemVPAdapter(ArrayList<String> datas , List<Fragment> fragmentList, FragmentManager fm ){
        super(fm);
        this.fragmentList=fragmentList;
        this.titleDatas=datas;
    }
    @Override
    public Fragment getItem(int position) {
//        return fragmentList==null ? null : fragmentList.get(position);
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {

      //  return fragmentList == null ? 0 :fragmentList.size();
        return titleDatas == null ? 0 :titleDatas.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return  titleDatas==null ? null : titleDatas.get(position);
//     //   return  titleDatas.get(position);
//    }

    @Override
    public String getTabName(int position) {
        return titleDatas.get(position);
    }


}

