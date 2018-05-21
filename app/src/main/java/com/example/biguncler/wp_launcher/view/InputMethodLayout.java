package com.example.biguncler.wp_launcher.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.VoiceTextManager;
import com.example.biguncler.wp_launcher.util.AppUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Biguncler on 12/4/16.
 */

public class InputMethodLayout extends LinearLayout  implements View.OnClickListener ,View.OnLongClickListener{
    private Context context;
    private String text="";
    private TextWatcher textWatcher;

    private static String[] topKeys=new String[]{"Q","W","E","R","T","Y","U","I","O","P"};
    private static String[] midKeys=new String[]{"A","S","D","F","G","H","J","K","L"};
    private static String[] bottomKeys=new String[]{"GO","Z","X","C","V","B","N","M","DE"};
    private static Map<Integer,String[]> map=new HashMap<>();







    public InputMethodLayout(Context context) {
        super(context);
        init(context);
    }

    public InputMethodLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputMethodLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context){
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        setWeightSum(3);
        map.put(0,topKeys);
        map.put(1,midKeys);
        map.put(2,bottomKeys);
        // 添加上中下布局
        for(int i=0;i<getWeightSum();i++){
            LayoutInflater.from(context).inflate(R.layout.item_keyboard_layout,this);
        }
        // 为上中下三布局添加键
        for(int i=0;i<getChildCount();i++) {
            ViewGroup child = (ViewGroup) getChildAt(i);
            for (int j = 0; j < map.get(i).length; j++) {
                LayoutInflater.from(context).inflate(R.layout.item_keyboard_bt, child);
            }
        }

        // 为key添加字母及监听
        for(int i=0;i<getChildCount();i++){
            ViewGroup child= (ViewGroup) getChildAt(i);
            String[] keys=map.get(i);
            for(int j=0;j<child.getChildCount();j++){
                Button button= (Button) child.getChildAt(j);
                button.setTag(keys[j]);
                button.setText(keys[j]);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
            }
        }

        // 对删除按钮和GO按钮做特殊处理

        Button goBt= (Button) findViewWithTag("GO");
        LayoutParams params= (LayoutParams) goBt.getLayoutParams();
        params.weight=1.5f;
        goBt.setLayoutParams(params);
        goBt.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                text=text+" ";
                textWatcher.onTextChangedListener(text);
                return true;
            }
        });
        Button deBt= (Button) findViewWithTag("DE");
        LayoutParams params2= (LayoutParams) deBt.getLayoutParams();
        params2.weight=1.5f;
        deBt.setLayoutParams(params2);
        deBt.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setText("");
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(textWatcher==null) return;

        if(view.getTag().equals("DE")){
            if(!TextUtils.isEmpty(text)){
                text=text.substring(0,text.length()-1);
            }
        }else if(view.getTag().equals("GO")){
              textWatcher.onGOClickListener();
            return;
        }else{
            text=text+((Button)view).getText();
        }
        textWatcher.onTextChangedListener(text);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if(textWatcher!=null){
            textWatcher.onTextChangedListener(text);
        }
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }




    public interface TextWatcher{
        void onTextChangedListener(String text);
        void onGOClickListener();
    }

    public void startVibrate(){
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,20};   //100表示先静止200毫秒,然后在震动过20毫秒
        vibrator.vibrate(pattern,-1);
        //vibrator.cancel();
    }

    public void setTextsColor(int color){
        for(int i=0;i<getChildCount();i++){
            ViewGroup child= (ViewGroup) getChildAt(i);
            for(int j=0;j<child.getChildCount();j++){
                Button button= (Button) child.getChildAt(j);
                button.setTextColor(color);
            }
        }
    }

    public void setBtsBackground(int color){
        try {
            for(int i=0;i<getChildCount();i++){
                ViewGroup child= (ViewGroup) getChildAt(i);
                for(int j=0;j<child.getChildCount();j++){
                    Button button= (Button) child.getChildAt(j);
                    button.setBackgroundColor(color);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean onLongClick(View view) {
        String text= (String) ((Button)view).getText();
        VoiceTextManager voiceTextManager=new VoiceTextManager(getContext());
        String appName="";if(text.equals("A")){
            AppUtil.luanchApp(context, MyApplication.appMap.get("WDJ"),view);
        }else if(text.equals("B")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("ABZ"),view);
        }else if(text.equals("C")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("闹钟")),view);
        }else if(text.equals("D")){

        }else if(text.equals("E")){

        }else if(text.equals("F")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("QTFM"),view);
        }else if(text.equals("G")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("相册")),view);
        }else if(text.equals("H")){

        }else if(text.equals("I")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("ITZJ"),view);
        }else if(text.equals("J")){

        }else if(text.equals("K")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("KMK"),view);
        }else if(text.equals("L")){

        }else if(text.equals("M")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("高德地图")),view);
        }else if(text.equals("N")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("IFENG_NEWS"),view);
        }else if(text.equals("O")){

        }else if(text.equals("P")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("支付宝")),view);
        }else if(text.equals("Q")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("QQ"),view);
        }else if(text.equals("R")){

        }else if(text.equals("S")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("设置")),view);
        }else if(text.equals("T")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("SJTB"),view);
        }else if(text.equals("U")){

        }else if(text.equals("V")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("小爱同学")),view);
        }else if(text.equals("W")){
            AppUtil.luanchApp(context,MyApplication.appMap.get(voiceTextManager.transfer("微信")),view);
        }else if(text.equals("X")){

        }else if(text.equals("Y")){

        }else if(text.equals("Z")){

        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
