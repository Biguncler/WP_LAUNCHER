package com.example.libtheme;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.example.libutil.WallpaperUtil;

/**
 * Created by Biguncler on 1/27/2019.
 */

public class ThemeHelper {

    public static  boolean isLightTheme(Context context){
        return !WallpaperUtil.isWallpaperHightLight(context);
    }


    public static void  setTheme(Activity activity){
        if(isLightTheme(activity)){
            activity.setTheme(R.style.th_theme_light);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }else{
            activity.setTheme(R.style.th_theme_dark);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static int getTextColor(Context context){
        TypedArray a = context.obtainStyledAttributes(new int[]{R.attr.th_text_color});
        return a.getColor(0, Color.TRANSPARENT);
    }

    public static int getTintBgColor(Context context){
        TypedArray a = context.obtainStyledAttributes(new int[]{R.attr.th_tint_bg_color});
        return a.getColor(0, Color.TRANSPARENT);
    }

    public static int getTintIcColor(Context context){
        TypedArray a = context.obtainStyledAttributes(new int[]{R.attr.th_tint_ic_color});
        return a.getColor(0, Color.TRANSPARENT);
    }
}
