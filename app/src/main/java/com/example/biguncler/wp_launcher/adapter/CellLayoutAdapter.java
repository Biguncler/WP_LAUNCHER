package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mode.CellInfo;
import com.example.biguncler.wp_launcher.mode.IconCellInfo;
import com.example.libutil.ColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biguncler on 3/20/2019.
 */

public class CellLayoutAdapter extends BaseAdapter {
    public final static int TYPE_ICON = 0;
    public final static int TYPE_DATE = TYPE_ICON + 1;
    public final static int TYPE_GALLERY = TYPE_DATE + 1;
    public final static int TYPE_BATTERY = TYPE_GALLERY + 1;
    public final static int TYPE_COUNT = TYPE_BATTERY+1;

    private Context context;
    private List<CellInfo> data =new ArrayList<>();

    public CellLayoutAdapter(Context context, List<CellInfo> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 设置磁贴的颜色
        int color= Integer.valueOf(SharedPreferenceDB.getString(context,SharedPreferenceDB.METRO_COLOR));
        int transparency= 255-(int)(255*SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_TRANSPARENCY)/100f);
        color=ColorUtil.setColorAlpha(color,transparency);
        int type = getItemViewType(i);
        if(view==null) {
            switch (type) {
                case TYPE_DATE:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_item_date, viewGroup,false);
                    break;
                case TYPE_ICON:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_item_icon, viewGroup,false);
                    break;
                case TYPE_GALLERY:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_item_gallery, viewGroup,false);
                    break;
                case TYPE_BATTERY:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_item_battery, viewGroup,false);
                    break;
            }
        }
        view.setBackgroundColor(color);
        switch (type){
            case TYPE_DATE:
                break;
            case TYPE_ICON:
                IconCellInfo iconCellInfo= (IconCellInfo) data.get(i);
                ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
                TextView tvName = (TextView) view.findViewById(R.id.tv_name);
                ivIcon.setImageDrawable(iconCellInfo.getMode().getIcon());
                tvName.setText(iconCellInfo.getMode().getAppName().toUpperCase());
                break;
            case TYPE_GALLERY:
                break;
        }
        return view;
    }

    public void update(List<CellInfo> data) {
        this.data=data;
        notifyDataSetChanged();
    }
}
