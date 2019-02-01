package com.example.biguncler.wp_launcher.db;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Biguncler on 12/11/16.
 */

public class SharedPreferenceDB {

    public static final String METRO_COLOR="METRO_COLOR";
    public static final String SWITCH_LOCK="switch_lock";
    public static final String SWITCH_HOME="switch_home";







    public static void saveString(Context context, String key, String data){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,data);
        editor.commit();
    }


    public static  String getString(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void saveBoolean(Context context, String key, boolean data){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,data);
        editor.commit();
    }

    public static  boolean getBoolean(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
