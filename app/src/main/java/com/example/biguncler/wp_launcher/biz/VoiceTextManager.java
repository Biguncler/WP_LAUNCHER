package com.example.biguncler.wp_launcher.biz;

import android.content.Context;
import android.text.TextUtils;

import com.example.biguncler.wp_launcher.util.CharUtil;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Biguncler on 11/05/2018.
 */

public class VoiceTextManager {
    public static HashMap<String, String> map;
    private Context context;

    {
        map = new HashMap<>();
        map.put("支付宝", "ALIPAY");
        map.put("高德地图", "AMAP");
        map.put("百度", "BAIDU");
        map.put("浏览器", "BROWSER");
        map.put("日历", "CALENDAR");
        map.put("计算器", "CALCULATOR");
        map.put("相机", "CAMERA");
        map.put("谷歌浏览器", "CHROME");
        map.put("闹钟", "CLOCK");
        map.put("联系人", "CONTACTS");
        map.put("滴滴", "DIDI");
        map.put("文件管理器", "FILE_MANAGER");
        map.put("相册", "GALLERY");
        map.put("信息", "MESSAGING");
        map.put("设置", "SETTINGS");
        map.put("天气", "WEATHER");
        map.put("微信", "WECHAT");
        map.put("摩拜", "MOBIKE");
        map.put("摩拜", "MOBIKE");
        map.put("微博", "WEIBO");
        map.put("为知笔记", "WIZNOTE");
    }

    public VoiceTextManager(Context context) {
        this.context = context;
    }

    public String analyseText(String text) {
        text = filter(text);
        return transfer(text);

    }

    public String filter(String text) {
        if (!TextUtils.isEmpty(text) && text.startsWith("打开")) {
            text = text.replace("打开", "")
                    .replace(",", "")
                    .replace("，", "")
                    .replace(".", "")
                    .replace("。", "");
        }

        return text;
    }

    public String transfer(String text) {
        if (!TextUtils.isEmpty(text)) {
            Locale curLocale = context.getResources().getConfiguration().locale;
            if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
                //中文
                return chineseEnv(text);
            } else if (curLocale.equals(Locale.ENGLISH)) {
                //英文
                return englishEnv(text);
            }
        }
        return "";
    }

    public String chineseEnv(String text) {
        if (CharUtil.isChinese(text)) {
            return CharUtil.getPinYinHeadChar(text).toUpperCase();
        } else {
            return text;
        }
    }

    public String englishEnv(String text) {
        if (map.containsKey(text)) {
            return map.get(text);
        }
        return text;
    }


}
