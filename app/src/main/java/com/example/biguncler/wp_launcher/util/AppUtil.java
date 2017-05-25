package com.example.biguncler.wp_launcher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.Collections;
import java.util.List;


/**
 * Created by Biguncler on 11/29/16.
 */

public class AppUtil {



    /**
     * 获取安装在手机中具有launcher的app
     * @return
     */
    public static List<ResolveInfo> getEnableLauncherApp(Context context){
        PackageManager pManager = context.getPackageManager();
        Intent mainIntent = new Intent();
        mainIntent.setAction(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(mainIntent, 0);
        // 调用系统排序 ， 根据name排序
        Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pManager));
        return resolveInfos;
    }

    /**
     * 启动app
     * @param context
     * @param packageName
     * @return
     */
    public static boolean luanchApp(Context context,String packageName){
        try{
            Intent intent = new Intent();
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除app
     * @param cotext
     * @param pakeageName
     * @return
     */
    public static boolean uninstallApp(Context cotext,String pakeageName){
        try{
            Uri packageURI = Uri.parse("package:"+pakeageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            cotext.startActivity(uninstallIntent);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
