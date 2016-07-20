package com.gsfh.myteamwork.vmovie.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SeriesDetailitemLvAdapter;

import java.util.ArrayList;


/**
 * Created by moon.zhong on 2015/3/9.
 */
public class SlidingTabLayout extends LinearLayout {
    /*默认的页卡颜色*/
    private final int DEFAULT_INDICATOR_COLOR = 0xffff00ff;
    /*默认分割线的颜色*/
    private final int DEFAULT_DIVIDER_COLOR = 0xff000000;
    /*默认title字体的大小*/
    private final int DEFAULT_TEXT_SIZE = 16;
    /*默认padding*/
    private final int DEFAULT_TEXT_PADDING = 16;
    /*divider默认的宽度*/
    private final int DEFAULT_DIVIDER_WIDTH = 1;
    /*indicator 的高度*/
    private final int DEFAULT_INDICATOR_HEIGHT = 5;
    /*底部线条的高度默认值*/
    private final int DEFAULT_BOTTOM_LINE_HEIGHT = 2;
    /*分割线距离上下边缘的距离默认为8*/
    private final int DEFAULT_DIVIDER_MARGIN = 8;
    /*底部线条的颜色默认值*/
    private final int DEFAULT_BOTTOM_LINE_COLOR = 0xff000000;
    /*页面*/
    private ViewPager mViewPager;
    private MyListView mListView;
     private ArrayList<String> tabNameList=new ArrayList<>();
    /*页面切换监听事件*/
    private ViewPager.OnPageChangeListener mListener;
    /*页卡的颜色*/
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;
    /*分割线的颜色*/
    private int mDividerColor = DEFAULT_DIVIDER_COLOR;
    /*分割线距离上线边距的距离*/
    private int mDividerMargin = DEFAULT_DIVIDER_MARGIN;
    /*页卡画笔*/
    private Paint mIndicatorPaint;
    /*分割线画笔*/
    private Paint mDividerPaint;
    /*获取 tab 每个 item 的信息*/
                                                              //item
    /*当前选中的页面位置*/
    private int mSelectedPosition;
    /*页面的偏移量*/
    private float mSelectionOffset;
    /*底部线条的颜色*/
    private int mBottomLineColor = DEFAULT_BOTTOM_LINE_COLOR;
    /*底部线条的高度*/
    private int mBottomLineHeight = DEFAULT_BOTTOM_LINE_HEIGHT;
    /*滑动指示器的高度*/
    private int mIndicatorHeight = DEFAULT_INDICATOR_HEIGHT ;
    /*分割线的宽度*/
    private int mDividerWidth = DEFAULT_DIVIDER_WIDTH;
    /*底部线条的画笔*/
    private Paint mBottomPaint ;


     private SlidingTabClickListener  mSlidingTabClickListener;


    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*获取TypedArray*/
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.SlidingTabLayout);
        /*获取自定义属性的个数*/
        int N = typedArray.getIndexCount();
        Log.v("zgy","=========getIndexCount========="+N) ;
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingTabLayout_indicatorColor:
                    /*获取页卡颜色值*/
                    mIndicatorColor = typedArray.getColor(attr, DEFAULT_INDICATOR_COLOR);
                    break;
                case R.styleable.SlidingTabLayout_dividerColor:
                    /*获取分割线颜色的值*/
                    mDividerColor = typedArray.getColor(attr, DEFAULT_DIVIDER_COLOR);
                    break;
                case R.styleable.SlidingTabLayout_bottomLineColor:
                    /*获取底部线条颜色的值*/
                    mBottomLineColor = typedArray.getColor(attr, DEFAULT_BOTTOM_LINE_COLOR);
                    break;
                case R.styleable.SlidingTabLayout_dividerMargin:
                    /*获取分割线的距离上线边距的距离*/
                    mDividerMargin = (int) typedArray.getDimension(attr, DEFAULT_DIVIDER_MARGIN * getResources().getDisplayMetrics().density);
                    Log.v("zgy","=========mDividerMargin========="+mDividerMargin) ;
                    break;
                case R.styleable.SlidingTabLayout_indicatorHeight:
                    /*获取页卡的高度*/
                    mIndicatorHeight = (int) typedArray.getDimension(attr, DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
                    break;
                case R.styleable.SlidingTabLayout_bottomLineHeight:
                    /*获取底部线条的高度*/
                    mBottomLineHeight = (int) typedArray.getDimension(attr, DEFAULT_BOTTOM_LINE_HEIGHT * getResources().getDisplayMetrics().density);
                    break;
                case R.styleable.SlidingTabLayout_dividerWidth:
                    /*获取分割线的宽度*/
                    mDividerWidth = (int) typedArray.getDimension(attr, DEFAULT_DIVIDER_WIDTH * getResources().getDisplayMetrics().density);
                    break;
            }
        }
        /*释放TypedArray*/
        typedArray.recycle();
        initView();
    }

    private void initView() {
        setWillNotDraw(false);

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(mDividerColor);
        mDividerPaint.setStrokeWidth(mDividerWidth);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setColor(mIndicatorColor);

        mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomPaint.setColor(mBottomLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getChildCount() == 0) {
            return;
        }
        final int height = getHeight();
        /*当前页面的View tab*/
        View selectView = getChildAt(mSelectedPosition);
        /*计算开始绘制的位置*/
        int left = selectView.getLeft();
        /*计算结束绘制的位置*/
        int right = selectView.getRight();
        if (mSelectionOffset > 0) {
            View nextView = getChildAt(mSelectedPosition + 1);
            /*如果有偏移量，重新计算开始绘制的位置*/
            left = (int) (mSelectionOffset * nextView.getLeft() + (1.0f - mSelectionOffset) * left);
            /*如果有偏移量，重新计算结束绘制的位置*/
            right = (int) (mSelectionOffset * nextView.getRight() + (1.0f - mSelectionOffset) * right);
        }
        /*绘制滑动的页卡*/
        canvas.drawRect(left, height - mIndicatorHeight, right, height, mIndicatorPaint);
        canvas.drawRect(0,height - mBottomLineHeight,getWidth(),height,mBottomPaint);
        int bili=2;
        for (int i = 0; i < getChildCount() - 0; i++) {//改了建议。divider
            View child = getChildAt(i);
            canvas.drawLine(child.getRight(), mDividerMargin*bili,
                    child.getRight(), height - mDividerMargin*bili,
                    mDividerPaint);
        }
    }



    /**
     * 设置viewPager，初始化SlidingTab，
     * 在这个方法中为SlidingLayout设置
     * 内容，
     *
     *
     */
    public void setDataChange(ArrayList<String> tabNameList, Activity interfaceActivity) {
        this.mSlidingTabClickListener= (SlidingTabClickListener) interfaceActivity;
        /*先移除所以已经填充的内容*/
        removeAllViews();
        this.tabNameList=tabNameList;
        populateTabLayout();
    }


    /**
     * 填充layout，设置其内容
     */
    private void populateTabLayout() {

        final OnClickListener tabOnClickListener = new TabOnClickListener();

        for (int i = 0; i < tabNameList.size(); i++) {
            TextView textView = createDefaultTabView(getContext());
            textView.setOnClickListener(tabOnClickListener);
            textView.setText(tabNameList.get(i));
            addView(textView);
        }
    }
    private class TabOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < getChildCount(); i++) {
                if (v == getChildAt(i)) {
                  mSlidingTabClickListener.giveYouTheTabName(tabNameList.get(i),i);

                    return;
                }
            }
        }
    }

    /**
     * 创建默认的TabItem
     *
     * @param context
     * @return
     */
    private TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        textView.setLayoutParams(layoutParams);
        int padding = (int) (DEFAULT_TEXT_PADDING * getResources().getDisplayMetrics().density);
        //   textView.setPadding(padding, padding, padding, padding);
        textView.setPadding(10,10,10,5);
        textView.setLines(1);


        textView.setAllCaps(true);
        return textView;
    }





    /**
     * 回调获取 item name 的接口
     */
    public interface SlidingTabClickListener {
        /**
         * 获取 tab name
         *

         * @return
         */
        void giveYouTheTabName(String tabname,int nameIndex);
    }

}
