package com.example.biguncler.wp_launcher.mvpview;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Biguncler on 2/1/2019.
 */

public interface ISettingView extends IBaseView {
    Activity getActivity();
    void startActivity(Intent intent ,Class cls,int id);
}
