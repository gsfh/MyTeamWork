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
import com.gsfh.myteamwork.vmovie.bean.ChannelDetailBean;
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
    private List<ChannelDetailBean.DataBean> mBeenlist;
    private OnitemClickListener onitemClickListener;

    public ChannelSortLvAdapter(ChannelSort_HotActivity channelSort_hotActivity, List<ChannelDetailBean.DataBean> sortList) {
        this.mBeenlist = sortList;
        this.context = channelSort_hotActivity;
    }

    public void setOnitemClickListener(OnitemClickListener onitemClickListener){
       this.onitemClickListener=onitemClickListener;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_channelsort_lv_item, parent, false);
            hoder = new viewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.item_channelsort_lv_item_tv);
            hoder.time = (TextView) convertView.findViewById(R.id.item_channelsort_time_item_tv);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.item_channelsort_lv_item_im);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }

        String title = mBeenlist.get(position).getTitle();
        String cate = mBeenlist.get(position).getCates().get(0).getCatename();
        String duration = mBeenlist.get(position).getDuration();
        Integer time = Integer.valueOf(duration);
        String result = cate + " / " + time / 60 + "'" + time % 60+"''";
        String mUrl = mBeenlist.get(position).getImage();

        hoder.title.setText(title);
        hoder.time.setText(result);
        Picasso.with(context).load(mUrl).into(hoder.imageView);
      final   String postid=mBeenlist.get(position).getPostid();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.itemClick(postid);
            }
        });
        return convertView;
    }





    class viewHoder {

        private TextView title;
        private TextView time;
        private ImageView imageView;
    }

    public interface OnitemClickListener{
        void itemClick(String postid);
    }
}
