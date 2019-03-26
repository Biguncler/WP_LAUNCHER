package com.example.biguncler.wp_launcher.util;

/**
 * Created by Biguncler on 17/01/2017.
 */

public class Week {
    public static final String Monday="Monday";
    public static final String Tuesday="Tuesday";
    public static final String Wednesday="Wednesday";
    public static final String Thursday="Thursday";
    public static final String Friday="Friday";
    public static final String Saturday="Saturday";
    public static final String Sunday="Sunday";



    public static String getWeek(int week ){
        if(week==0){
            return "";
        }else if(week==2){
            return Monday;
        }else if(week==3){
            return Tuesday;
        }else if(week==4){
            return Wednesday;
        }else if(week==5){
                return Thursday;
        }else if(week==6){
            return Friday;
        }else if(week==7){
            return Saturday;
        }else if(week==1){
            return Sunday;
        }
        return "";
    }
}
