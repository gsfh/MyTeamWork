package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;

import com.gsfh.myteamwork.vmovie.activity.SeriesDetail;
import com.gsfh.myteamwork.vmovie.bean.SeriesBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 董传亮
 * 系列页面的适配器
 * Created by admin on 2016/7/7.
 */
public class SeriesLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<SeriesBean.DataBean> mList = new ArrayList<>();
    private FragmentActivity activity;

    //  final int a=View.TEXT_ALIGNMENT_CENTER;
    public SeriesLvAdapter(Context context, List<SeriesBean.DataBean> mList, FragmentActivity activity) {
        this.mList = mList;
        this.mContext = context;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_series_lv_item, parent,false);
            hoder = new viewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.item_series_title_tv);
            hoder.num = (TextView) convertView.findViewById(R.id.item_series_num_tv);
            hoder.peoplenum = (TextView) convertView.findViewById(R.id.item_series_peoplenum_tv);
            hoder.context = (TextView) convertView.findViewById(R.id.item_series_context_tv);
            // hoder.button = (Button) convertView.findViewById(R.id.item_series_clicked_btn);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.item_series_show_im);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }
        String title = mList.get(position).getTitle();
        String num = mList.get(position).getUpdate_to();
        String peoplenum = mList.get(position).getFollower_num();
        final String context = mList.get(position).getContent();
        String mUrl = mList.get(position).getImage();
        hoder.title.setText(title);
        hoder.num.setText(num);
        hoder.peoplenum.setText(peoplenum);
        hoder.context.setText(context);
        Picasso.with(mContext).load(mUrl).into(hoder.imageView);
        //跳转的监听事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, SeriesDetail.class);
                intent.putExtra("seriesid", mList.get(position).getSeriesid());
                intent.putExtra("readcount", mList.get(position).getFollower_num());
                activity.startActivity(intent);


            }
        });


        return convertView;
    }

    class viewHoder {

        private TextView title;
        private TextView num;
        private TextView peoplenum;
        private TextView context;
        //  private Button button;
        private ImageView imageView;
    }
}
