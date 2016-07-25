package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.MainActivity;
import com.gsfh.myteamwork.vmovie.R;

/**
 * Created by admin on 2016/7/22.
 */
public class StartActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        TextView textView= (TextView) findViewById(R.id.start_scale_background_tv);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.scale);
        textView.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentonce=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intentonce);
                StartActivity.this.finish();
            }
        },1000);
    }

}
