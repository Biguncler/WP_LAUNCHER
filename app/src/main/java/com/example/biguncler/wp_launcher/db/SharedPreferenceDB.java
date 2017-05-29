package com.example.biguncler.wp_launcher.db;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Biguncler on 12/11/16.
 */

public class SharedPreferenceDB {

    public static final String METRO_COLOR="METRO_COLOR";






    public static void save(Context context, String key,String data){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,data);
        editor.commit();
    }


    public static  String get(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences("Session",Context.MODE_PRIVATE);
        return sp.getString(key, "");

    }
}
