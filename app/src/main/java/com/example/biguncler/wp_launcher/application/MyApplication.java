package com.example.biguncler.wp_launcher.application;

import android.app.Application;
import android.graphics.Color;

import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.BitmapUtil;
import com.example.biguncler.wp_launcher.util.WallpaperUtil;

import java.util.List;

/**
 * Created by Biguncler on 07/03/2017.
 */

public class MyApplication extends Application {
    public static boolean  isLightTheme;
    public static List<AppMode> apps;
    public static int metroColor;

    @Override
    public void onCreate() {
        super.onCreate();
        isLightTheme=WallpaperUtil.isWallpaperHightLight(this);

        AppManager appManager = new AppManager();
        apps = appManager.getInstalledApp(this);
        metroColor= Color.GREEN;

    }
}
