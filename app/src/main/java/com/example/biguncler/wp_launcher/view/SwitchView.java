package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.libtheme.ThemeHelper;

/**
 * Created by Biguncler on 2/1/2019.
 * 开关宽50dp 高 22dp  ，开关滑块宽12dp 高22dp ，线宽度2dp
 */

public class SwitchView extends LinearLayout implements View.OnClickListener {
    private Paint paint;
    private int lineColor =Color.GRAY;
    private int metroColor = Color.YELLOW;
    private boolean open;
    private Context context;
    private OnSwitchListener listner;

    public SwitchView(Context context) {
        super(context);
        init();
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.context = getContext();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLineColor(ThemeHelper.getTextColor(context));
        setMetroColor(Integer.valueOf(SharedPreferenceDB.getString(context, SharedPreferenceDB.METRO_COLOR)));
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (open) {
            paint.setColor(metroColor);
            Rect rect1 = new Rect(0, PixUtil.dip2px(context, 1), PixUtil.dip2px(context, 50), PixUtil.dip2px(context, 21));
            canvas.drawRect(rect1, paint);

            paint.setColor(lineColor);
            int swidth = PixUtil.dip2px(context, 2);
            paint.setStrokeWidth(swidth);
            int margin = swidth / 2;
            paint.setStyle(Paint.Style.STROKE);
            Rect rect2 = new Rect(rect1.left + margin, rect1.top + margin, rect1.right - margin, rect1.bottom - margin);
            canvas.drawRect(rect2, paint);

            paint.reset();
            paint.setColor(lineColor);
            Rect rect3 = new Rect(PixUtil.dip2px(context, 38), 0, PixUtil.dip2px(context, 50), PixUtil.dip2px(context, 22));
            canvas.drawRect(rect3, paint);
        } else {
            paint.setColor(reverseColor(lineColor));
            Rect rect1 = new Rect(0, PixUtil.dip2px(context, 1), PixUtil.dip2px(context, 50), PixUtil.dip2px(context, 21));
            canvas.drawRect(rect1, paint);

            paint.setColor(lineColor);
            int swidth = PixUtil.dip2px(context, 2);
            paint.setStrokeWidth(swidth);
            int margin = swidth / 2;
            paint.setStyle(Paint.Style.STROKE);
            Rect rect2 = new Rect(rect1.left + margin, rect1.top + margin, rect1.right - margin, rect1.bottom - margin);
            canvas.drawRect(rect2, paint);

            paint.reset();
            paint.setColor(lineColor);
            Rect rect3 = new Rect(0, 0, PixUtil.dip2px(context, 12), PixUtil.dip2px(context, 22));
            canvas.drawRect(rect3, paint);
        }
    }


    private int reverseColor(int color) {
        if (Color.BLACK == color) {
            return Color.WHITE;
        } else if (Color.WHITE == color) {
            return Color.BLACK;
        } else {
            return color;
        }
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getMetroColor() {
        return metroColor;
    }

    public void setMetroColor(int metroColor) {
        this.metroColor = metroColor;
    }

    public void setOnSwitchListener(OnSwitchListener listener){
        this.listner = listener;
    }

    public void setSwitchState(boolean state){
        open=state;
        invalidate();
    }

    @Override
    public void onClick(View view) {
        open=!open;
        if(listner!=null){
            listner.onSwitch(view,open);
        }
        invalidate();
    }

    public static interface OnSwitchListener {
        void onSwitch(View view,boolean isOpen);
    }

    public void updateSwitchTheme(){
        setLineColor(ThemeHelper.getTextColor(context));
        setMetroColor(Integer.valueOf(SharedPreferenceDB.getString(context, SharedPreferenceDB.METRO_COLOR)));
        invalidate();
    }
}
