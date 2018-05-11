package com.example.floatball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Biguncler on 11/05/2018.
 */

public class FloatBallManager {
    private Context context;


    public FloatBallManager(Context context) {
        this.context = context;
    }


    private void checkAccessibility() {
        check();
        // 判断辅助功能是否开启
        if (!AccessibilityUtil.isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(context, "请先开启FloatBall辅助功能", Toast.LENGTH_SHORT).show();
        }
    }

    public void showFloatBall() {
        checkAccessibility();
        Intent intent = new Intent(context, FloatBallService.class);
        Bundle data = new Bundle();
        data.putInt("type", FloatBallService.TYPE_ADD);
        intent.putExtras(data);
        context.startService(intent);
    }

    public void dismissFloatBall() {
        Intent intent = new Intent(context, FloatBallService.class);
        Bundle data = new Bundle();
        data.putInt("type", FloatBallService.TYPE_DEL);
        intent.putExtras(data);
        context.startService(intent);
    }

    private void check(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity)context).startActivityForResult(intent, 1);
                Toast.makeText(context, "请先允许FloatBall出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
