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
    private SeekBar tranSeekBar ,spaceSeekBar ,columnSeekBar;
    private TextView tranProgress , tvSpace ,tvColumn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_setting);
        tranSeekBar = (SeekBar) findViewById(R.id.sb_transparency);
        tranProgress = (TextView) findViewById(R.id.tv_transparency_progress);

        spaceSeekBar = (SeekBar) findViewById(R.id.sb_spacing);
        tvSpace = (TextView) findViewById(R.id.tv_spacing_progress);

        columnSeekBar = (SeekBar) findViewById(R.id.sb_column);
        tvColumn = (TextView) findViewById(R.id.tv_column_progress);


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

        int spacing = SharedPreferenceDB.getInt(this,SharedPreferenceDB.TILE_SPACING);
        spaceSeekBar.setProgress(spacing);
        tvSpace.setText(String.valueOf(spacing));
        spaceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvSpace.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceDB.saveInt(TileSettingActivity.this,SharedPreferenceDB.TILE_SPACING,seekBar.getProgress());
                sendBroadcast(new Intent(FragmentHome.ACTION_UPDATE_TILE_SPACIING));
            }
        });
        updateSeekbar(spaceSeekBar);

        int column = SharedPreferenceDB.getInt(this,SharedPreferenceDB.TILE_COLUMN);
        columnSeekBar.setProgress(column);
        tvColumn.setText(String.valueOf(column));
        columnSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvColumn.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceDB.saveInt(TileSettingActivity.this,SharedPreferenceDB.TILE_COLUMN,seekBar.getProgress());
                sendBroadcast(new Intent(FragmentHome.ACTION_UPDATE_TILE_COLUMN));
            }
        });
        updateSeekbar(columnSeekBar);



    }

    @Override
    protected void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        updateSeekbar(tranSeekBar);
        updateSeekbar(spaceSeekBar);
    }

    private void updateSeekbar(SeekBar seekBar){
        LayerDrawable layerDrawable= (LayerDrawable) seekBar.getProgressDrawable();
        Drawable drawable =layerDrawable.findDrawableByLayerId(android.R.id.progress);
        if(drawable!=null){
            DrawableCompat.setTint(drawable, Integer.valueOf(SharedPreferenceDB.getString(this,SharedPreferenceDB.METRO_COLOR)));
            layerDrawable.setDrawableByLayerId(android.R.id.progress,drawable);
        }
    }

    @Override
    protected void onPressHome(Intent intent) {
        super.onPressHome(intent);
        finish();
    }
}
