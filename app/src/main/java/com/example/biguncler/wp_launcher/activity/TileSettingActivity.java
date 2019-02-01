package com.example.biguncler.wp_launcher.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.fragment.FragmentHome;


/**
 * Created by Biguncler on 2/1/2019.
 */

public class TileSettingActivity extends BaseActivity {
    private SeekBar tranSeekBar;
    private TextView tranProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_setting);
        tranSeekBar = (SeekBar) findViewById(R.id.sb_transparency);
        tranProgress = (TextView) findViewById(R.id.tv_transparency_progress);
        int progress = SharedPreferenceDB.getInt(this,SharedPreferenceDB.TILE_TRANSPARENCY);
        tranSeekBar.setProgress(progress);
        tranProgress.setText(String.valueOf(progress));
        tranSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tranProgress.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceDB.saveInt(TileSettingActivity.this,SharedPreferenceDB.TILE_TRANSPARENCY,seekBar.getProgress());
                sendBroadcast(new Intent(FragmentHome.ACTION_UPDATE_TILE_TRANSPARENCY));
            }
        });
        updateSeekbar(tranSeekBar);
    }

    @Override
    protected void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        updateSeekbar(tranSeekBar);
    }

    private void updateSeekbar(SeekBar seekBar){
        LayerDrawable layerDrawable= (LayerDrawable) seekBar.getProgressDrawable();
        Drawable drawable =layerDrawable.findDrawableByLayerId(android.R.id.progress);
        if(drawable!=null){
            DrawableCompat.setTint(drawable, Integer.valueOf(SharedPreferenceDB.getString(this,SharedPreferenceDB.METRO_COLOR)));
            layerDrawable.setDrawableByLayerId(android.R.id.progress,drawable);
        }
    }
}
