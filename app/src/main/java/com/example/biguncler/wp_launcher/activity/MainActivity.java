package com.example.biguncler.wp_launcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.MyViewPagerAdapter;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.fragment.BaseFragment;
import com.example.biguncler.wp_launcher.fragment.FragmentApps;
import com.example.biguncler.wp_launcher.fragment.FragmentHome;
import com.example.biguncler.wp_launcher.fragment.FragmentSearchApp;
import com.example.biguncler.wp_launcher.util.StatusBarUtil;
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
        //fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentApps() );

        viewPager= (ViewPager) findViewById(R.id.view_viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewPager. setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onBackPressed();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onWallpaperChanged(Intent intent) {
        super.onWallpaperChanged(intent);
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onWallpaperChanged(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //
        getWindow().getDecorView().setBackground(new BitmapDrawable(WallpaperUtil.getWallpaper(this)));

    }

    @Override
    protected void onAppUninstalled(Intent intent) {
        super.onAppUninstalled(intent);
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onAppUninstalled(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onAppInstalled(Intent intent) {
        super.onAppInstalled(intent);
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onAppInstalled(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        MyApplication.metroColor= Integer.valueOf(SharedPreferenceDB.get(this,SharedPreferenceDB.METRO_COLOR));
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onMetroColorChanged(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onPageSelected(int position){
        try{
            List<Fragment> fragments=getSupportFragmentManager().getFragments();
            for(Fragment fragment:fragments){
                ((BaseFragment)fragment).onPageSelected(position);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
