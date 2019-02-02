package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.biguncler.wp_launcher.util.ScreenUtil;
import com.example.libutil.ColorUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Biguncler on 11/29/16.
 */

public class MetroGridAdapter extends BaseAdapter{
    private Context context;

    public List<AppMode> getList() {
        return list;
    }

    private List<AppMode> list;
    private Map<Integer ,WeakReference<View>> viewMap=new HashMap<>();
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
        // 设置磁贴的宽高间距
        int padding = SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_SPACING);
        padding= PixUtil.dip2px(context, padding);
        //设置磁贴的宽高
        int column =SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_COLUMN);
        int size=(int)((ScreenUtil.getScreenWidth(context)-2*padding)/(float)column);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(size,size);
        layoutParent.setLayoutParams(params);
        layoutParent.setPadding(padding,padding,padding,padding);

        FrameLayout layoutMetro= (FrameLayout) layoutParent.findViewById(R.id.layout_fl_item__adapter_metro);
        // 设置磁贴的颜色
        int color= Integer.valueOf(SharedPreferenceDB.getString(context,SharedPreferenceDB.METRO_COLOR));
        int transparency= 255-(int)(255*SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_TRANSPARENCY)/100f);
        layoutMetro.setBackgroundColor(ColorUtil.setColorAlpha(color,transparency));
        // 设置app名称及图标
        TextView tvName= (TextView) layoutMetro.findViewById(R.id.view_tv_item_adapter_name);
        ImageView ivIcon= (ImageView) layoutMetro.findViewById(R.id.view_iv_item_adapter_icon);
        tvName.setText(list.get(i).getAppName().toUpperCase());
        ivIcon.setImageDrawable(list.get(i).getIcon());
        initListener(layoutParent,i);
        viewMap.put(i,new WeakReference<View>(layoutParent));
        return layoutParent;
    }

    public MetroGridAdapter(Context context, List<AppMode> list) {
        this.context = context;
        this.list = list;
    }

    public MetroGridAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AppMode> list) {
        this.list = list;
    }

    private void initListener(View view,final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pk=list.get(position).getPackageName();
                boolean result= AppUtil.luanchApp(context,pk,view);
                if(!result){
                    Toast.makeText(context,"启动失败",Toast.LENGTH_SHORT).show();;
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    public Map<Integer,WeakReference<View>> getViewMap(){
        return viewMap;
    }

}
