package com.example.biguncler.wp_launcher.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.ContactCellAdapter;
import com.example.biguncler.wp_launcher.mode.ContactCellInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactCellView extends LinearLayout {
    private GridView gridView;
    private ContactCellAdapter adapter;

    public ContactCellView(Context context) {
        super(context);
        init();
    }

    public ContactCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContactCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gridView = new LineGridView(getContext());
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        addView(gridView,params);
        gridView.setNumColumns(6);
        gridView.setScrollbarFadingEnabled(false);
        gridView.setVerticalScrollBarEnabled(false);
        adapter = new ContactCellAdapter(getContext());
        gridView.setAdapter(adapter);

        setData(getDatas());

        Observable.interval(3,4, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(final Long aLong) throws Exception {
                int count = gridView.getChildCount();
                if(count>0){
                    int postion=(int)(Math.random()*(count));
                    final ViewGroup view= (ViewGroup) gridView.getChildAt(postion);
                    if(view!=null){
                        float rotaion=view.getRotationX();
                        Log.i("ContactCellView","rotaion="+rotaion);
                        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationX", 180, 360);
                        animator.setInterpolator(new AnticipateOvershootInterpolator());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                               float value= (float) valueAnimator.getAnimatedValue();
                                if(180+84<value&&value<96+180){
                                    Log.i("ContactCellView","-------value="+value);
                                    for(int i=0;i<view.getChildCount();i++){
                                        View child=view.getChildAt(i);
                                        if(child.getVisibility()==View.VISIBLE){
                                            child.setVisibility(View.INVISIBLE);
                                        }else{
                                            child.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    animator.removeUpdateListener(this);
                                }
                            }
                        });
                        animator.setDuration(2000).start();
                    }
                }
            }
        });
    }


    public void setData(List<ContactCellInfo> data) {
        adapter.updateDate(data);
    }

    public void setNumColumns(int num) {
       // gridView.setNumColumns(num);
    }

    public List<ContactCellInfo> getDatas(){
        List<ContactCellInfo> list=new ArrayList<>();
        for(int i=0;i<18;i++){
            ContactCellInfo info=new ContactCellInfo();
            int index=i*2;
            info.setFbgText(contacts[index]);
            info.setBbgText(contacts[index+1]);
            list.add(info);
        }
        return list;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private String[] contacts=new String[]{"魏","黄","王","赵","钟","朱","刘","温","陈","徐",
        "唐","李","余","廖","杨","曾","郑","周","宋","秦",
    "何","张","曹","金","欧","胡","林","叶","程","赖",
    "杜","梁","岳","孙","沈","吴"};
}
