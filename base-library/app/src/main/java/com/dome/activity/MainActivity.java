package com.dome.activity;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.activity.AppTitleBarActivity;
import com.library.views.page.indicator.DotIndicator;

import butterknife.Bind;

public class MainActivity extends AppTitleBarActivity {

    @Bind(R.id.main_indicator)
    DotIndicator indicator;
    @Bind(R.id.main_viewpager)
    ViewPager pager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        TextView tv01 = new TextView(this);
        tv01.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv01.setGravity(Gravity.CENTER);
        tv01.setText("01");
        TextView tv02 = new TextView(this);
        tv02.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv02.setGravity(Gravity.CENTER);
        tv02.setText("02");
        TextView tv03 = new TextView(this);
        tv03.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv03.setGravity(Gravity.CENTER);
        tv03.setText("03");
        final View[] v = {tv01,tv02,tv03};
        pager.setAdapter(new PagerAdapter() {
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
        });
        indicator.setViewPager(pager);
    }
}
