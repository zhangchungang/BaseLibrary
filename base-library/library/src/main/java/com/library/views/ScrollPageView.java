package com.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.library.R;
import com.library.helper.DrawableHelper;
import com.library.utils.Utils;

/**
 * 滚动页控件
 * Created by Administrator on 2017/2/21.
 */
public class ScrollPageView extends RelativeLayout {

    private LinearLayout indicator;
    // 底部小点的图片
    private ImageView[] points;

    private ViewPager vp;

    private ViewPagerAdapter vpAdapter;
    //指针颜色
    private int indicatorColor;
    //指针大小
    private int indicatorSize;
    //指针间距
    private int indicatorSpace;
    public ScrollPageView(Context context) {
        this(context,null);
    }

    public ScrollPageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.scrollPageAttr);
    }

    public ScrollPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        indicatorSize = Utils.Int2dp(getContext(),20);
        indicatorSpace = Utils.Int2dp(getContext(),5);
        indicatorColor = Color.rgb(0,0,0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollPageView, defStyleAttr, R.style.defaultScrollPageView);
        indicatorColor = a.getColor(R.styleable.ScrollPageView_indicatorColor,indicatorColor);
        indicatorSize = a.getDimensionPixelSize(R.styleable.ScrollPageView_indicatorSize,indicatorSize);
        indicatorSpace = a.getDimensionPixelOffset(R.styleable.ScrollPageView_indicatorSpace,indicatorSpace);
        a.recycle();
        if(!this.isInEditMode()) {
            init();
        }
    }

    private void init() {
        vp = new ViewPager(getContext());
        vp.setId(R.id.pager_viewpager);
        this.addView(vp,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        indicator = new LinearLayout(getContext());
        indicator.setOrientation(LinearLayout.HORIZONTAL);
        indicator.setId(R.id.pager_indicator);
        RelativeLayout.LayoutParams indicator_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        indicator_lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        indicator_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicator_lp.setMargins(0, 0, 0, Utils.Int2dp(getContext(), 20));
        this.addView(indicator, indicator_lp);
    }

    public void setViews(View...v){
        if(v != null){
            vp.setAdapter(vpAdapter = new ViewPagerAdapter(v));
            vp.addOnPageChangeListener(pageChangeListener);
            points = new ImageView[vpAdapter.getCount()];
            for(int i = 0;i < vpAdapter.getCount();i++){
                ImageView iv = new ImageView(getContext());
                iv.setPadding(indicatorSpace,0,indicatorSpace,0);
                iv.setLayoutParams(new ViewGroup.LayoutParams(indicatorSize, indicatorSize));
                points[i] = iv;
                if (i == 0) {
                    //默认选中第一张图片
                    points[i].setImageDrawable(getSelectedDrawable());
                } else {
                    points[i].setImageDrawable(getDefaultDrawable());
                }

                indicator.addView(points[i]);
            }
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        //当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < vpAdapter.getCount(); i++) {
                if(i == position){
                    points[i].setImageDrawable(getSelectedDrawable());
                }else {
                    points[i].setImageDrawable(getDefaultDrawable());
                }
            }
        }

        //当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter {
        View[] v;
        public ViewPagerAdapter(View ...v){
            this.v = v;
        }
        @Override
        public int getCount() {
            return v.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = v[position];
            ((ViewPager) container).addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(v[position]);
        }
    }
    private Drawable getDefaultDrawable(){

        Drawable d = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .setSize(indicatorSize,indicatorSize)
                .setStroke(1,indicatorColor)
                .create();
        return d;
    }
    private Drawable getSelectedDrawable(){
        Drawable d = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .setSize(indicatorSize,indicatorSize)
                .setSolidColor(indicatorColor)
                .create();
        return d;
    }
}