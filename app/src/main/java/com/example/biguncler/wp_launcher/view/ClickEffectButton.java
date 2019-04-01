package com.example.biguncler.wp_launcher.view;

import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.libutil.ColorUtil;

/**
 * Created by Biguncler on 05/29/17.
 */

public class ClickEffectButton extends Button {
    private Context context;
    private int  bgColor;
    public ClickEffectButton(Context context) {
        super(context);
        init(context);
    }

    public ClickEffectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClickEffectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;



    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bgColor= ((ColorDrawable) getBackground()).getColor();
                int color= Integer.valueOf(SharedPreferenceDB.getString(context,SharedPreferenceDB.METRO_COLOR));
                int transparency= 255-(int)(255*SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_TRANSPARENCY)/100f);
                color= ColorUtil.setColorAlpha(color,transparency);
                setBackgroundColor(color);
                downAnim();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                upAnim();
                setBackgroundColor(bgColor);
                break;
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(bgColor);
                upAnim();
                break;
        }
        return super.dispatchTouchEvent(event);
    }




    private void downAnim() {
        // 注意 此处的15f的单位为sp，不知道为什么不是像素，getTextSize() 是像素为单位的数值
        anim(15f, 30f, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value= (float) valueAnimator.getAnimatedValue();
                setTextSize(value);
            }
        },null);
    }

    private void upAnim() {
        anim(30f, 15f, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value= (float) valueAnimator.getAnimatedValue();
                setTextSize(value);
            }
        },null);
    }


    private void anim(float start, float end, ValueAnimator.AnimatorUpdateListener listener1,AnimatorListenerAdapter listener2){
        ValueAnimator valueAnimator=ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                float start = (float) startValue;
                float end = (float) endValue;
                float result = (float) ((start + fraction * (end - start)));
                return result;
            }
        },start,end);
        valueAnimator.setDuration(200);
        if (listener1 != null) {
            valueAnimator.addUpdateListener(listener1);
        }
        if (listener2 != null) {
            valueAnimator.addListener(listener2);
        }
        valueAnimator.start();
    }



}
