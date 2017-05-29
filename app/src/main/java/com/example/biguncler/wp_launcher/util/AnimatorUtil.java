
package com.example.biguncler.wp_launcher.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

/**
 * Created by Biguncler on 03/05/2017.
 */

public class AnimatorUtil {
    public static final String SCALE="scale";
    public static final String SCALE_X="scaleX";
    public static final String SCALE_Y="scaleY";
    public static final String ALPHA="alpha";
    public static final String ROTATION="rotation";
    public static final String ROTATION_X="rotationX";
    public static final String ROTATION_Y="rotationY";
    public static final String TRANSLATION_X="X";
    public static final String TRANSLATION_Y="Y";
    public static final String TRANSLATION="translation";

    public static AnimatorUtil instance;

    public static AnimatorUtil getInstance(){
        if(instance==null){
            synchronized (AnimatorUtil.class){
                return instance=new AnimatorUtil();
            }
        }else{
            return instance;
        }
    }

    public void startAnimator(View target, String propertyName, float start, float end, float pivotX, float pivotY,int duration, TimeInterpolator timeInterpolator, AnimatorListenerAdapter listenerAdapter){
        if(pivotX>=0)target.setPivotX(pivotX);
        if(pivotY>=0)target.setPivotY(pivotY);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(target,propertyName,start,end);
        objectAnimator.setDuration(duration);
        if(timeInterpolator!=null)objectAnimator.setInterpolator(timeInterpolator);
        if(listenerAdapter!=null) objectAnimator.addListener(listenerAdapter);
        objectAnimator.start();
    }

    public void startAnimator(View target, String propertyName, float start, float end,int duration, TimeInterpolator timeInterpolator, AnimatorListenerAdapter listenerAdapter){
        startAnimator(target,propertyName,start,end,-1f,-1f,duration,timeInterpolator,listenerAdapter);
    }


    public void startAnimatorSet(int duration, TimeInterpolator timeInterpolator, AnimatorListenerAdapter listenerAdapter,Animator...animators){
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(duration);
        if(timeInterpolator!=null)animatorSet.setInterpolator(timeInterpolator);
        if(listenerAdapter!=null) animatorSet.addListener(listenerAdapter);
        animatorSet.playTogether(animators);
        animatorSet.start();
    }


    public ObjectAnimator getObjectAnimator(View target, String propertyName, float start, float end, float pivotX, float pivotY){
        if(pivotX>=0)target.setPivotX(pivotX);
        if(pivotY>=0)target.setPivotY(pivotY);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(target,propertyName,start,end);
        return objectAnimator;
    }


    public ObjectAnimator getObjectAnimator(View target, String propertyName, float start, float end){
        return getObjectAnimator(target,propertyName,start,end,-1f,-1f);
    }



}
