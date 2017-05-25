package com.example.biguncler.wp_launcher.biz;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;


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
            String appName = resolveInfo.activityInfo.loadLabel(context.getPackageManager()).toString().toUpperCase();
            if (CharUtil.isChinese(appName)) {
                appName = CharUtil.getPinYinHeadChar(appName).toUpperCase();
            }
            if (appName.contains(" ")) {
                appName = appName.replace(" ", "_");
            }
            // 设置图片
            Drawable icon =icon = resolveInfo.activityInfo.loadIcon(context.getPackageManager());

            AppMode appMode = new AppMode(pakageName, appName, icon);
            apps.add(appMode);

            String str = "public static final String APP_PACKAGE_" + appName.toUpperCase() + "=\"" + pakageName + "\";";
            Log.i("weijunshu", "$=" + str);
        }
        return apps;
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
