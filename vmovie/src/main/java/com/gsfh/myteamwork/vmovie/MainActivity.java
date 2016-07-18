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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gsfh.myteamwork.vmovie.activity.FirstDetailActivity;
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
    private RadioGroup radioGroup;
    private RadioButton[] rbArray;
    private ImageView closeIv;
    private int preFragmentTag = 0;
    private ImageView sideSelectMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {

        sideSelectMenu = (ImageView) findViewById(R.id.main_side_select_menu_iv);

        View windowView = LayoutInflater.from(this).inflate(R.layout.slide_window,null);

        closeIv = (ImageView) windowView.findViewById(R.id.slide_close_iv);
        //初始化RadioGroup
        radioGroup = (RadioGroup) windowView.findViewById(R.id.slide_guide_rg);

        rbArray = new RadioButton[radioGroup.getChildCount()];

        for (int i = 0; i < rbArray.length; i++) {

            rbArray[i] = (RadioButton) radioGroup.getChildAt(i);
        }

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
        Bundle bundle=new Bundle();
        fragmentList.add(SeriesFragment.newInstance(bundle));
        fragmentList.add(BackStageFragment.newInstance(bundle));

    }

    private void initListener() {

        sideSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for(int i=0;i<rbArray.length;i++){

                    if(checkedId == rbArray[i].getId()){
//                        switchFragment(i);
                        close();
                    }
                }
            }
        });
    }

    /**
     * 显示弹出窗口
     * @param view
     */
    private void showWindow(View view) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.slide_window,null);

//        radioGroup.check();

        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);

        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

    }


    public void toHome(View view){

        switchFragment(0);
        close();
    }

    public void toSeries(View view){

        switchFragment(1);
        close();
    }

    public void toBackStage(View view){

        switchFragment(2);
        close();
    }

    /**
     * 切换Fragment
     */
    public void switchFragment(int curFragmentTag){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment pre_fragment = fragmentList.get(0);
        switch (preFragmentTag){
            case 0:
                pre_fragment = fragmentList.get(0);
                break;
            case 1:
                pre_fragment = fragmentList.get(1);
                break;
            case 2:
                pre_fragment = fragmentList.get(2);
                break;
        }

        Fragment cur_fragment = fragmentList.get(curFragmentTag);
        if(!cur_fragment.isAdded()){

            transaction.hide(pre_fragment).add(R.id.fl_container, cur_fragment);
        }else{
            transaction.hide(pre_fragment).show(cur_fragment);
        }
        transaction.commit();

        preFragmentTag = curFragmentTag;
    }

    /**
     * 点击事件，关闭弹出窗口
     * @param view
     */
    public void closeWindow(View view){

        close();
    }

    private void close() {
        if (null != popupWindow && popupWindow.isShowing()){

            popupWindow.dismiss();
        }
    }

    @Override
    public void onBackPressed() {

        if (null != popupWindow && popupWindow.isShowing()){

            popupWindow.dismiss();
        }else {
            super.onBackPressed();
        }
    }

    public void search(View view){

//        Intent intent = new Intent(MainActivity.this, FirstDetailActivity.class);
//        intent.putExtra("id","3958");
//        startActivity(intent);
    }
}
