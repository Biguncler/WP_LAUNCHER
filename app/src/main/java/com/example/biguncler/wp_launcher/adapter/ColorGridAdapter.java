package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.content.Intent;
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

public class ColorGridAdapter extends BaseAdapter{
    private Context context;


    public List<Integer> getList() {
        return list;
    }

    private List<Integer> list;
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
        LinearLayout layoutParent = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_adapter_colors,null);
        ImageView imageView= (ImageView) layoutParent.findViewById(R.id.view_iv_color);
        imageView.setBackgroundColor(list.get(i));
        return layoutParent;
    }

    public ColorGridAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    public ColorGridAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

}
