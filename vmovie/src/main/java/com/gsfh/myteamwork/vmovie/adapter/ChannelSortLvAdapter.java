package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.ChannelSort_HotActivity;
import com.gsfh.myteamwork.vmovie.bean.BackStageBean;
import com.gsfh.myteamwork.vmovie.bean.ChannelSortBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @ 董传亮
 * 专题进入的适配器
 * Created by admin on 2016/7/7.
 */
public class ChannelSortLvAdapter extends BaseAdapter {
    private Context context;
   private List<ChannelSortBean.DataBean> mBeenlist;

    public ChannelSortLvAdapter(ChannelSort_HotActivity channelSort_hotActivity, List<ChannelSortBean.DataBean> sortList) {
        this.mBeenlist = sortList;
        this.context = context;

    }

    @Override
    public int getCount() {
      //  return 10;
        return mBeenlist == null ? 0 : mBeenlist.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHoder hoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_backstagesort_lv_item, parent, false);
            hoder = new viewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.backstagesort_title_show_tv);
            hoder.share = (TextView) convertView.findViewById(R.id.backstagesort_share_tv);
            hoder.collect = (TextView) convertView.findViewById(R.id.backstagesort_collect_tv);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.backstagesort_iv);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }

        String title=mBeenlist.get(position).getTitle();
        String share=mBeenlist.get(position).getShare_num();
        String collect=mBeenlist.get(position).getLike_num();
        String mUrl=mBeenlist.get(position).getImage();

     hoder.title.setText(title);
     hoder.share.setText(share);
     hoder.collect.setText(collect);
        Picasso.with(context).load(mUrl).into(hoder.imageView);
        return convertView;
    }

    class viewHoder {

        private TextView title;
        private TextView share;
        private TextView collect;
        private ImageView imageView;
    }
}
