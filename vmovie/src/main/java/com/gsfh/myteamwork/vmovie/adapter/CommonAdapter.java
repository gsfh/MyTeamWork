package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by GSFH on 2016-6-21.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> list;
    protected int layoutId;

    public CommonAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(
                context, position, layoutId, convertView);
        convert(holder, getItem(position));
        return holder.getmConvertView();
    }

    public abstract void convert(ViewHolder holder, T bean);

}
