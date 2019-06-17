package com.example.biguncler.wp_launcher.activity;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.MyViewPagerAdapter;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.VoiceTextManager;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.fragment.BaseFragment;
import com.example.biguncler.wp_launcher.fragment.FragmentApps;
import com.example.biguncler.wp_launcher.fragment.FragmentHome;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.LockScreenUtil;
import com.example.biguncler.wp_launcher.util.PlayMusicUtils;
import com.example.floatball.Constant;
import com.example.libutil.BitmapUtil;
import com.example.libutil.WallpaperUtil;
import com.example.biguncler.wp_launcher.view.ScreenStateLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ScreenStateLayout.OnScreenStateChangedListener {
    public static final String ACTION_hOME_SWITCH = "com.android.launcher.action.HOME_SWITCH";
    private ScreenStateLayout layoutParent;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private MyViewPagerAdapter adapter;
    private ImageView iv;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 设置背景透明，显示桌面壁纸
        if (forceOpaqueBackground(this)) {
            updateWallpaperVisibility(false);
        } else {
            updateWallpaperVisibility(true);
        }
    }


    private void initView() {

        layoutParent = (ScreenStateLayout) findViewById(R.id.activity_main);

        fragmentList = new ArrayList<>();
        if (SharedPreferenceDB.getBoolean(this, SharedPreferenceDB.SWITCH_HOME)) {
            fragmentList.add(new FragmentHome());
        }
        fragmentList.add(new FragmentApps());
        iv = (ImageView) findViewById(R.id.iv_bg);
        viewPager = (ViewPager) findViewById(R.id.view_viewpager);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);

        layoutParent.setOnScreenStateChangedListener(this);
        if (adapter.getCount() > 1) {
            iv.setBackground(new BitmapDrawable(WallpaperUtil.getBlureWallpaper(this)));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                private float alpha = viewPager.getAlpha();

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (positionOffset == 0) return;
                    if (positionOffset > 0.99) {
                        positionOffset = 1;
                    }
                    float ap = 0;
                    if (alpha == 1) {
                        ap = Math.abs(alpha - positionOffset);
                    } else {
                        ap = Math.abs(1 - positionOffset);
                    }
                    iv.setAlpha(ap);

                }

                @Override
                public void onPageSelected(int position) {
                    MainActivity.this.onPageSelected(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 0) {
                        alpha = iv.getAlpha();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onBackPressed() {
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int currentItem = viewPager.getCurrentItem();
        int items = adapter.getCount();
        int item = currentItem - 1;
        if (item < 0) {
            item = items - 1;
        }
        viewPager.setCurrentItem(item);
    }

    @Override
    protected void onWallpaperChanged(Intent intent) {
        super.onWallpaperChanged(intent);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onWallpaperChanged(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onAppUninstalled(Intent intent) {
        super.onAppUninstalled(intent);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onAppUninstalled(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAppInstalled(Intent intent) {
        super.onAppInstalled(intent);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onAppInstalled(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        MyApplication.metroColor = Integer.valueOf(SharedPreferenceDB.getString(this, SharedPreferenceDB.METRO_COLOR));
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onMetroColorChanged(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onTileTransparency(Intent intent) {
        super.onTileTransparency(intent);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onTileTransparency(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPageSelected(int position) {
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                ((BaseFragment) fragment).onPageSelected(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFloatGestureDown(Intent intent) {
        super.onFloatGestureDown(intent);
        AppUtil.luanchApp(this, MyApplication.appMap.get(new VoiceTextManager(this).transfer("小爱同学")));
    }

    @Override
    protected void onFloatGestureUp(Intent intent) {
        super.onFloatGestureUp(intent);
        LockScreenUtil.lock(this);
    }

    @Override
    protected void onFloatGestureLeft(Intent intent) {
        super.onFloatGestureLeft(intent);
    }

    @Override
    public void onScreenTurnOn() {

    }

    @Override
    public void onScreenTurnOff() {
        if (SharedPreferenceDB.getBoolean(this, SharedPreferenceDB.SWITCH_LOCK)) {
            PlayMusicUtils.playSound(this, R.raw.lock);
        }
    }

    @Override
    protected void onUnlock(Intent intent) {
        super.onUnlock(intent);
        if (SharedPreferenceDB.getBoolean(this, SharedPreferenceDB.SWITCH_LOCK)) {
            PlayMusicUtils.playSound(this, R.raw.unlock);
        }
    }

    private void updateWallpaperVisibility(boolean visible) {
        int wpflags = visible ? WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER : 0;
        int curflags = getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
        if (wpflags != curflags) {
            getWindow().setFlags(wpflags, WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        }
    }


    public static boolean forceOpaqueBackground(Context context) {
        return WallpaperManager.getInstance(context).getWallpaperInfo() != null;

    }


    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_hOME_SWITCH.equals(action)) {
                    finish();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_hOME_SWITCH);
        registerReceiver(receiver, intentFilter);

    }
    private void unregisterReceiver() {
        unregisterReceiver(receiver);
    }
}
