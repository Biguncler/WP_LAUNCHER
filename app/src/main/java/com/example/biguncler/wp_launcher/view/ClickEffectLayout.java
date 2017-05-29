package com.example.biguncler.wp_launcher.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.util.AnimatorUtil;

/**
 * Created by Biguncler on 05/29/17.
 */

public class ClickEffectLayout extends LinearLayout {
    private Context context;
    public ClickEffectLayout(Context context) {
        super(context);
        init(context);
    }

    public ClickEffectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClickEffectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        //initListener();

    }
    private void initListener(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"hello1",Toast.LENGTH_LONG).show();
            }
        });
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context,"hello2",Toast.LENGTH_LONG).show();

                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downAnim();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                upAnim();
                break;
            case MotionEvent.ACTION_CANCEL:
                upAnim();
                break;
        }
        return super.dispatchTouchEvent(event);
    }




    private void downAnim() {
        ObjectAnimator animatorX = AnimatorUtil.getInstance().getObjectAnimator(this, AnimatorUtil.SCALE_X, 1.0f, 0.9f, -1, -1);
        ObjectAnimator animatorY = AnimatorUtil.getInstance().getObjectAnimator(this, AnimatorUtil.SCALE_Y, 1.0f, 0.9f, -1, -1);
        AnimatorUtil.getInstance().startAnimatorSet(200, null, null, animatorX, animatorY);

    }

    private void upAnim() {
        ObjectAnimator animatorX = AnimatorUtil.getInstance().getObjectAnimator(this, AnimatorUtil.SCALE_X, 0.9f, 1.0f, -1, -1);
        ObjectAnimator animatorY = AnimatorUtil.getInstance().getObjectAnimator(this, AnimatorUtil.SCALE_Y, 0.9f, 1.0f, -1, -1);
        AnimatorUtil.getInstance().startAnimatorSet(200, null, null, animatorX, animatorY);
    }



}
