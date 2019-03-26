package com.example.biguncler.wp_launcher.util;

/**
 * Created by Biguncler on 17/01/2017.
 */

public class Month {

    public static final String January="January";
    public static final String February="February";
    public static final String March="March";
    public static final String April="April";
    public static final String May="May";
    public static final String June="June";
    public static final String July="July";
    public static final String August="August";
    public static final String September="September";
    public static final String October="October";
    public static final String November="November";
    public static final String December="December";



    public static String getMonth(int month){
        if(month==0){
            return January;
        }else if(month==1){
            return February;
        }else if(month==2){
            return March;
        }else if(month==3){
            return April;
        }else if(month==4){
            return May;
        }else if(month==5){
            return June;
        }else if(month==6){
            return July;
        }else if(month==7){
            return August;
        }else if(month==8){
            return September;
        }else if(month==9){
            return October;
        }else if(month==10){
            return November;
        }else if(month==11){
            return December;
        }else{
            return "";
        }
    }

}
