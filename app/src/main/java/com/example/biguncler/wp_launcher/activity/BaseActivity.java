package com.example.biguncler.wp_launcher.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.util.StatusBarUtil;

/**
 * Created by Biguncler on 07/03/2017.
 */

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDecorView();
        StatusBarUtil.setStatusTextColor(this, MyApplication.isLightTheme);
    }



    private void setDecorView() {
        // 让布局全屏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }





}
