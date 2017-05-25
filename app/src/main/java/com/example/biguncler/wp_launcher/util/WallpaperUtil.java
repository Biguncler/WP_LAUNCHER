package com.example.biguncler.wp_launcher.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import java.io.IOException;

/**
 * Created by Biguncler on 12/5/16.
 */

public class WallpaperUtil {




    public static boolean isWallpaperHightLight(Context context){
        int wallpapebright= BitmapUtil.getBitmapBrightness(getWallpaper(context));
        return wallpapebright>160?true:false;
    }

    public static Bitmap getWallpaper(Context context){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        // 获取当前壁纸
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        // 将Drawable,转成Bitmap
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        return bm;
    }

    public static void setWallpaper(Context context,Bitmap bitmap){
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
