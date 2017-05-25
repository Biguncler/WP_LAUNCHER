package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.ScreenUtil;

import java.util.List;

/**
 * Created by Biguncler on 11/29/16.
 */

public class GridAdapter2 extends BaseAdapter{
    private Context context;

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
        LinearLayout layoutParent = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_adapter_gride_view2,null);
        // 设置app名称及图标
        TextView tvName= (TextView) layoutParent.findViewById(R.id.view_tv_item_adapter_name);
        ImageView ivIcon= (ImageView) layoutParent.findViewById(R.id.view_iv_item_adapter_icon);
        tvName.setText(list.get(i).getAppName().toUpperCase());
        if(MyApplication.isLightTheme){
            tvName.setTextColor(Color.BLACK);
        }else{
            tvName.setTextColor(Color.WHITE);
        }
        ivIcon.setImageDrawable(list.get(i).getIcon());
        ivIcon.setBackgroundColor(MyApplication.metroColor);


        return layoutParent;
    }

    public GridAdapter2(Context context, List<AppMode> list) {
        this.context = context;
        this.list = list;
    }

    public GridAdapter2(Context context) {
        this.context = context;
    }

    public void setList(List<AppMode> list) {
        this.list = list;
    }

}
