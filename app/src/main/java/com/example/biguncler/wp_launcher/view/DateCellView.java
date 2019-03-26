package com.example.biguncler.wp_launcher.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.util.Month;
import com.example.biguncler.wp_launcher.util.Week;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Biguncler on 3/21/2019.
 */

public class DateCellView extends LinearLayout {
    private TextView tvTime;
    private TextView tvDate;
    private Context context;

    public DateCellView(Context context) {
        super(context);
        init(context);
    }

    public DateCellView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        init(context);
    }

    public DateCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View layout = LayoutInflater.from(context).inflate(R.layout.cell_date, this);
        tvTime = (TextView) layout.findViewById(R.id.tv_time);
        tvDate = (TextView) layout.findViewById(R.id.tv_date);
        tvTime.setText(getTime().toUpperCase());
        tvDate.setText(getDate().toUpperCase());

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
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
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                tvTime.setText(getTime().toUpperCase());
                tvDate.setText(getDate().toUpperCase());
            }
        }
    };

    private String getDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            String str_month = month > 10 ? (month + "") : ("0" + month);
            String str_day = day > 10 ? (day + "") : ("0" + day);
            //String date=Week.getWeek(week)+" , "+year+"-"+str_month+"-"+str_day+" , "+getTime();
            String date = Week.getWeek(week) + " " + Month.getMonth(month) + " " + day;
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


    /**
     * 获取当前的时间
     *
     * @return
     */
    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(System.currentTimeMillis()));
    }
}
