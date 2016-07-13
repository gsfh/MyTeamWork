package com.gsfh.myteamwork.vmovie.widget;

import android.content.Context;
 import android.graphics.Typeface;
 import android.os.Build;
 import android.support.v4.view.PagerAdapter;
 import android.support.v4.view.ViewPager;
 import android.util.AttributeSet;
 import android.util.TypedValue;
 import android.view.Gravity;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.widget.HorizontalScrollView;
 import android.widget.LinearLayout;
 import android.widget.TextView;
 /**
   * To be used with ViewPager to provide a tab indicator component which give
   * constant feedback as to the user's scroll progress.
   * <p>
   * To use the component, simply add it to your view hierarchy. Then in your
   * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
   * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout is
 24  * being used for.
 25  * <p>
 26  * The colors can be customized in two ways. The first and simplest is to
 27  * provide an array of colors via {@link #setSelectedIndicatorColors(int...)}
 28  * and {@link #setDividerColors(int...)}. The alternative is via the
 29  * {@link TabColorizer} interface which provides you complete control over which
 30  * color is used for any individual position.
 31  * <p>
 32  * The views used as tabs can be customized by calling
 33  * {@link #setCustomTabView(int, int)}, providing the layout ID of your custom
 34  * layout.
 35  */
/**
 * Created by admin on 2016/7/13.
 */
public class SlidingTabLayout extends HorizontalScrollView  {
    /**
     40      * Allows complete control over the colors drawn in the tab layout. Set with
     41      * {@link #setCustomTabColorizer(TabColorizer)}.
     42      */
         public interface TabColorizer {

                         /**
         46          * @return return the color of the indicator used when {@code position}
         47          *         is selected.
         48          */
                         int getIndicatorColor(int position);

                         /**
         52          * @return return the color of the divider drawn to the right of
         53          *         {@code position}.
         54          */
                         int getDividerColor(int position);

                     }

                 private static final int TITLE_OFFSET_DIPS = 24;
         private static final int TAB_VIEW_PADDING_DIPS = 16;
         private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

                 private int mTitleOffset;

                 private int mTabViewLayoutId;
         private int mTabViewTextViewId;

                 private ViewPager mViewPager;
         private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

                 private final SlidingTabStrip mTabStrip;

                 public SlidingTabLayout(Context context) {
                 this(context, null);
             }

                 public SlidingTabLayout(Context context, AttributeSet attrs) {
                 this(context, attrs, 0);
             }

                 public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
                 super(context, attrs, defStyle);

                 // Disable the Scroll Bar
                 setHorizontalScrollBarEnabled(false);
                 // Make sure that the Tab Strips fills this View
                 setFillViewport(true);

                 mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

                 mTabStrip = new SlidingTabStrip(context);
                 addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
             }

                 /**
     96      * Set the custom {@link TabColorizer} to be used.
     97      *
     98      * If you only require simple custmisation then you can use
     99      * {@link #setSelectedIndicatorColors(int...)} and
     100      * {@link #setDividerColors(int...)} to achieve similar effects.
     101      */
                 public void setCustomTabColorizer(TabColorizer tabColorizer) {
                 mTabStrip.setCustomTabColorizer(tabColorizer);
             }

                 /**
     107      * Sets the colors to be used for indicating the selected tab. These colors
     108      * are treated as a circular array. Providing one color will mean that all
     109      * tabs are indicated with the same color.
     110      */
                 public void setSelectedIndicatorColors(int... colors) {
                 mTabStrip.setSelectedIndicatorColors(colors);
             }

                 /**
     116      * Sets the colors to be used for tab dividers. These colors are treated as
     117      * a circular array. Providing one color will mean that all tabs are
     118      * indicated with the same color.
     119      */
                 public void setDividerColors(int... colors) {
                 mTabStrip.setDividerColors(colors);
             }

                 /**
     125      * Set the {@link ViewPager.OnPageChangeListener}. When using
     126      * {@link SlidingTabLayout} you are required to set any
     127      * {@link ViewPager.OnPageChangeListener} through this method. This is so
     128      * that the layout can update it's scroll position correctly.
     129      *
     130      * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     131      */
                 public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
                 mViewPagerPageChangeListener = listener;
             }

                 /**
     137      * Set the custom layout to be inflated for the tab views.
     138      *
     139      * @param layoutResId
     140      *            Layout id to be inflated
     141      * @param textViewId
     142      *            id of the {@link TextView} in the inflated view
     143      */
                 public void setCustomTabView(int layoutResId, int textViewId) {
                 mTabViewLayoutId = layoutResId;
                 mTabViewTextViewId = textViewId;
             }

                 /**
     150      * Sets the associated view pager. Note that the assumption here is that the
     151      * pager content (number of tabs and tab titles) does not change after this
     152      * call has been made.
     153      */
                 public void setViewPager(ViewPager viewPager) {
                 mTabStrip.removeAllViews();

                 mViewPager = viewPager;
                 if (viewPager != null) {
                         viewPager.setOnPageChangeListener(new InternalViewPagerListener());
                         populateTabStrip();
                     }
             }
                 /**
     165      * Create a default view to be used for tabs. This is called if a custom tab
     166      * view is not set via {@link #setCustomTabView(int, int)}.
     167      */
                 protected TextView createDefaultTabView(Context context) {
                 TextView textView = new TextView(context);
                 textView.setGravity(Gravity.CENTER);
                 textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
                 textView.setTypeface(Typeface.DEFAULT_BOLD);

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                         // If we're running on Honeycomb or newer, then we can use the
                         // Theme's
                         // selectableItemBackground to ensure that the View has a pressed
                         // state
                         TypedValue outValue = new TypedValue();
                         getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                         textView.setBackgroundResource(outValue.resourceId);
                     }

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                         // If we're running on ICS or newer, enable all-caps to match the
                         // Action Bar tab style
                         textView.setAllCaps(true);
                     }

                 int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
                 textView.setPadding(padding, padding, padding, padding);

                 return textView;
             }

                 private void populateTabStrip() {
                 final PagerAdapter adapter = mViewPager.getAdapter();
                 final View.OnClickListener tabClickListener = new TabClickListener();

                 for (int i = 0; i < adapter.getCount(); i++) {
                         View tabView = null;
                         TextView tabTitleView = null;

                         if (mTabViewLayoutId != 0) {
                                 // If there is a custom tab view layout id set, try and inflate
                                 // it
                             tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip, false);
                            tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
                           }

                         if (tabView == null) {
                                 tabView = createDefaultTabView(getContext());
                             }

                         if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                                 tabTitleView = (TextView) tabView;
                             }

                         tabTitleView.setText(adapter.getPageTitle(i));
                         tabView.setOnClickListener(tabClickListener);
                         //将tab标题栏平均分配
                         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
                         tabView.setLayoutParams(layoutParams);

                         mTabStrip.addView(tabView);
                     }
             }

                 @Override
         protected void onAttachedToWindow() {
                 super.onAttachedToWindow();

                 if (mViewPager != null) {
                         scrollToTab(mViewPager.getCurrentItem(), 0);
                     }
             }

                 private void scrollToTab(int tabIndex, int positionOffset) {
                 final int tabStripChildCount = mTabStrip.getChildCount();
                 if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
                         return;
                     }

                 View selectedChild = mTabStrip.getChildAt(tabIndex);
                 if (selectedChild != null) {
                         int targetScrollX = selectedChild.getLeft() + positionOffset;

                         if (tabIndex > 0 || positionOffset > 0) {
                                 // If we're not at the first child and are mid-scroll, make sure
                                 // we obey the offset
                                 targetScrollX -= mTitleOffset;
                             }

                         scrollTo(targetScrollX, 0);
                     }
             }

                 private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
                 private int mScrollState;

                         @Override
                 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                         int tabStripChildCount = mTabStrip.getChildCount();
                         if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                                 return;
                             }

                         mTabStrip.onViewPagerPageChanged(position, positionOffset);

                         View selectedTitle = mTabStrip.getChildAt(position);
                         int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
                         scrollToTab(position, extraOffset);

                         if (mViewPagerPageChangeListener != null) {
                                 mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                             }
                     }

                         @Override
                 public void onPageScrollStateChanged(int state) {
                         mScrollState = state;

                         if (mViewPagerPageChangeListener != null) {
                                 mViewPagerPageChangeListener.onPageScrollStateChanged(state);
                             }
                     }

                         @Override
                 public void onPageSelected(int position) {
                         if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                                 mTabStrip.onViewPagerPageChanged(position, 0f);
                                 scrollToTab(position, 0);
                             }

                         if (mViewPagerPageChangeListener != null) {
                                 mViewPagerPageChangeListener.onPageSelected(position);
                             }
                     }
             }

                 private class TabClickListener implements View.OnClickListener {
                 @Override
                 public void onClick(View v) {
                         for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                                 if (v == mTabStrip.getChildAt(i)) {
                                         return;
                                     }
                             }
                     }
             }
     }

