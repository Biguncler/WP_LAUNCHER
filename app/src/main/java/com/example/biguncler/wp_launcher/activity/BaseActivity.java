package com.example.biguncler.wp_launcher.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.fragment.BaseFragment;
import com.example.biguncler.wp_launcher.util.StatusBarUtil;
import com.example.floatball.Constant;
import com.example.libtheme.ThemeHelper;

import java.util.List;

/**
 * Created by Biguncler on 07/03/2017.
 */

public class BaseActivity extends FragmentActivity {
    public static final String ACTION_APP_INSTALLED="com.biguncler.wp_launcher.app_installed";
    public static final String ACTION_APP_UNINSTALLED="com.biguncler.wp_launcher.app_uninstalled";
    public static final String ACTION_WALLPEPER_UPDATED="com.biguncler.wp_launcher.wallpaper_updated";
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static final String ACTION_METRO_COLOR_CHANGED = "com.biguncler.wp_launcher.action.METRO_COLOR_CHANGED";
    public static final String ACTION_UPDATE_TILE_TRANSPARENCY="action_update_tile_transparency";
    private BroadcastReceiver receiver;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDecorView();
        ThemeHelper.setTheme(this);
        registerReceiver();

    }



    private void setDecorView() {
        // 让布局全屏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    protected  void onWallpaperChanged(Intent intent){
        //recreate();   recreate()在小米9系统上，recreate后，测量控件高度为0，其他手机是ok的，暂时以如下方法recreate
        finish();
        startActivity(new Intent(this,this.getClass()));
    }

    protected void onAppInstalled(Intent intent){

    }

    protected void onAppUninstalled(Intent intent){

    }

    protected void onMetroColorChanged(Intent intent){

    }

    protected void onPressHome(Intent intent){

    }
    protected void onLongPressHome(Intent intent){

    }

    protected void onBatteryChanged(Intent intent){

    }

    protected void onScreenOff(Intent intent){

    }

    protected void onDateChange(Intent intent){

    }

    protected void onTimeTick(Intent intent){

    }

    protected void onFloatGestureRight(Intent intent){

    }

    protected void onFloatGestureLeft(Intent intent){

    }
    protected void onFloatGestureUp(Intent intent){

    }
    protected void onFloatGestureDown(Intent intent){

    }

    protected void onUnlock(Intent intent){

    }

    protected void onTileTransparency(Intent intent){

    }




    /**
     *注册广播
     */
    private void registerReceiver() {
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_APP_INSTALLED);
        intentFilter.addAction(ACTION_APP_UNINSTALLED);
        intentFilter.addAction(ACTION_WALLPEPER_UPDATED);
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(ACTION_METRO_COLOR_CHANGED);
        intentFilter.addAction(Constant.ACTION_GESTURE_FLOAT_RIGHT);
        intentFilter.addAction(Constant.ACTION_GESTURE_FLOAT_LEFT);
        intentFilter.addAction(Constant.ACTION_GESTURE_FLOAT_UP);
        intentFilter.addAction(Constant.ACTION_GESTURE_FLOAT_DOWN);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
      //  intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
       // intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
       // intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        //intentFilter.addAction(Intent.ACTION_TIME_TICK);
       // intentFilter.addAction(ACTION_ADD_SHORTCUT);
        intentFilter.addAction(ACTION_UPDATE_TILE_TRANSPARENCY);
        registerReceiver(receiver, intentFilter);


    }


    /**
     * 取消广播注册
     */
    private void unregisterReceiver() {
        unregisterReceiver(receiver);
    }

    /**
     * app安装和卸载,壁纸更换广播接收器
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ACTION_APP_INSTALLED)){
                onAppInstalled(intent);
            }else if(action.equals(ACTION_APP_UNINSTALLED)){
               onAppUninstalled(intent);
            }else if(action.equals(ACTION_WALLPEPER_UPDATED)){
                onWallpaperChanged(intent);
            }else if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){

                String SYSTEM_REASON = "reason";
                String SYSTEM_HOME_KEY = "homekey";
                String SYSTEM_HOME_KEY_LONG = "recentapps";

                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键
                    onPressHome(intent);
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    //表示长按home键
                    onLongPressHome(intent);
                }
            }else if(action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                onBatteryChanged(intent);
            }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
                onScreenOff(intent);
            }else if(action.equals(Intent.ACTION_DATE_CHANGED)){
                onDateChange(intent);
            }else if(action.equals(Intent.ACTION_TIME_TICK)){
                onTimeTick(intent);
            }else if(action.equals(ACTION_ADD_SHORTCUT)){
                /*Toast.makeText(BaseActivity.this,"aaaa",Toast.LENGTH_SHORT).show();
                Intent shortcut_intent = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);

                ComponentName componentName=shortcut_intent.getComponent();
                String a=componentName.getClassName();
                String b=componentName.getPackageName();


                // startActivity(new Intent().setComponent(new ComponentName(b,a)));
                startActivity(shortcut_intent);*/
            }else if(action.equals(ACTION_METRO_COLOR_CHANGED)){
                onMetroColorChanged(intent);
            }else if(action.equals(Constant.ACTION_GESTURE_FLOAT_DOWN)){
                onFloatGestureDown(intent);
            }else if(action.equals(Constant.ACTION_GESTURE_FLOAT_UP)){
                onFloatGestureUp(intent);
            }else if(action.equals(Constant.ACTION_GESTURE_FLOAT_LEFT)){
                onFloatGestureLeft(intent);
            }else if(action.equals(Constant.ACTION_GESTURE_FLOAT_RIGHT)){
                onFloatGestureRight(intent);
            }else if(action.equals(Intent.ACTION_USER_PRESENT)){
                onUnlock(intent);
            }else if(action.equals(ACTION_UPDATE_TILE_TRANSPARENCY)){
                onTileTransparency(intent);
            }
        }
    }

}
