package com.example.biguncler.wp_launcher.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.util.Month;
import com.example.biguncler.wp_launcher.util.Week;
import com.example.libutil.Arith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Biguncler on 3/21/2019.
 */

public class BatteryCellView extends LinearLayout {
    private WaveView waveView;
    private TextView tvPercent;
    private Context context;

    public BatteryCellView(Context context) {
        super(context);
        init(context);
    }

    public BatteryCellView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        init(context);
    }

    public BatteryCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View layout = LayoutInflater.from(context).inflate(R.layout.cell_battery, this);
        waveView = (WaveView) layout.findViewById(R.id.wave_view);
        tvPercent = (TextView) layout.findViewById(R.id.tv_percent);
        waveView.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(receiver, new IntentFilter(intentFilter));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        context.unregisterReceiver(receiver);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int curentLevel = intent.getIntExtra("level", 0);
                //电量的总刻度
                int totalLevel = intent.getIntExtra("scale", 100);
                float level = (float) Arith.div(curentLevel, totalLevel, 2);
                waveView.setWaveHeightPercent(level);
                tvPercent.setText(String.valueOf(curentLevel));
                Log.i("wjs","curentLevel="+curentLevel+"/totalLevel="+totalLevel);
            }
        }
    };
}
