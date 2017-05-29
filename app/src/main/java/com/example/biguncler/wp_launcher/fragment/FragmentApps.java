package com.example.biguncler.wp_launcher.fragment;

import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.AnimatorUtil;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.example.biguncler.wp_launcher.view.AppsLayout;
import com.example.biguncler.wp_launcher.view.ColorsLayout;
import com.example.biguncler.wp_launcher.view.InputMethodLayout;

import java.util.ArrayList;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentApps extends BaseFragment {
    private Button btText;
    private InputMethodLayout inputLayout;
    private AppsLayout appsLayout;
    private ColorsLayout colorsLayout;
    private RadioGroup radioGroup;
    private boolean isMove=true;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout layout= (FrameLayout) inflater.inflate(R.layout.fragment_apps,container,false);
        btText= (Button) layout.findViewById(R.id.view_bt_search_app);
        inputLayout= (InputMethodLayout) layout.findViewById(R.id.layout_ll_input_method);
        appsLayout= (AppsLayout) layout.findViewById(R.id.layout_apps);
        colorsLayout= (ColorsLayout) layout.findViewById(R.id.layout_colors);
        radioGroup= (RadioGroup) layout.findViewById(R.id.view_rg);
        initListener();

        radioGroup.check(R.id.view_rbt_apps);

        if(MyApplication.isLightTheme){
            btText.setBackgroundResource(R.drawable.shape_bt_bg_dark);
            btText.setTextColor(Color.BLACK);
        }else{
            btText.setBackgroundResource(R.drawable.shape_bt_bg_light);
            btText.setTextColor(Color.WHITE);
        }

        inputLayout.setVisibility(View.GONE);
        if(MyApplication.isLightTheme){
            inputLayout.setBackgroundColor(Color.LTGRAY);
            inputLayout.setTextsColor(Color.BLACK);
        }else{
            inputLayout.setBackgroundColor(Color.DKGRAY);
            inputLayout.setTextsColor(Color.WHITE);
        }




        return layout;
    }

    private void initListener() {
        inputLayout.setTextWatcher(new InputMethodLayout.TextWatcher() {
            @Override
            public void onTextChangedListener(String text) {
                btText.setText(text);
                if(TextUtils.isEmpty(text.trim())){
                    appsLayout.getAdapter().setList(MyApplication.apps);
                    appsLayout.getAdapter().notifyDataSetChanged();
                }else {
                    appsLayout.getAdapter().setList(new AppManager().getInstalledAppByName(MyApplication.apps, text));
                    appsLayout.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onGOClickListener() {
                if(TextUtils.isEmpty(inputLayout.getText())) return;
                if(appsLayout.getAdapter().getList().size()>0){
                    boolean result= AppUtil.luanchApp(getActivity(),appsLayout.getAdapter().getList().get(0).getPackageName(),appsLayout.getGridView().getChildAt(0));
                    if(!result){
                        Toast.makeText(getActivity(),"启动失败",Toast.LENGTH_SHORT).show();;
                    }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.view_rbt_apps:
                        appsLayout.setVisibility(View.VISIBLE);
                        colorsLayout.setVisibility(View.GONE);
                        break;
                    case R.id.view_rbt_colors:
                        appsLayout.setVisibility(View.GONE);
                        colorsLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        btText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputLayout(null);
            }
        });

        appsLayout.getGridView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i==1) isMove=false; // 1 为从静止到开始移动的状态
                if(i==2) {// 2 为移动状态
                    isMove=true;
                    if(TextUtils.isEmpty(inputLayout.getText())){
                        dismissInputLayout(null);
                    }
                }
                if(i==0) {// 0 为从移动到静止状态
                    if (!isMove) {
                        showInputLayout(null);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                isMove=true;

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if(!TextUtils.isEmpty(inputLayout.getText())){
            inputLayout.setText("");
        }
        dismissInputLayout(null);

    }

    @Override
    public void onWallpaperChanged(Intent intent) {
        super.onWallpaperChanged(intent);
        appsLayout.getAdapter().notifyDataSetChanged();
        if(MyApplication.isLightTheme){
            btText.setBackgroundResource(R.drawable.shape_bt_bg_dark);
            btText.setTextColor(Color.BLACK);
        }else{
            btText.setBackgroundResource(R.drawable.shape_bt_bg_light);
            btText.setTextColor(Color.WHITE);
        }

        if(MyApplication.isLightTheme){
            inputLayout.setBackgroundColor(Color.LTGRAY);
            inputLayout.setTextsColor(Color.BLACK);
        }else{
            inputLayout.setBackgroundColor(Color.DKGRAY);
            inputLayout.setTextsColor(Color.WHITE);
        }

    }

    @Override
    public void onAppUninstalled(Intent intent) {
        super.onAppUninstalled(intent);
        appsLayout.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onAppInstalled(Intent intent) {
        super.onAppInstalled(intent);
        appsLayout.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        appsLayout.getAdapter().notifyDataSetChanged();
        radioGroup.check(R.id.view_rbt_apps);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        // 1为该fragment在viewpager中的下标
        if(position!=1){
            if(!TextUtils.isEmpty(inputLayout.getText())){
                inputLayout.setText("");
            }
            dismissInputLayout(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!TextUtils.isEmpty(inputLayout.getText())){
            inputLayout.setText("");
        }
        dismissInputLayout(null);
    }

    private void showInputLayout(AnimatorListenerAdapter listenerAdapter){
        if(inputLayout.getVisibility()==View.GONE){
            inputLayout.setVisibility(View.VISIBLE);
            int startY2= ScreenUtil.getScreenHeight(getActivity());
            // 250+22 22为状态栏高度
            int endY2=startY2- PixUtil.dip2px(getActivity(),272);
            int pivotX2=0;
            int pivotY2=ScreenUtil.getScreenHeight(getActivity());
            AnimatorUtil.getInstance().startAnimator(inputLayout,AnimatorUtil.TRANSLATION_Y,startY2,endY2,pivotX2,pivotY2,250,null,listenerAdapter);
        }

    }

    private void dismissInputLayout(AnimatorListenerAdapter listenerAdapter) {
        if (inputLayout.getVisibility() == View.VISIBLE) {
            int endY2 = ScreenUtil.getScreenHeight(getActivity());
            int startY2 = ScreenUtil.getScreenHeight(getActivity()) - PixUtil.dip2px(getActivity(), 272);
            int pivotX2 = 0;
            int pivotY2 = ScreenUtil.getScreenHeight(getActivity()) - PixUtil.dip2px(getActivity(), 272);
            AnimatorUtil.getInstance().startAnimator(inputLayout, AnimatorUtil.TRANSLATION_Y, startY2, endY2, pivotX2, pivotY2, 250, null, listenerAdapter);
            inputLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputLayout.setVisibility(View.GONE);
                }
            },250);
        }
    }
}
