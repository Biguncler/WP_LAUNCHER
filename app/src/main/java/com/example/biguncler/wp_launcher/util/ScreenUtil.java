package com.example.biguncler.wp_launcher.util;

import android.content.Context;

/**
 * Created by Biguncler on 23/12/2016.
 */

public class ScreenUtil {
    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public  static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public  static int  getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }



}
