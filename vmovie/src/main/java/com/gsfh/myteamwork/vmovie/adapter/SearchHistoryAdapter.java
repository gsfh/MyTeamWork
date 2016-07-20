package com.gsfh.myteamwork.vmovie.adapter;

import android.content.Context;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.dao.Customer;

import java.util.List;

/**
 * Created by GSFH on 2016-7-20.
 */
public class SearchHistoryAdapter extends CommonAdapter<Customer>{

    public SearchHistoryAdapter(Context context, int layoutId, List<Customer> list) {
        super(context, layoutId, list);
    }

    @Override
    public void convert(ViewHolder holder, Customer bean) {

        TextView his_text = holder.getView(R.id.search_history);
        his_text.setText(bean.getCustomerName());
    }
}
