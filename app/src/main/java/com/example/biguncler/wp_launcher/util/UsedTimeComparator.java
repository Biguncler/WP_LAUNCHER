package com.example.biguncler.wp_launcher.util;

import android.app.usage.UsageStats;

import java.util.Comparator;

/**
 * Created by Biguncler on 22/05/2017.
 */

public class UsedTimeComparator implements Comparator {
    @Override
    public int compare(Object object1, Object object2) {
        UsageStats usageStats1= (UsageStats) object1;
        UsageStats usageStats2= (UsageStats) object2;
        // 降序
        return new Long(usageStats2.getLastTimeUsed()).compareTo(new Long(usageStats1.getLastTimeUsed()));
    }
}
