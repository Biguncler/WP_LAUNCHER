package com.example.libutil;

import android.graphics.Color;

/**
 * Created by Biguncler on 2/2/2019.
 */

public class ColorUtil {


    public static int setColorAlpha(int color ,int alpha){
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return Color.argb(alpha,red,green,blue);
    }
}
