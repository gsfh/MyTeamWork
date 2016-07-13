package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gsfh.myteamwork.vmovie.R;

import java.util.List;

/**
 * @ 董传亮
 * 首页的频道页面的显示 RCY 适配器
 * Created by admin on 2016/7/8.
 */
public class ChannelRCYAdapter extends RecyclerView.Adapter<ChannelRCYAdapter.Holder>{
  private Context mContext;
  //  private List
   public ChannelRCYAdapter(Context context ){
       this.mContext=context;

   }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_channel_rcyv_item, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
       if(holder!=null) {
           holder.imageView.setImageResource(R.drawable.video_lan_share);
       }
    }


    @Override
    public int getItemCount() {
        return 10;
    }



    class Holder extends RecyclerView.ViewHolder {
   private ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.item_channel_show_im);
        }
    }
}
