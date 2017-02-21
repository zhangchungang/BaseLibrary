package com.dome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.library.activity.AppTitleBarActivity;

public class MainActivity extends AppTitleBarActivity {

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
    }
}
