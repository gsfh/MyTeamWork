package com.gsfh.myteamwork.vmovie.util;

/**
 * Created by GSFH on 2016-7-13.
 */
public class URLConstants {

      //首页头部图片轮播
      public static final String LATEST_BANNER_URL = "http://app.vmoiver.com/apiv3/index/getBanner";

      //首页最新
      public static final String LATEST_URL = "http://app.vmoiver.com/apiv3/post/getPostByTab";

      //首页最新详情
      public static final String LATEST_DETAIL_URL = "http://app.vmoiver.com/apiv3/post/view";

      //系列    post
      public static final String  URL_SERIES ="http://app.vmoiver.com/apiv3/series/getList";
      public static final String  URL_SERIESVIEW ="http://app.vmoiver.com/apiv3/series/view";
    //系列跳转进去后的视频分页列表
    public static final String  URL_SERIESVEDIOLIST ="http://app.vmoiver.com/apiv3/series/getVideo";
    //系列跳进去后的聊天列表
      public static final String  URL_SERIESCOMMENT ="http://app.vmoiver.com/apiv3/comment/getLists";













      //幕后文章
      public static final String  URL_BACKTITLE ="http://app.vmoiver.com/apiv3/backstage/getCate";

      //幕后文章的数据内容接口
      public static final String  URL_BACKFRAGMENT ="http://app.vmoiver.com/apiv3/backstage/getPostByCate";

}
