package com.example.biguncler.wp_launcher.presenter;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.biguncler.wp_launcher.activity.MainActivity;
import com.example.biguncler.wp_launcher.activity.MetroColorActivity;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mvpview.IBaseView;
import com.example.biguncler.wp_launcher.mvpview.ISettingView;
import com.example.libutil.WallpaperUtil;

/**
 * Created by Biguncler on 2/1/2019.
 */

public class SettingPresenter implements ISettingPrestenter {
    private ISettingView view;
    @Override
    public void detachView() {
        view=null;
    }

    @Override
    public void attachView(IBaseView view) {
        this.view= (ISettingView) view;
    }

    @Override
    public Drawable getBackgroudDrawable() {
        return new BitmapDrawable(WallpaperUtil.getBlureWallpaper(view.getActivity()));
    }

    @Override
    public void forwardMetroColorPage() {
        Intent intent=new Intent();
        view.startActivity(intent, MetroColorActivity.class,0);
    }

    @Override
    public int getMetroColor() {
        return Integer.valueOf(SharedPreferenceDB.getString(view.getActivity(), SharedPreferenceDB.METRO_COLOR));
    }

    @Override
    public void updateSwitch(String key) {
        boolean state=getSwitchState(key);
        SharedPreferenceDB.saveBoolean(view.getActivity(),key,!state);
        if(SharedPreferenceDB.SWITCH_LOCK.equals(key)){
            view.updateLockSwitch(!state);
        }else if(SharedPreferenceDB.SWITCH_HOME.equals(key)){
            view.updateHomeSwitch(!state);
            Intent intent=new Intent(MainActivity.ACTION_hOME_SWITCH);
            view.getActivity().sendBroadcast(intent);
        }
    }

    @Override
    public boolean getSwitchState(String key) {
        return SharedPreferenceDB.getBoolean(view.getActivity(),key);
    }
}
