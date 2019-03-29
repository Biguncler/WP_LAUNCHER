package com.example.biguncler.wp_launcher.biz;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;


import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.CharUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biguncler on 12/2/16.
 */

public class AppManager {
    /**
     * 根据app的名称获取相应的app
     *
     * @param apps
     * @param appName
     * @return
     */
    public List<AppMode> getInstalledAppByName(List<AppMode> apps, String appName) {
        List<AppMode> list = new ArrayList<>();
        if (TextUtils.isEmpty(appName)) return list;
        if (appName.equals(".")) return apps;
        for (AppMode appMode : apps) {
            if (appMode.getAppName().toUpperCase().contains(appName.toUpperCase())) {
                list.add(appMode);
            }
        }
        return list;
    }

    /**
     * 获取安装在手机中具有launcher的app
     *
     * @return
     */
    public List<AppMode> getInstalledApp(Context context) {
        List<AppMode> apps = new ArrayList<AppMode>();
        List<ResolveInfo> resolveInfos = AppUtil.getEnableLauncherApp(context);
        for (ResolveInfo resolveInfo : resolveInfos) {
            // 设置应用程序的包名
            String pakageName = resolveInfo.activityInfo.packageName;
            // 设置应用程序名字
            String appName="";
            String simpleAppName="";
            String rawAppName = resolveInfo.activityInfo.loadLabel(context.getPackageManager()).toString();
            if (CharUtil.isChinese(rawAppName)) {
                simpleAppName = CharUtil.getPinYinHeadChar(rawAppName).toUpperCase();
            }
            if(!TextUtils.isEmpty(simpleAppName)){
                appName=simpleAppName+MyApplication.SPLIT_STRING+rawAppName;
            }else{
                appName=rawAppName;
            }
            if (appName.contains(" ")) {
                appName = appName.replace(" ", "_");
            }
            appName=appName.toUpperCase();
            // 设置图片
            Drawable icon =icon = resolveInfo.activityInfo.loadIcon(context.getPackageManager());

            AppMode appMode = new AppMode(pakageName, appName, icon);
            apps.add(appMode);

            String str = "public static final String APP_PACKAGE_" + appName.toUpperCase() + "=\"" + pakageName + "\";";
            Log.i("weijunshu", "$=" + str);
        }
        return apps;
    }


    public List<AppMode> getHomeApps(Context context){
        List<AppMode> list = new ArrayList<>();
        AppMode calendar=new AppMode(MyApplication.appMap.get("calendar".toUpperCase()),"calendar", context.getDrawable(R.drawable.icon_white_weather));
        AppMode weather=new AppMode(MyApplication.appMap.get("weather".toUpperCase()),"weather", context.getDrawable(R.drawable.icon_white_weather));
        AppMode phone=new AppMode(MyApplication.appMap.get("phone".toUpperCase()),"phone", context.getDrawable(R.drawable.icon_white_phone));
        AppMode people=new AppMode(MyApplication.appMap.get("phone".toUpperCase()),"people", context.getDrawable(R.drawable.icon_white_contact));
        String messagePn= MyApplication.appMap.get("messages".toUpperCase());
        if(TextUtils.isEmpty(messagePn)){
            messagePn = MyApplication.appMap.get("messaging".toUpperCase());
        }
        AppMode message=new AppMode(messagePn,"messages", context.getDrawable(R.drawable.icon_white_message));
        AppMode clock=new AppMode(MyApplication.appMap.get("clock".toUpperCase()),"alarm", context.getDrawable(R.drawable.icon_white_clock));
        AppMode chrome=new AppMode(MyApplication.appMap.get("chrome".toUpperCase()),"chrome", context.getDrawable(R.drawable.icon_white_chrome));
        AppMode fileManager=new AppMode(MyApplication.appMap.get("file_manager".toUpperCase()),"files", context.getDrawable(R.drawable.icon_white_file_explore));
        AppMode camera=new AppMode(MyApplication.appMap.get("camera".toUpperCase()),"camera", context.getDrawable(R.drawable.icon_white_camera));
        AppMode gallery=new AppMode(MyApplication.appMap.get("gallery".toUpperCase()),"gallery", context.getDrawable(R.drawable.icon_white_gallery));
        AppMode map=new AppMode(MyApplication.appMap.get("amap".toUpperCase()),"map", context.getDrawable(R.drawable.icon_white_map));
        AppMode music=new AppMode(MyApplication.appMap.get("kgyl".toUpperCase()),"music", context.getDrawable(R.drawable.icon_white_music));
        AppMode store=new AppMode(MyApplication.appMap.get("wdj".toUpperCase()),"store", context.getDrawable(R.drawable.icon_white_store));
        AppMode setting=new AppMode(MyApplication.appMap.get("settings".toUpperCase()),"settings", context.getDrawable(R.drawable.icon_white_setting));
        AppMode qq=new AppMode(MyApplication.appMap.get("qq".toUpperCase()),"qq", context.getDrawable(R.drawable.icon_white_qq));
        AppMode wechat=new AppMode(MyApplication.appMap.get("wechat".toUpperCase()),"wechat", context.getDrawable(R.drawable.icon_white_wechat));

        list.add(calendar);
        list.add(weather);
        list.add(phone);
        list.add(people);
        list.add(message);
        list.add(clock);
        list.add(chrome);
        list.add(fileManager);
        list.add(camera);
        list.add(gallery);
        list.add(map);
        list.add(music);
        list.add(store);
        list.add(setting);
        list.add(qq);
        list.add(wechat);



return list;

    }


    public List<AppMode> getTabApps(Context context) {
        /*AppMode phone = null;
        AppMode message = null;
        AppMode cortana = null;
        AppMode browser = null;
        AppMode chrome = null;
        AppMode camera = null;
        AppMode camera_x = null;

        List<AppMode> list = new ArrayList<>();
        for (AppMode appMode : MyApplication.apps) {
            if (Constant_android.APP_PACKAGE_PHONE.equals(appMode.getPackageName())) {
                phone = appMode;
            }
            if (Constant_android.APP_PACKAGE_MESSAGING.equals(appMode.getPackageName())) {
                message = appMode;
            }
            if (Constant_android.APP_PACKAGE_CAMERA.equals(appMode.getPackageName())) {
                camera = appMode;
            }
            if (Constant_android.APP_PACKAGE_BROWSER.equals(appMode.getPackageName())) {
                browser = appMode;
            }
            if (Constant_my.APP_PACKAGE_CHROME.equals(appMode.getPackageName())) {
                chrome = appMode;
            }
            if (Constant_my.APP_PACKAGE_CORTANA.equals(appMode.getPackageName())) {
                cortana = appMode;
            }
            if ((appMode.getAppName().toUpperCase().equals("XJ") || appMode.getAppName().toUpperCase().equals("CAMERA")) && appMode.getPackageName().toUpperCase().contains("CAMERA")) {
                camera_x = appMode;
            }
        }


        if(phone!=null){
            list.add(phone);
        }
        if(message!=null){
            list.add(message);
        }
        if(cortana!=null){
            list.add(cortana);
        }
        if(camera!=null){
            list.add(camera);
        }else{
            if(camera_x!=null){
                list.add(camera_x);
            }
        }

        if(chrome!=null){
            list.add(chrome);
        }else{
            if(browser!=null){
                list.add(browser);
            }
        }*/
        return null;
    }

}
