package com.example.biguncler.wp_launcher.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.text.TextUtils;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.activity.BaseActivity;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.BitmapUtil;
import com.example.biguncler.wp_launcher.util.WallpaperUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Biguncler on 07/03/2017.
 */

public class MyApplication extends Application {
    private BroadcastReceiver receiver, wallpaperReceiver;
    public static String ACTION_EMULATE_WALLPAPER_CHANGED="action_emulate_wallpaper_changed";
    public static boolean  isLightTheme;
    public static List<AppMode> apps;
    public static Map<String,String> appMap;
    public static int metroColor;


    @Override
    public void onCreate() {
        super.onCreate();
        isLightTheme=WallpaperUtil.isWallpaperHightLight(this);
        AppManager appManager = new AppManager();
        apps = appManager.getInstalledApp(this);
        setMap();
        registerReceiver();

        String color=SharedPreferenceDB.get(this,SharedPreferenceDB.METRO_COLOR);
        if(TextUtils.isEmpty(color)){
            SharedPreferenceDB.save(this,SharedPreferenceDB.METRO_COLOR,String.valueOf(getResources().getColor(R.color.metro_color_1)));
            metroColor=getResources().getColor(R.color.metro_color_1);
        }else{
            metroColor=Integer.valueOf(color);
        }
    }


    /**
     * 注册广播
     */
    private void registerReceiver() {
        wallpaperReceiver = new MyApplication.WallpaperReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Intent.ACTION_WALLPAPER_CHANGED);
        intentFilter2.addAction(ACTION_EMULATE_WALLPAPER_CHANGED);
        registerReceiver(wallpaperReceiver, intentFilter2);

        receiver = new MyApplication.MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver, intentFilter);


    }


    /**
     * 取消广播注册
     */
    private void unregisterReceiver() {
        unregisterReceiver(receiver);
        unregisterReceiver(wallpaperReceiver);
    }

    /**
     * app安装和卸载广播接收器
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
                AppManager appManager = new AppManager();
                apps = appManager.getInstalledApp(MyApplication.this);
                setMap();
                sendBroadcast(BaseActivity.ACTION_APP_INSTALLED);
            } else if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
                AppManager appManager = new AppManager();
                apps = appManager.getInstalledApp(MyApplication.this);
                setMap();
                sendBroadcast(BaseActivity.ACTION_APP_UNINSTALLED);
            }
        }
    }

    /**
     * 壁纸更换广播
     */
    public class WallpaperReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_WALLPAPER_CHANGED)) {
                isLightTheme=WallpaperUtil.isWallpaperHightLight(MyApplication.this);
                sendBroadcast(BaseActivity.ACTION_WALLPEPER_UPDATED);
            }else if(action.equals(ACTION_EMULATE_WALLPAPER_CHANGED)){

            }
        }
    }

    /**
     * 发送广播
     *
     * @param action
     */
    private void sendBroadcast(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void setMap(){
        appMap=new HashMap<>();
        for(AppMode appMode:apps){
            appMap.put(appMode.getAppName().toUpperCase(),appMode.getPackageName());
        }
    }

}
