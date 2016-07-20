package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.SearchBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GSFH on 2016-7-19.
 */
public class SearchResultAdapter extends CommonAdapter<SearchBean.DataBean> {

    public SearchResultAdapter(Context context, int layoutId, List<SearchBean.DataBean> list) {
        super(context, layoutId, list);
    }

    @Override
    public void convert(ViewHolder holder, SearchBean.DataBean bean) {

        ImageView imageView = holder.getView(R.id.search_list_item_movie_image);
        TextView play_time = holder.getView(R.id.search_item_play_time_tv);
        TextView title = holder.getView(R.id.search_item_title);
        TextView rank = holder.getView(R.id.search_item_rank);
        TextView share_counts = holder.getView(R.id.search_item_share_counts);

        Picasso.with(context).load(bean.getImage()).into(imageView);
        play_time.setText(bean.getDuration());
        title.setText(bean.getTitle());
        rank.setText(bean.getRating()+"分");
        share_counts.setText(bean.getShare_num());
    }
}
