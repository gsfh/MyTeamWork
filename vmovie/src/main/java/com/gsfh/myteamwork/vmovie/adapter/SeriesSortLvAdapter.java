package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;

import com.gsfh.myteamwork.vmovie.bean.SeriesBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 董传亮
 * 系列页面的适配器
 * Created by admin on 2016/7/7.
 */
public class SeriesSortLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<SeriesBean.DataBean> mList=new ArrayList<>();


    //  final int a=View.TEXT_ALIGNMENT_CENTER;
    public SeriesSortLvAdapter(Context context) {

        this.mContext = context;


    }

    @Override
    public int getCount() {
        return mList==null? 0 : mList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewHoder hoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_seriesdetail_lv_item, null);
            hoder = new viewHoder();

            hoder.tabs = (TabLayout) convertView.findViewById(R.id.seriesdetail_tab_titles_tbll);
            hoder.viewPager = (ViewPager) convertView.findViewById(R.id.seriesdetail_tab_page_show_vp);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }

        String peoplenum=mList.get(position).getFollower_num();
        String mUrl=mList.get(position).getImage();


        return convertView;
    }

    class viewHoder {

        private TabLayout tabs;
        private ViewPager viewPager;
    }
}
