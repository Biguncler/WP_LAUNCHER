package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.hp.hpl.sparta.Text;

import java.util.List;

/**
 * Created by Biguncler on 11/29/16.
 */

public class GridAdapter extends BaseAdapter{
    private Context context;
    private int numColumns=3;

    public List<AppMode> getList() {
        return list;
    }

    private List<AppMode> list;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout layoutParent = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_adapter_grid_view,null);
        FrameLayout layoutMetro= (FrameLayout) layoutParent.findViewById(R.id.layout_fl_item__adapter_metro);
        //设置磁贴的宽高
        int size=ScreenUtil.getScreenWidth(context)/numColumns;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(size,size);
        layoutMetro.setLayoutParams(params);
        // 设置磁贴的颜色
        layoutMetro.setBackgroundColor(Integer.valueOf(SharedPreferenceDB.get(context,SharedPreferenceDB.METRO_COLOR)));
        // 设置app名称及图标
        TextView tvName= (TextView) layoutMetro.findViewById(R.id.view_tv_item_adapter_name);
        ImageView ivIcon= (ImageView) layoutMetro.findViewById(R.id.view_iv_item_adapter_icon);
        tvName.setText(list.get(i).getAppName().toUpperCase());
        ivIcon.setImageDrawable(list.get(i).getIcon());


        return layoutParent;
    }

    public GridAdapter(Context context, List<AppMode> list,int numColumns) {
        this.context = context;
        this.list = list;
        this.numColumns=numColumns;
    }

    public GridAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AppMode> list) {
        this.list = list;
    }

}
