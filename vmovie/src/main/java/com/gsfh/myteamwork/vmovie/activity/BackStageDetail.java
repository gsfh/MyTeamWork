package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.bean.MainDetailNewViewBean;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @ 董传亮
 * 幕后页面的跳转detail
 * Created by admin on 2016/7/14.
 */
public class BackStageDetail extends AppCompatActivity {
    private BridgeWebView webView;
    private   JCVideoPlayerStandard jcVideoPlayer;
    private String mURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstagedetail);
        //获取上个页面的参数
        initData();
        //初始化全部View
        initView();




    }

    /**
     * @ 董传亮
     * 初始化所有控件
     */
    private void initView() {
        //播放器
        initVideoView();

        //初始化网页展示webview
        initWebView();
    }

    /**
     * @ 董传亮
     * 初始化播放器
     */
    private void initVideoView() {
        jcVideoPlayer = (JCVideoPlayerStandard) findViewById(R.id.activity_backstagedetail_videoplayer_standard);
    }

    /**
     * @ 董传亮
     * 获取上个页面的网络数据
     */
    private void initData() {
        Intent intent=getIntent();
       mURL=intent.getStringExtra("backurl");

    }

    /**
     * @ 董传亮
     * 初始化当前页面需要控件
     */
    private void initWebView() {
        webView= (BridgeWebView) findViewById(R.id.activity_backstagedetail_bwebview);
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//            }
//        });

        webView.getSettings().setJavaScriptEnabled(true);
        if (mURL!=null){

            webView.loadUrl(mURL);

        }
        webView.registerHandler("handlerVideo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //data ---->  {"id":3958,"type":0}
                if (null == data) {
                    return;
                }
                Log.i("ddsfec", "handler: "+data);
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        jcVideoPlayer.releaseAllVideos();
    }

}
