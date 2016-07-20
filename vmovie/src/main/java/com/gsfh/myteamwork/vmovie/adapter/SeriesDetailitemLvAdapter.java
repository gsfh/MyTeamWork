package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;

import com.gsfh.myteamwork.vmovie.activity.SeriesDetail;
import com.gsfh.myteamwork.vmovie.bean.SeriesDetailBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/7.
 */
public class SeriesDetailitemLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<SeriesDetailBean.DataBean.PostsBean.ListBean> mBeenlist;
    private ArrayList<String> tabNameList;
    private SeriesDetailItemListener mSeriesDetailItemListener;
    private Map<String, List<SeriesDetailBean.DataBean.PostsBean.ListBean>> mMap;
    private int isplaying = -1;
    private String mpubmovingplaying = null;
    private String pubplaying = null;
    private String mTitle;//传出的值
    private String mURL;//传出的网页
    private String mID;//传出的网页
    private int startnum = 0;//第一页的值

    public SeriesDetailitemLvAdapter(SeriesDetail seriesDetail2, Map<String, List<SeriesDetailBean.DataBean.PostsBean.ListBean>> mMap, ArrayList<String> tabNameList) {
        this.mSeriesDetailItemListener = seriesDetail2;
        this.mMap = mMap;
        this.tabNameList = tabNameList;
        this.mContext = seriesDetail2;
    }
    /**
     * @ 董传亮
     * 传动修改适配器参数
     */
    public void setParams(int position, String realtitle, String msouerlink, String id) {
        isplaying = position;//正在播放按钮状态
       mTitle = realtitle;
        mURL = msouerlink;
        mID = id;
        mSeriesDetailItemListener.itemClick(mTitle, mURL, mID);
        this.notifyDataSetChanged();
    }
    //设置页面参数修改关键值
    public void setStartnum(int startnum,String pubmovingplaying ) {
        this.startnum = startnum;
        this.mpubmovingplaying=pubmovingplaying;

    }
    public void setPubplaying(String mpubmovingplaying) {

        this.pubplaying=mpubmovingplaying;
    }

    @Override
    public int getCount() {
        mBeenlist = mMap.get(tabNameList.get(startnum));
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewHoder hoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_seriesdetailitem_lv_item, null);
            hoder = new viewHoder();
            hoder.title = (TextView) convertView.findViewById(R.id.seriesdetailitem_title_lv_tv);
            hoder.playing = (TextView) convertView.findViewById(R.id.seriesdetailitem_playing_lv_tv);//正在播放的按钮隐藏
            hoder.duration = (TextView) convertView.findViewById(R.id.seriesdetailitem_duration_lv_im);
            hoder.updata = (TextView) convertView.findViewById(R.id.seriesdetailitem_update_lv_tv);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.seriesdetailitem_lv_im);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHoder) convertView.getTag();
        }


        //当前页面的播放状态保存
        if (pubplaying == tabNameList.get(startnum) && isplaying == position) {
            hoder.playing.setVisibility(View.VISIBLE);
        } else {
            hoder.playing.setVisibility(View.INVISIBLE);
        }
        //初始化特殊状态记录播放状态
        if(position==0 && startnum==0 && isplaying==-1){
            hoder.playing.setVisibility(View.VISIBLE);
        }

        final String title = mBeenlist.get(position).getTitle();//85（集）后面的
        String num = mBeenlist.get(position).getNumber();//85(集)
        final String realtitle = "第" + num + "集" + " " + title;
        String playing = "正在播放";
        String duration = mBeenlist.get(position).getDuration();
        int a=Integer.valueOf(duration).intValue();
        String time=a/60+":"+a%60;
        String updata = mBeenlist.get(position).getAddtime();
        final String mUrl = mBeenlist.get(position).getThumbnail();
        final String msouerlink = mBeenlist.get(position).getSeries_postid();
        final String id = mBeenlist.get(position).getSeries_postid();


        hoder.title.setText(realtitle);
        hoder.playing.setText(playing);
        hoder.duration.setText(time);
        hoder.updata.setText(updata);
        Picasso.with(mContext).load(mUrl).placeholder(R.mipmap.ic_launcher).into(hoder.imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPubplaying( mpubmovingplaying);
                setParams(position, realtitle, msouerlink, id);
            }
        });
        return convertView;
    }

    //暴露值接口
    public interface SeriesDetailItemListener {
        void itemClick(String mTitle, String mURL, String mID);
    }
    class viewHoder {
        private TextView title;
        private TextView playing;
        private TextView duration;
        private TextView updata;
        private ImageView imageView;
    }
}
