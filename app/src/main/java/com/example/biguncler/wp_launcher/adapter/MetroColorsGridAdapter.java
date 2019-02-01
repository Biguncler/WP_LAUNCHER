package com.example.biguncler.wp_launcher.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.activity.BaseActivity;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;

import java.util.List;

/**
 * Created by Biguncler on 11/29/16.
 */

public class MetroColorsGridAdapter extends BaseAdapter{
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
        initListener(layoutParent,i);
        return layoutParent;
    }

    public MetroColorsGridAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    public MetroColorsGridAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    private void initListener(View view,final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceDB.saveString(context,SharedPreferenceDB.METRO_COLOR,String.valueOf(list.get(position)));
                Intent intent = new Intent(BaseActivity.ACTION_METRO_COLOR_CHANGED);
                context.sendBroadcast(intent);
                ((Activity)context).finish();
            }
        });
    }


}
