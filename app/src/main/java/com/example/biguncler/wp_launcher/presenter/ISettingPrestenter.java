package com.example.biguncler.wp_launcher.presenter;

import android.graphics.drawable.Drawable;

/**
 * Created by Biguncler on 2/1/2019.
 */

public interface ISettingPrestenter extends IBasePresenter {
    /**
     * 获取背景drawable
     * @return
     */
    Drawable getBackgroudDrawable();

    void forwardMetroColorPage();

    int getMetroColor();
    void updateSwitch(String key,boolean open);
    boolean getSwitchState(String key);
}
