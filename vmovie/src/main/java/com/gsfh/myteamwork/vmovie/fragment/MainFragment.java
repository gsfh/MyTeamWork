package com.gsfh.myteamwork.vmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.MainBannerBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GSFH on 2016-7-13.
 */
public class MainFragment extends Fragment {

    private ConvenientBanner convenientBanner;
    private List<MainBannerBean.DataBean> bannerDataList = new ArrayList<>();
    private List<String> bannerUrlList = new ArrayList<>();
    private PullToRefreshExpandableListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,null);
        listView = (PullToRefreshExpandableListView) view.findViewById(R.id.pe_lv_main);

        View bannerView = inflater.inflate(R.layout.main_header_view,null);
        convenientBanner = (ConvenientBanner) bannerView.findViewById(R.id.convenientBanner);


        initData();

        return view;
    }

    private void initData() {

        OkHttpTool.newInstance().start(URLConstants.MAIN_BANNER_URL).callback(new IOKCallBack() {
            @Override
            public void success(String result) {

                if (null == result){
                    return;
                }

                Gson gson = new Gson();
                MainBannerBean bannerBean = gson.fromJson(result,MainBannerBean.class);
                bannerDataList.addAll(bannerBean.getData());

                for (MainBannerBean.DataBean data : bannerDataList) {

                    bannerUrlList.add(data.getImage());
                }

                initBanner();
            }
        });
    }

    private void initBanner() {

        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {

            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        },bannerUrlList)
                .setPageIndicator(new int[]{R.drawable.main_header_dot_n,R.drawable.main_header_dot_s})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

    }


    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

}
