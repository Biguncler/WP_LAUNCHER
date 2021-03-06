package com.example.biguncler.wp_launcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mvpview.ISettingView;
import com.example.biguncler.wp_launcher.presenter.ISettingPrestenter;
import com.example.biguncler.wp_launcher.presenter.SettingPresenter;
import com.example.biguncler.wp_launcher.view.SwitchView;


/**
 * Created by Biguncler on 2/1/2019.
 */

public class SettingActivity extends BaseActivity implements ISettingView ,View.OnClickListener{
    private ISettingPrestenter prestenter;
    private LinearLayout itemMetroColor ,itemLockSwitch ,homeSwitch ,itemTileSetting;
    private ImageView ivMetroColor;
    private SwitchView switchLock ,switchHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prestenter=new SettingPresenter();
        prestenter.attachView(this);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        itemMetroColor = (LinearLayout) findViewById(R.id.layout_item_metro_color);
        itemMetroColor.setOnClickListener(this);
        ivMetroColor = (ImageView) findViewById(R.id.iv_metro_color);
        ivMetroColor.setImageDrawable(new ColorDrawable(prestenter.getMetroColor()));

        itemTileSetting = (LinearLayout) findViewById(R.id.layout_item_tile_setting);
        itemTileSetting.setOnClickListener(this);


        itemLockSwitch = (LinearLayout) findViewById(R.id.layout_item_lock_switch);
        itemLockSwitch.setOnClickListener(this);
        switchLock = (SwitchView) findViewById(R.id.iv_switch_lock);
        switchLock.setSwitchState(prestenter.getSwitchState(SharedPreferenceDB.SWITCH_LOCK));

        homeSwitch = (LinearLayout) findViewById(R.id.layout_item_home_switch);
        homeSwitch.setOnClickListener(this);
        switchHome = (SwitchView) findViewById(R.id.iv_switch_home);
        switchHome.setSwitchState(prestenter.getSwitchState(SharedPreferenceDB.SWITCH_HOME));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prestenter.detachView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_item_metro_color:
                prestenter.forwardMetroColorPage();
                break;
            case R.id.layout_item_tile_setting:
                prestenter.forwardToTileSetting();
                break;
            case R.id.layout_item_lock_switch:
                prestenter.updateSwitch(SharedPreferenceDB.SWITCH_LOCK);
                break;
            case R.id.layout_item_home_switch:
                prestenter.updateSwitch(SharedPreferenceDB.SWITCH_HOME);
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void startActivity(Intent intent, Class cls, int id) {
        intent.setClass(this,cls);
        startActivity(intent);
    }

    @Override
    public void updateLockSwitch(boolean state) {
        switchLock.setSwitchState(state);
    }

    @Override
    public void updateHomeSwitch(boolean state) {
        switchHome.setSwitchState(state);
    }

    @Override
    protected void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        ivMetroColor.setImageDrawable(new ColorDrawable(prestenter.getMetroColor()));
        switchLock.updateSwitchTheme();
        switchHome.updateSwitchTheme();
    }
}
