package com.gsfh.myteamwork.vmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.gsfh.myteamwork.vmovie.fragment.MainFragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * MainActivity中的Fragment列表
     */
    private ArrayList<Fragment> fragmentList;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

        initFragment();
        //加载Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //添加第一个默认的Fragment
        transaction.add(R.id.fl_container,fragmentList.get(0));
        transaction.commit();

    }

    /**
     * 初始化Fragment,将创建的Fragment添加到fragmentList中
     */
    private void initFragment() {

        //在此创建Fragment
        Fragment fragment1 = new MainFragment();

        fragmentList = new ArrayList<>();
        //在此添加Fragment
        fragmentList.add(fragment1);

    }

    /**
     * 切换Fragment
     */
    public void switchFragment(){

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();


    }

    /**
     * slide_window的点击事件
     * @param view
     */
    public void select(View view){

        showWindow(view);
    }

    /**
     * 显示弹出窗口
     * @param view
     */
    private void showWindow(View view) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.slide_window,null);

        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);

        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

    }

    @Override
    public void onBackPressed() {

        if (null != popupWindow && popupWindow.isShowing()){

            popupWindow.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
