package com.gsfh.myteamwork.vmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gsfh.myteamwork.vmovie.activity.LoginActivity;
import com.gsfh.myteamwork.vmovie.activity.SearchActivity;
import com.gsfh.myteamwork.vmovie.fragment.BackStageFragment;
import com.gsfh.myteamwork.vmovie.fragment.MainFragment;
import com.gsfh.myteamwork.vmovie.fragment.SeriesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * MainActivity中的Fragment列表
     */
    private ArrayList<Fragment> fragmentList;
    private PopupWindow popupWindow;
    private LinearLayout ll_home;
    private LinearLayout ll_series;
    private LinearLayout ll_backstage;
    //存储菜单位置
    private int currentPosition = 0;
    //点击返回键的次数
    private int backCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {

        View view = LayoutInflater.from(this).inflate(R.layout.fragment_main,null);
        View windowView = LayoutInflater.from(this).inflate(R.layout.slide_window,null);
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
     * 初始化Fragment
     * 将创建的Fragment添加到fragmentList中
     * >>>>> 按顺序添加 <<<<<
     * fragment1 ---> MainFragment
     * fragment2 ---> SeriesFragment
     * fragment3 ---> BackStageFragment
     */
    private void initFragment() {

        fragmentList = new ArrayList<>();

        fragmentList.add(new MainFragment());
        fragmentList.add(new SeriesFragment());
        fragmentList.add(new BackStageFragment());
    }

    private void initListener() {
    }

    /**
     * onClick点击事件
     * @param view
     */
    public void showMenu(View view){

        showWindow(view);
    }

    /**
     * 显示弹出窗口
     * @param view
     */
    private void showWindow(View view) {

        View window_view = LayoutInflater.from(this).inflate(R.layout.slide_window,null);
        ll_home = (LinearLayout) window_view.findViewById(R.id.ll_slide_home);
        ll_series = (LinearLayout) window_view.findViewById(R.id.ll_slide_series);
        ll_backstage = (LinearLayout) window_view.findViewById(R.id.ll_slide_backstage);

        //显示当前菜单中选中的位置
        switch (currentPosition){
            case 0:
                ll_home.setSelected(true);
                break;
            case 1:
                ll_series.setSelected(true);
                break;
            case 2:
                ll_backstage.setSelected(true);
                break;
        }

        //显示弹出窗口
        popupWindow = new PopupWindow(window_view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }

    /**
     * onClick点击事件，进入登录界面
     * @param view
     */
    public void login(View view){

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        backCount = 0;
    }

    /**
     * onClick点击事件，切换Fragment
     * @param view
     */
    public void toHome(View view){

        switchFragment(0);
        currentPosition = 0;
        close_menu();
        backCount = 0;
    }
    public void toSeries(View view){

        switchFragment(1);
        currentPosition = 1;
        close_menu();
        backCount = 0;
    }
    public void toBackStage(View view){

        switchFragment(2);
        currentPosition = 2;
        close_menu();
        backCount = 0;
    }

    /**
     * 切换Fragment
     */
    public void switchFragment(int toWhere){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment cur_fragment = fragmentList.get(0);
        switch (currentPosition){
            case 0:
                cur_fragment = fragmentList.get(0);
                break;
            case 1:
                cur_fragment = fragmentList.get(1);
                break;
            case 2:
                cur_fragment = fragmentList.get(2);
                break;
        }

        Fragment enter_fragment = fragmentList.get(toWhere);
        if(!enter_fragment.isAdded()){

            transaction.hide(cur_fragment).add(R.id.fl_container, enter_fragment);
        }else{
            transaction.hide(cur_fragment).show(enter_fragment);
        }
        transaction.commit();
        //更新当前位置
        currentPosition = toWhere;
    }

    /**
     * 点击事件，关闭弹出窗口
     * @param view
     */
    public void closeWindow(View view){

        close_menu();
    }

    private void close_menu() {
        if (null != popupWindow && popupWindow.isShowing()){

            popupWindow.dismiss();
        }
    }

    /**
     * onClick点击事件，进入搜索界面
     * @param view
     */
    public void search(View view){

        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (null != popupWindow && popupWindow.isShowing()){

            popupWindow.dismiss();
        }else {

            backCount++;
            if (backCount==2){

                super.onBackPressed();
            }else {
                Toast.makeText(this,"再按一次返回键退出程序",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
