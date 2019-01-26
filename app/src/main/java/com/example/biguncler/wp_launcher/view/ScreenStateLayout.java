package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Biguncler on 1/27/2019.
 */

public class ScreenStateLayout extends LinearLayout {
    private OnScreenStateChangedListener listener;
    public ScreenStateLayout(Context context) {
        super(context);
    }

    public ScreenStateLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenStateLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScreenStateChangedListener(OnScreenStateChangedListener listener){
        this.listener = listener;
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if(screenState==0){
            if(listener!=null) listener.onScreenTurnOff();
        }else if(screenState==1){
            if(listener!=null) listener.onScreenTurnOn();
        }
    }

    public interface OnScreenStateChangedListener{
        void onScreenTurnOn();
        void onScreenTurnOff();
    }
}
