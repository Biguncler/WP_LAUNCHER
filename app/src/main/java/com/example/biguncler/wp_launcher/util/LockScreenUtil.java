package com.example.biguncler.wp_launcher.util;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.biguncler.wp_launcher.broadcastReceiver.MyDeviceAdminReceiver;

/**
 * Created by Biguncler on 24/05/2018.
 */

public class LockScreenUtil {
    public static void lock(Context context) {
        // 设备安全管理服务    2.2之前的版本是没有对外暴露的 只能通过反射技术获取
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        // 申请权限
        ComponentName componentName = new ComponentName(context, MyDeviceAdminReceiver.class);
        // 判断该组件是否有系统管理员的权限
        boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
        if (isAdminActive) {

            devicePolicyManager.lockNow(); // 锁屏

            //devicePolicyManager.resetPassword("123", 0); // 设置锁屏密码
//            devicePolicyManager.wipeData(0);  恢复出厂设置  (建议大家不要在真机上测试) 模拟器不支持该操作

        } else {
            Intent intent2 = new Intent();
            // 指定动作名称
            intent2.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            // 指定给哪个组件授权
            intent2.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            context.startActivity(intent2);
        }
    }
}
