package com.example.biguncler.wp_launcher.fragment;

import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.activity.SettingActivity;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.util.AnimatorUtil;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.example.biguncler.wp_launcher.util.StatusBarUtil;
import com.example.libtheme.ThemeHelper;
import com.example.libutil.BitmapUtil;
import com.example.libutil.WallpaperUtil;
import com.example.biguncler.wp_launcher.view.AppsLayout;
import com.example.biguncler.wp_launcher.view.InputMethodLayout;
import com.example.biguncler.wp_launcher.view.ReboundGridView;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentApps extends BaseFragment {
    private Button btText;
    private InputMethodLayout inputLayout;
    private AppsLayout appsLayout;
    private ImageView ivSetting;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout layout= (FrameLayout) inflater.inflate(R.layout.fragment_apps,container,false);
        // 设置布局和状态栏的关系
        layout.setPadding(layout.getPaddingLeft(),layout.getPaddingTop()+StatusBarUtil.getStatusBarHeight(getActivity()),layout.getPaddingRight(),layout.getPaddingBottom());
        btText= (Button) layout.findViewById(R.id.view_bt_search_app);
        inputLayout= (InputMethodLayout) layout.findViewById(R.id.layout_ll_input_method);
        appsLayout= (AppsLayout) layout.findViewById(R.id.layout_apps);
        ivSetting = (ImageView) layout.findViewById(R.id.iv_setting);
        initListener();
        setBtTextTheme();
        inputLayout.post(new Runnable() {
            @Override
            public void run() {
                setInputLayoutTheme();
                inputLayout.setVisibility(View.GONE);
            }
        });
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

        btText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputLayout(null);
            }
        });

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
                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
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
    }

    @Override
    public void onTileTransparency(Intent intent) {
        super.onTileTransparency(intent);
        appsLayout.getAdapter().notifyDataSetChanged();
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

            int startY2= ((ViewGroup)inputLayout.getParent()).getHeight();;
            // 200+22 22为状态栏高度
            int endY2=startY2- inputLayout.getHeight();
            int pivotX2=0;
            int pivotY2=startY2;
            AnimatorUtil.getInstance().startAnimator(inputLayout,AnimatorUtil.TRANSLATION_Y,startY2,endY2,pivotX2,pivotY2,250,null,listenerAdapter);
        }

    }

    private void dismissInputLayout(AnimatorListenerAdapter listenerAdapter) {
        if (inputLayout.getVisibility() == View.VISIBLE) {
            int endY2 = ((ViewGroup)inputLayout.getParent()).getHeight();
            int startY2 = endY2 - inputLayout.getHeight();
            int pivotX2 = 0;
            int pivotY2 =startY2;
            AnimatorUtil.getInstance().startAnimator(inputLayout, AnimatorUtil.TRANSLATION_Y, startY2, endY2, pivotX2, pivotY2, 250, null, listenerAdapter);
            inputLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputLayout.setVisibility(View.GONE);
                }
            },250);
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
            Bitmap bitmap=WallpaperUtil.getBlureWallpaper(getActivity());
            float scale=bitmap.getWidth()*1f/ScreenUtil.getScreenWidth(getActivity());
            int inputlayoutHeight= (int) (inputLayout.getHeight()*scale);
            bitmap=BitmapUtil.cropBitmap(bitmap,0,bitmap.getHeight()-inputlayoutHeight,bitmap.getWidth(),inputlayoutHeight);
            return BitmapUtil.getBlurBitmap(bitmap,150,false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
