package com.library.views.page.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.library.R;
import com.library.helper.DrawableHelper;

/**
 * 圆点指示器
 * Created by Administrator on 2017/2/22.
 */
public class DotIndicator extends LinearLayout implements PageIndicator{

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    //当前页
    private int mCurrentPage;
    //圆点颜色
    private int mDotColor;
    //圆点之间的间距
    private int mDotSpace;
    //圆点的大小
    private int mDotSize;
    public DotIndicator(Context context) {
        this(context,null);
    }

    public DotIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dotIndicatorAttr);
    }

    public DotIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode())return;
        this.setOrientation(HORIZONTAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotIndicator, defStyleAttr, R.style.defaultDotIndicator);
        mDotColor = a.getColor(R.styleable.DotIndicator_dotColor,mDotColor);
        mDotSize = a.getDimensionPixelSize(R.styleable.DotIndicator_dotSize,mDotSize);
        mDotSpace = a.getDimensionPixelSize(R.styleable.DotIndicator_dotSpace,mDotSpace);
        a.recycle();

    }

    @Override
    public void setViewPager(ViewPager view) {
        setViewPager(view,0);
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.addOnPageChangeListener(this);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        notifyDataSetChanged();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        this.removeAllViews();
        int count = mViewPager.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setPadding(mDotSpace,0,mDotSpace,0);
            iv.setLayoutParams(new ViewGroup.LayoutParams(mDotSize, mDotSize));
            if (i == mCurrentPage) {
                //默认选中第一张图片
                iv.setImageDrawable(getSelectedDrawable());
            } else {
                iv.setImageDrawable(getDefaultDrawable());
            }
            this.addView(iv);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        notifyDataSetChanged();
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }
    private Drawable getDefaultDrawable(){

        Drawable d = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .setSize(mDotSize,mDotSize)
                .setStroke(2,mDotColor)
                .create();
        return d;
    }
    private Drawable getSelectedDrawable(){
        Drawable d = new DrawableHelper.RoundBackgroundBuilder(getContext())
                .setSize(mDotSize,mDotSize)
                .setSolidColor(mDotColor)
                .create();
        return d;
    }
}
