package com.example.floatball;

import android.os.Handler;

public class TimerHelper {
    private static TimerHelper singleton;
    private OnTimeListener listener;
    private long time;
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (listener != null) {
                listener.onTime();
            }
            stop();
        }
    };

    private TimerHelper() {

    }

    public static TimerHelper getInstance() {
        synchronized (TimerHelper.class) {
            if (singleton == null) {
                singleton = new TimerHelper();
            }
        }
        return singleton;
    }


    public void start(long time, OnTimeListener listener) {
        stop();
        this.time = time;
        this.listener = listener;
        if (time < 1) return;
        handler.postDelayed(runnable, time);
    }

    public void stop() {
        handler.removeCallbacks(runnable);
        time = 0;
        listener = null;

    }

    public interface OnTimeListener {
        void onTime();
    }
}