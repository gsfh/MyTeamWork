package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.LatestBean;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by GSFH on 2016-7-14.
 */
public class LatestAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private Map<String ,List<LatestBean.DataBean>> mData;
    private List<String> mGroupList;
//    List<LatestBean.DataBean> mChildList;

    public LatestAdapter(Context context, Map<String ,List<LatestBean.DataBean>> data,List<String> groupList){

        mContext = context;
        mData = data;
        mGroupList = groupList;
    }

    @Override
    public int getGroupCount() {
        Set<String> keySet = mData.keySet();
        return keySet == null ? 0 : keySet.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {

        String key = mGroupList.get(groupPosition);
        List<LatestBean.DataBean> dataList = mData.get(key);
//        mChildList = dataList;
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
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
        GroupViewHolder groupViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_latest_date, null);
            groupViewHolder = new GroupViewHolder(view);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        groupViewHolder.date.setText(mGroupList.get(groupPosition));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view = convertView;
        ChildViewHolder childViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_latest_lv, null);
            childViewHolder = new ChildViewHolder(view);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        String key = mGroupList.get(groupPosition);
        List<LatestBean.DataBean> dataList = mData.get(key);
        LatestBean.DataBean  bean = dataList.get(childPosition);

        Picasso.with(mContext).load(bean.getImage()).into(childViewHolder.imageView);
        childViewHolder.title.setText(bean.getTitle());
        childViewHolder.sort.setText(bean.getCates().get(0).getCatename());
        childViewHolder.time.setText(bean.getDuration());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder{

        TextView date;

        public GroupViewHolder(View view){
            view.setTag(this);
            date = (TextView) view.findViewById(R.id.tv_latest_date);
        }

    }

    class ChildViewHolder{

        ImageView imageView;
        TextView title;
        TextView sort;
        TextView time;

        public ChildViewHolder(View view){
            view.setTag(this);
            imageView = (ImageView) view.findViewById(R.id.iv_latest_image);
            title = (TextView) view.findViewById(R.id.tv_latest_title);
            sort = (TextView) view.findViewById(R.id.tv_latest_sort);
            time = (TextView) view.findViewById(R.id.tv_latest_video_play_time);
        }
    }
}
