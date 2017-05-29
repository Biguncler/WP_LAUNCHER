package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.util.AppUtil;


/**
 * Created by Biguncler on 12/4/16.
 */

public class InputMethodLayout extends LinearLayout  implements View.OnClickListener ,View.OnLongClickListener{
    private Context context;
    private LinearLayout layoutParent;
    private String text="";


    private TextWatcher textWatcher;







    private Button btA,btB,btC,btD,btE,btF,btG,btH,btI,btJ,btK,btL,btM,btN,btO,btP,btQ,btR,btS,btT,btU,btV,btW,btX,btY,btZ,btSPACE,btDELETE;
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
        layoutParent= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.input_method_layout,this);

        btA= (Button) layoutParent.findViewById(R.id.view_bt_center_A);
        btB= (Button) layoutParent.findViewById(R.id.view_bt_bottom_B);
        btC= (Button) layoutParent.findViewById(R.id.view_bt_bottom_C);
        btD= (Button) layoutParent.findViewById(R.id.view_bt_center_D);
        btE= (Button) layoutParent.findViewById(R.id.view_bt_top_E);
        btF= (Button) layoutParent.findViewById(R.id.view_bt_center_F);
        btG= (Button) layoutParent.findViewById(R.id.view_bt_center_G);
        btH= (Button) layoutParent.findViewById(R.id.view_bt_center_H);
        btI= (Button) layoutParent.findViewById(R.id.view_bt_top_I);
        btJ= (Button) layoutParent.findViewById(R.id.view_bt_center_J);
        btK= (Button) layoutParent.findViewById(R.id.view_bt_center_K);
        btL= (Button) layoutParent.findViewById(R.id.view_bt_center_L);
        btM= (Button) layoutParent.findViewById(R.id.view_bt_bottom_M);
        btN= (Button) layoutParent.findViewById(R.id.view_bt_bottom_N);
        btO= (Button) layoutParent.findViewById(R.id.view_bt_top_O);
        btP= (Button) layoutParent.findViewById(R.id.view_bt_top_P);
        btQ= (Button) layoutParent.findViewById(R.id.view_bt_top_Q);
        btR= (Button) layoutParent.findViewById(R.id.view_bt_top_R);
        btS= (Button) layoutParent.findViewById(R.id.view_bt_center_S);
        btT= (Button) layoutParent.findViewById(R.id.view_bt_top_T);
        btU= (Button) layoutParent.findViewById(R.id.view_bt_top_U);
        btV= (Button) layoutParent.findViewById(R.id.view_bt_bottom_V);
        btW= (Button) layoutParent.findViewById(R.id.view_bt_top_W);
        btX= (Button) layoutParent.findViewById(R.id.view_bt_bottom_X);
        btY= (Button) layoutParent.findViewById(R.id.view_bt_top_Y);
        btZ= (Button) layoutParent.findViewById(R.id.view_bt_bottom_Z);
        btSPACE= (Button) layoutParent.findViewById(R.id.view_bt_bottom_SPACE);
        btDELETE= (Button) layoutParent.findViewById(R.id.view_bt_bottom_DELETE);


        btA.setOnClickListener(this);
        btB.setOnClickListener(this);
        btC.setOnClickListener(this);
        btD.setOnClickListener(this);
        btE.setOnClickListener(this);
        btF.setOnClickListener(this);
        btG.setOnClickListener(this);
        btH.setOnClickListener(this);
        btI.setOnClickListener(this);
        btJ.setOnClickListener(this);
        btK.setOnClickListener(this);
        btL.setOnClickListener(this);
        btM.setOnClickListener(this);
        btN.setOnClickListener(this);
        btO.setOnClickListener(this);
        btP.setOnClickListener(this);
        btQ.setOnClickListener(this);
        btR.setOnClickListener(this);
        btS.setOnClickListener(this);
        btT.setOnClickListener(this);
        btU.setOnClickListener(this);
        btV.setOnClickListener(this);
        btW.setOnClickListener(this);
        btX.setOnClickListener(this);
        btY.setOnClickListener(this);
        btZ.setOnClickListener(this);
        btSPACE.setOnClickListener(this);
        btDELETE.setOnClickListener(this);

        btA.setOnLongClickListener(this);
        btB.setOnLongClickListener(this);
        btC.setOnLongClickListener(this);
        btD.setOnLongClickListener(this);
        btE.setOnLongClickListener(this);
        btF.setOnLongClickListener(this);
        btG.setOnLongClickListener(this);
        btH.setOnLongClickListener(this);
        btI.setOnLongClickListener(this);
        btJ.setOnLongClickListener(this);
        btK.setOnLongClickListener(this);
        btL.setOnLongClickListener(this);
        btM.setOnLongClickListener(this);
        btN.setOnLongClickListener(this);
        btO.setOnLongClickListener(this);
        btP.setOnLongClickListener(this);
        btQ.setOnLongClickListener(this);
        btR.setOnLongClickListener(this);
        btS.setOnLongClickListener(this);
        btT.setOnLongClickListener(this);
        btU.setOnLongClickListener(this);
        btV.setOnLongClickListener(this);
        btW.setOnLongClickListener(this);
        btX.setOnLongClickListener(this);
        btY.setOnLongClickListener(this);
        btZ.setOnLongClickListener(this);

        btDELETE.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setText("");
                return true;
            }
        });

        btSPACE.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                text=text+" ";
                textWatcher.onTextChangedListener(text);
                startVibrate();
                return true;
            }
        });



    }

    @Override
    public void onClick(View view) {
        if(textWatcher==null) return;

        if(view.getId()==R.id.view_bt_bottom_DELETE){
            if(!TextUtils.isEmpty(text)){
                text=text.substring(0,text.length()-1);
            }
        }else if(view.getId()==R.id.view_bt_bottom_SPACE){
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
        btA.setTextColor(color);
        btB.setTextColor(color);
        btC.setTextColor(color);
        btD.setTextColor(color);
        btE.setTextColor(color);
        btF.setTextColor(color);
        btG.setTextColor(color);
        btH.setTextColor(color);
        btI.setTextColor(color);
        btJ.setTextColor(color);
        btK.setTextColor(color);
        btL.setTextColor(color);
        btM.setTextColor(color);
        btN.setTextColor(color);
        btO.setTextColor(color);
        btP.setTextColor(color);
        btQ.setTextColor(color);
        btR.setTextColor(color);
        btS.setTextColor(color);
        btT.setTextColor(color);
        btU.setTextColor(color);
        btV.setTextColor(color);
        btW.setTextColor(color);
        btX.setTextColor(color);
        btY.setTextColor(color);
        btZ.setTextColor(color);
        btSPACE.setTextColor(color);
        btDELETE.setTextColor(color);
    }

    public void setBtsBackground(int idres){
        btA.setBackgroundResource(idres);
        btB.setBackgroundResource(idres);
        btC.setBackgroundResource(idres);
        btD.setBackgroundResource(idres);
        btE.setBackgroundResource(idres);
        btF.setBackgroundResource(idres);
        btG.setBackgroundResource(idres);
        btH.setBackgroundResource(idres);
        btI.setBackgroundResource(idres);
        btJ.setBackgroundResource(idres);
        btK.setBackgroundResource(idres);
        btL.setBackgroundResource(idres);
        btM.setBackgroundResource(idres);
        btN.setBackgroundResource(idres);
        btO.setBackgroundResource(idres);
        btP.setBackgroundResource(idres);
        btQ.setBackgroundResource(idres);
        btR.setBackgroundResource(idres);
        btS.setBackgroundResource(idres);
        btT.setBackgroundResource(idres);
        btU.setBackgroundResource(idres);
        btV.setBackgroundResource(idres);
        btW.setBackgroundResource(idres);
        btX.setBackgroundResource(idres);
        btY.setBackgroundResource(idres);
        btZ.setBackgroundResource(idres);
        btSPACE.setBackgroundResource(idres);
        btDELETE.setBackgroundResource(idres);
    }



    @Override
    public boolean onLongClick(View view) {
        String text= (String) ((Button)view).getText();
        String appName="";if(text.equals("A")){
            AppUtil.luanchApp(context, MyApplication.appMap.get("WDJ"),view);
        }else if(text.equals("B")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("ABZ"),view);
        }else if(text.equals("C")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("CLOCK"),view);
        }else if(text.equals("D")){

        }else if(text.equals("E")){

        }else if(text.equals("F")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("QTFM"),view);
        }else if(text.equals("G")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("GALLERY"),view);
        }else if(text.equals("H")){

        }else if(text.equals("I")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("ITZJ"),view);
        }else if(text.equals("J")){

        }else if(text.equals("K")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("KMK"),view);
        }else if(text.equals("L")){

        }else if(text.equals("M")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("AMAP"),view);
        }else if(text.equals("N")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("IFENG_NEWS"),view);
        }else if(text.equals("O")){

        }else if(text.equals("P")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("ALIPAY"),view);
        }else if(text.equals("Q")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("QQ"),view);
        }else if(text.equals("R")){

        }else if(text.equals("S")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("SETTINGS"),view);
        }else if(text.equals("T")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("SJTB"),view);
        }else if(text.equals("U")){

        }else if(text.equals("V")){

        }else if(text.equals("W")){
            AppUtil.luanchApp(context,MyApplication.appMap.get("WECHAT"),view);
        }else if(text.equals("X")){

        }else if(text.equals("Y")){

        }else if(text.equals("Z")){

        }
        return true;
    }


}
