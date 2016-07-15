package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.BackStageBean;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2016/7/7.
 */
public class SeriesDetailitemLvAdapter extends BaseAdapter {
    private Context mContext;
   private List<SeriesDetailBean.DataBean.PostsBean.ListBean> mBeenlist;


    public SeriesDetailitemLvAdapter(Context mContext, List<SeriesDetailBean.DataBean.PostsBean.ListBean> mlist) {
        this.mContext=mContext;
        this.mBeenlist=mlist;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_seriesdetailitem_lv_item, parent, false);
            hoder = new viewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.seriesdetailitem_title_lv_tv);
            hoder.playing = (TextView) convertView.findViewById(R.id.seriesdetailitem_playing_lv_tv);
            hoder.duration = (TextView) convertView.findViewById(R.id.seriesdetailitem_duration_lv_im);
            hoder.updata = (TextView) convertView.findViewById(R.id.seriesdetailitem_update_lv_tv);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.seriesdetailitem_lv_im);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }

        String title=mBeenlist.get(position).getTitle();
        String playing="正在播放";
        String duration=mBeenlist.get(position).getDuration();
        String updata=mBeenlist.get(position).getAddtime();
        String mUrl=mBeenlist.get(position).getThumbnail();


     hoder.title.setText(title);
     hoder.playing.setText(playing);
     hoder.duration.setText(duration);
     hoder.updata.setText(updata);
Picasso.with(mContext).load(mUrl).into(hoder.imageView);
        return convertView;
    }

    class viewHoder {

        private TextView title;
        private TextView playing;
        private TextView duration;
        private TextView updata;
        private ImageView imageView;

    }
}
