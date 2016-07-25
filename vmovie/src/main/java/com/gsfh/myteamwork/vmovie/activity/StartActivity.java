package com.gsfh.myteamwork.vmovie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsfh.myteamwork.vmovie.MainActivity;
import com.gsfh.myteamwork.vmovie.R;

/**
 * Created by admin on 2016/7/22.
 */
public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        ImageView bg = (ImageView) findViewById(R.id.start_scale_background_iv);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        bg.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

}
