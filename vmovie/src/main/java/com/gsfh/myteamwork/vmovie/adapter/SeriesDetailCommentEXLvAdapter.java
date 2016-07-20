package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.activity.SeriesDetail;
import com.gsfh.myteamwork.vmovie.bean.CommentBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2016/7/19.
 */
public class SeriesDetailCommentEXLvAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    List<CommentBean.DataBean> mCommentGroupList;
    List<?> mCommentChildList ;
    public SeriesDetailCommentEXLvAdapter(SeriesDetail seriesDetail, List<CommentBean.DataBean> mCommentList) {
        this.mContext=seriesDetail;
        this.mCommentGroupList=mCommentList;

    }

    @Override
    public int getGroupCount() {
        return mCommentGroupList==null ? 0:mCommentGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        mCommentChildList= mCommentGroupList.get(groupPosition).getSubcomment();
        return mCommentChildList==null ? 0:mCommentChildList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        final GroupHolder holder;
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_seriesdetailcomment_explv_groupitem, null);
            holder.textname=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_item_name_tv);
            holder.texttime=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_item_time_tv);
            holder.textcontext=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_item_context_tv);
            holder.textcount=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_item_countup_tv);
            holder.imageView= (ImageView) view.findViewById(R.id.seriesdetailcomment_explv_item_icon_iv);
            view.setTag(holder);
        } else {
            holder= (GroupHolder) view.getTag();
        }
        String name=mCommentGroupList.get(groupPosition).getUserinfo().getUsername();
        String texttime=mCommentGroupList.get(groupPosition).getAddtime();
        String textcontext=mCommentGroupList.get(groupPosition).getContent();
        String textcount=mCommentGroupList.get(groupPosition).getCount_approve();

        String mURL=mCommentGroupList.get(groupPosition).getUserinfo().getAvatar();
        holder.textname.setText(name);
        holder.texttime.setText(texttime);
        holder.textcount.setText(textcount);
        holder.textcontext.setText(textcontext);
        Picasso.with(mContext).load(mURL).into(holder.imageView);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if( mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition)==null){
            return null;
        }
        View view = convertView;
        final ChildHolder holder;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_seriesdetailcomment_explv_childitem, null);
            holder.ctextname=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_childitem_name_tv);
            holder.ctexttime=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_childitem_time_tv);
            holder.ctextcontext=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_childitem_context_tv);
            holder.ctextcount=(TextView) view.findViewById(R.id.seriesdetailcomment_explv_childitem_countup_tv);
            holder.cimageView= (ImageView) view.findViewById(R.id.seriesdetailcomment_explv_childitem_icon_iv);
            view.setTag(holder);
        } else {
            holder= (ChildHolder) view.getTag();
        }
        String cname=mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition).getUserinfo().getUsername();
        String ctexttime=mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition).getAddtime();
        String ctextcontext=mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition).getContent();
        String ctextcount=mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition).getCount_approve();
        String mURL=mCommentGroupList.get(groupPosition).getSubcomment().get(childPosition).getUserinfo().getAvatar();

        holder.ctexttime.setText(ctexttime);
        holder.ctextcontext.setText(ctextcontext);
        holder.ctextcount.setText(ctextcount);
        Picasso.with(mContext).load(mURL).into(holder.cimageView);
        ;
        holder.ctextname.setText(cname);
        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class GroupHolder{
        TextView texttime;
        TextView textcontext;
        TextView textname;
        ImageView imageView;
        TextView textcount;
    }
    class ChildHolder{
        TextView ctexttime;
        TextView ctextcontext;
        TextView ctextname;
        TextView ctextcount;
        ImageView cimageView;
    }
}
