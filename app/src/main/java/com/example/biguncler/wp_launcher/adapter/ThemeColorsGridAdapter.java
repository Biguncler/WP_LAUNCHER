package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.activity.BaseActivity;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.example.biguncler.wp_launcher.util.WallpaperUtil;

import java.util.List;

/**
 * Created by Biguncler on 11/29/16.
 */

public class ThemeColorsGridAdapter extends BaseAdapter{
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

    public ThemeColorsGridAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    public ThemeColorsGridAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    private void initListener(View view,final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap= Bitmap.createBitmap(ScreenUtil.getScreenWidth(context),ScreenUtil.getScreenHeight(context), Bitmap.Config.ARGB_8888);
                Canvas canvas=new Canvas(bitmap);
                canvas.drawColor(list.get(position));
                WallpaperUtil.setWallpaper(context,bitmap);
            }
        });
    }


}
