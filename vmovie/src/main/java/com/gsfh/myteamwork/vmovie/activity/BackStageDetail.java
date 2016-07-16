package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.gsfh.myteamwork.vmovie.R;

/**
 * @ 董传亮
 * 幕后页面的跳转detail
 * Created by admin on 2016/7/14.
 */
public class BackStageDetail extends AppCompatActivity {
    private BridgeWebView webView;
    private String mURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstagedetail);
        //获取上个页面的参数
        initData();
        //初始化webView
        initWebView();



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
        webView= (BridgeWebView) findViewById(R.id.activity_backstagedetail_webv);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
        if (mURL!=null){webView.loadUrl(mURL);}
    }
}
