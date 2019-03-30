package com.example.biguncler.wp_launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.biguncler.wp_launcher.R;


/**
 * Created by Biguncler on 2/1/2019.
 */

public class MetroColorActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_color);
    }

    @Override
    protected void onPressHome(Intent intent) {
        super.onPressHome(intent);
        finish();
    }
}
