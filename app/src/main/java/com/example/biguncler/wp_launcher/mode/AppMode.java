package com.example.biguncler.wp_launcher.mode;

import android.graphics.drawable.Drawable;

/**
 * Created by Biguncler on 11/29/16.
 */

public class AppMode {
    private String packageName;
    private String appName;
    private Drawable icon;

    public AppMode(String packageName, String appName, Drawable icon) {
        this.packageName = packageName;
        this.appName = appName;
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

}
