package com.example.biguncler.wp_launcher.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.util.AnimatorUtil;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.example.libtheme.ThemeHelper;
import com.example.libutil.BitmapUtil;
import com.example.libutil.WallpaperUtil;
import com.example.biguncler.wp_launcher.view.AppsLayout;
import com.example.biguncler.wp_launcher.view.MetroColorsLayout;
import com.example.biguncler.wp_launcher.view.InputMethodLayout;
import com.example.biguncler.wp_launcher.view.ReboundGridView;
import com.example.biguncler.wp_launcher.view.ThemeColorsLayout;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentApps extends BaseFragment {
    private Button btText;
    private InputMethodLayout inputLayout;
    private AppsLayout appsLayout;
    private LinearLayout colorsLayout;
    private MetroColorsLayout metroColorsLayout;
    private ThemeColorsLayout themeColorsLayout;
    private RadioGroup radioGroupMetro;
    private ImageView ivSetting;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout layout= (FrameLayout) inflater.inflate(R.layout.fragment_apps,container,false);
        btText= (Button) layout.findViewById(R.id.view_bt_search_app);
        inputLayout= (InputMethodLayout) layout.findViewById(R.id.layout_ll_input_method);
        appsLayout= (AppsLayout) layout.findViewById(R.id.layout_apps);
        colorsLayout= (LinearLayout) layout.findViewById(R.id.layout_colors);
        themeColorsLayout= (ThemeColorsLayout) layout.findViewById(R.id.layout_theme_colors);
        metroColorsLayout = (MetroColorsLayout) layout.findViewById(R.id.layout_metro_colors);
        radioGroupMetro= (RadioGroup) layout.findViewById(R.id.view_rg);
        ivSetting = (ImageView) layout.findViewById(R.id.iv_setting);
        initListener();

        radioGroupMetro.check(R.id.view_rbt_apps);
        setRadioGroupButtonBG(radioGroupMetro);

        setBtTextTheme();
        inputLayout.setVisibility(View.GONE);
        setInputLayoutTheme();
        setViewBG(ivSetting);

        //new FloatBallManager(getActivity()).showFloatBall();

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
        radioGroupMetro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                dismissInputLayout(null);
                btText.setEnabled(false);
                inputLayout.setText("");
                switch (id){
                    case R.id.view_rbt_apps:
                        AnimatorUtil.getInstance().startAnimator(colorsLayout, AnimatorUtil.ALPHA, 1f, 0f, 0, 0, 200, null, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                colorsLayout.setVisibility(View.GONE);
                                appsLayout.setVisibility(View.VISIBLE);
                                AnimatorUtil.getInstance().startAnimator(appsLayout, AnimatorUtil.ALPHA, 0f, 1f, 0, 0, 400, null, null);
                            }
                        });
                        btText.setEnabled(true);
                        break;
                    case R.id.view_rbt_colors:
                        AnimatorUtil.getInstance().startAnimator(appsLayout, AnimatorUtil.ALPHA, 1f, 0f, 0, 0, 200, null, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                appsLayout.setVisibility(View.GONE);
                                colorsLayout.setVisibility(View.VISIBLE);
                                AnimatorUtil.getInstance().startAnimator(colorsLayout, AnimatorUtil.ALPHA, 0f, 1f, 0, 0, 400, null, null);

                            }
                        });
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
        /*btText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AppUtil.luanchApp(getActivity(),MyApplication.appMap.get("BAIDU"),btText);
                return true;
            }
        });*/

        ((ReboundGridView)appsLayout.getGridView()).setPullFinishListener(new ReboundGridView.OnPullFinishListener() {
            @Override
            public void onPullDownFinish() {
                showInputLayout(null);
            }

            @Override
            public void onPullUpFinish() {

            }
        });
        ((ReboundGridView)appsLayout.getGridView()).setOnScrollBoundListener(new ReboundGridView.OnScrollBoundListener() {
            @Override
            public void onScrollTop() {

            }

            @Override
            public void onScrollBottom() {

            }

            @Override
            public void onScrolling() {
                if(TextUtils.isEmpty(inputLayout.getText())){
                   dismissInputLayout(null);
                }
            }
        });
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        appsLayout.getGridView().smoothScrollToPosition(0);

    }

    @Override
    public void onWallpaperChanged(Intent intent) {
        super.onWallpaperChanged(intent);
        //appsLayout.getAdapter().notifyDataSetChanged();
        //setBtTextTheme();
        //setInputLayoutTheme();
        //setRadioGroupButtonBG(radioGroupMetro);
        radioGroupMetro.check(R.id.view_rbt_apps);
    }

    @Override
    public void onAppUninstalled(Intent intent) {
        super.onAppUninstalled(intent);
        appsLayout.getAdapter().setList(MyApplication.apps);
        appsLayout.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onAppInstalled(Intent intent) {
        super.onAppInstalled(intent);
        appsLayout.getAdapter().setList(MyApplication.apps);
        appsLayout.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        appsLayout.getAdapter().notifyDataSetChanged();
        radioGroupMetro.check(R.id.view_rbt_apps);

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
            appsLayout.getGridView().smoothScrollToPosition(0);
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
            // 200+22 22为状态栏高度
            int endY2=startY2- PixUtil.dip2px(getActivity(),222);
            int pivotX2=0;
            int pivotY2=ScreenUtil.getScreenHeight(getActivity());
            AnimatorUtil.getInstance().startAnimator(inputLayout,AnimatorUtil.TRANSLATION_Y,startY2,endY2,pivotX2,pivotY2,250,null,listenerAdapter);
        }

    }

    private void dismissInputLayout(AnimatorListenerAdapter listenerAdapter) {
        if (inputLayout.getVisibility() == View.VISIBLE) {
            int endY2 = ScreenUtil.getScreenHeight(getActivity());
            int startY2 = ScreenUtil.getScreenHeight(getActivity()) - PixUtil.dip2px(getActivity(), 222);
            int pivotX2 = 0;
            int pivotY2 = ScreenUtil.getScreenHeight(getActivity()) - PixUtil.dip2px(getActivity(), 222);
            AnimatorUtil.getInstance().startAnimator(inputLayout, AnimatorUtil.TRANSLATION_Y, startY2, endY2, pivotX2, pivotY2, 250, null, listenerAdapter);
            inputLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputLayout.setVisibility(View.GONE);
                }
            },250);
        }
    }


    public void setRadioGroupButtonBG(RadioGroup radioGroup){
        // 设置radioButton的背景
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for(int i=0;i<radioGroup.getChildCount();i++){
                RadioButton radioButton= (RadioButton) radioGroup.getChildAt(i);
                Drawable drawable=radioButton.getButtonDrawable();
                if(drawable!=null){
                    DrawableCompat.setTint(drawable, ThemeHelper.getTintIcColor(getActivity()));
                    radioButton.setButtonDrawable(drawable);
                }
            }
        }
    }

    private void setViewBG(View view){
        Drawable drawable =view.getBackground();
        if(drawable!=null){
            DrawableCompat.setTint(drawable, ThemeHelper.getTintIcColor(getActivity()));
            view.setBackground(drawable);
        }
    }


    private void setBtTextTheme(){
       Drawable drawable= btText.getBackground();
        if(drawable!=null){
            DrawableCompat.setTint(drawable, ThemeHelper.getTintIcColor(getActivity()));
            btText.setBackground(drawable);
        }
    }

    private void setInputLayoutTheme(){
        inputLayout.setBtsBackground(ThemeHelper.getTintBgColor(getActivity()));
        inputLayout.setTextsColor(ThemeHelper.getTextColor(getActivity()));
        try{
            inputLayout.setBackground(new BitmapDrawable(getInputMethodBG()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private Bitmap getInputMethodBG(){
        try{
            int screenWidth=ScreenUtil.getScreenWidth(getActivity());
            int screenHeight=ScreenUtil.getScreenHeight(getActivity());
            int height=PixUtil.dip2px(getActivity(),200);
            Bitmap bitmap=BitmapUtil.cropBitmap(WallpaperUtil.getWallpaper(getActivity()),0,screenHeight-height,screenWidth,height);
            return BitmapUtil.getBlurBitmap(bitmap,150,false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
