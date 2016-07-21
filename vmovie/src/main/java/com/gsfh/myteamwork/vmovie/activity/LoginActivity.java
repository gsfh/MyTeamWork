package com.gsfh.myteamwork.vmovie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gsfh.myteamwork.vmovie.R;

/**
 * @ 董传亮
 * 登录界面
 * Created by admin on 2016/7/17.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void back(View view){
        onBackPressed();
    }
}
