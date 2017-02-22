package com.dome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.activity.AppTitleBarActivity;
import com.library.views.ScrollPageView;

import org.w3c.dom.Text;

import butterknife.Bind;

public class MainActivity extends AppTitleBarActivity {

    @Bind(R.id.mScrollPageView)
    ScrollPageView mScrollPageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarContentView(R.layout.activity_main);
        getTitleBar().setTitle("标题");
        getTitleBar().setLeftLogo(R.drawable.btn_return_normal);
        getTitleBar().setLeftLogoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        View[] v = {tv01,tv02,tv03};
        mScrollPageView.setViews(v);
    }
}
