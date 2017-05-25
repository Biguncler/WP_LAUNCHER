package com.example.biguncler.wp_launcher.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.MyViewPagerAdapter;
import com.example.biguncler.wp_launcher.fragment.FragmentApps;
import com.example.biguncler.wp_launcher.fragment.FragmentHome;
import com.example.biguncler.wp_launcher.fragment.FragmentSearchApp;
import com.example.biguncler.wp_launcher.util.WallpaperUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private LinearLayout layoutParent;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }




    private void initView() {
        getWindow().getDecorView().setBackground(new BitmapDrawable(WallpaperUtil.getWallpaper(this)));

        layoutParent= (LinearLayout) findViewById(R.id.activity_main);

        fragmentList=new ArrayList<>();
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentApps() );

        viewPager= (ViewPager) findViewById(R.id.view_viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList));
    }


}
