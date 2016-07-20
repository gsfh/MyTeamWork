package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

public class ViewHolder {

    private View mConvertView;
    private int position;
    private SparseArray<View> viewArray;

    public View getmConvertView() {
        return mConvertView;
    }

    public void setmConvertView(View mConvertView) {
        this.mConvertView = mConvertView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ViewHolder(Context context, int position, int layoutId, View convertView) {
        this.position = position;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        viewArray = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, int position, int layoutId, View convertView) {
        if (convertView == null) {
            return new ViewHolder(context, position, layoutId, convertView);
        } else {
            ViewHolder holderM = (ViewHolder) convertView.getTag();
            holderM.position = position;
            return holderM;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {

        View view = viewArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

}
