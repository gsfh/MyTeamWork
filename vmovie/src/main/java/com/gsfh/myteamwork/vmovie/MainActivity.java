package com.gsfh.myteamwork.vmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    private int curIndex;
    private int preIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initView() {

        //初始化RadioGroup
        View windowView = LayoutInflater.from(this).inflate(R.layout.slide_window,null);
        radioGroup = (RadioGroup) windowView.findViewById(R.id.slide_guide_rb);

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
        Bundle bundle=new Bundle();
     //   Fragment fragment1 =  ;

        fragmentList = new ArrayList<>();
        //在此添加Fragment
       fragmentList.add(BackStageFragment.newInstance(bundle));
//        fragmentList.add(ChannelFragment.newInstance(bundle));//第一页右边硬编码的，数据都没解析
      //  fragmentList.add(fragment1);//第二页已经调好，按钮暂时没写，订阅人数字体有差异，
        fragmentList.add(SeriesFragment.newInstance(bundle));//第二页已经调好，按钮暂时没写，订阅人数字体有差异，
        fragmentList.add(SeriesFragment.newInstance(bundle));//第二页已经调好，按钮暂时没写，订阅人数字体有差异，

    }

    private void initListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for(int i=0;i<rbArray.length;i++){

                    if(checkedId == rbArray[i].getId()){
                        switchFragment(i);
                    }
                }
            }
        });
    }

    /**
     * 切换Fragment
     */
    public void switchFragment(int i){
        //当前
        curIndex = i;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment cur_fragment = fragmentList.get(curIndex);
        Fragment pre_fragment = fragmentList.get(preIndex);
        if(!cur_fragment.isAdded()){

            transaction.hide(pre_fragment).add(R.id.fl_container, cur_fragment);
        }else{
            transaction.hide(pre_fragment).show(cur_fragment);
        }
        transaction.commit();
        //保留上一个位置信息
        preIndex=curIndex;

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

        radioGroup.check(preIndex);

        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);

        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

    }

    /**
     * 点击事件，关闭弹出窗口
     * @param view
     */
    public void closeWindow(View view){

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
}
