package com.library.views.page.indicator;

import android.support.v4.view.ViewPager;

/**
 * 页指示器
 * Created by Administrator on 2017/2/22.
 */

public interface PageIndicator extends ViewPager.OnPageChangeListener {
    /**
     * 设置ViewPager
     * @param view
     */
    void setViewPager(ViewPager view);

    /**
     * 设置ViewPager
     * @param view
     * @param initialPosition 最初位置
     */
    void setViewPager(ViewPager view, int initialPosition);

    /**
     * 设置当前项
     * @param item
     */
    void setCurrentItem(int item);

    /**
     * 设置页更改侦听器。
     * @param listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * 通知数据集更改
     */
    void notifyDataSetChanged();
}
