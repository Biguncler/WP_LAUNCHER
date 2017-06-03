package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.MetroColorsGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biguncler on 05/28/17.
 */

public class MetroColorsLayout extends LinearLayout {
    private Context context;
    private GridView gridView;
    private MetroColorsGridAdapter adapter;
    public MetroColorsLayout(Context context) {
        super(context);
        init(context);
    }

    public MetroColorsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MetroColorsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        LinearLayout layout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_metro_colors,this);
        gridView= (GridView) layout.findViewById(R.id.view_gv_colors);
        int[] colors=getResources().getIntArray(R.array.metro_colors);
        adapter=new MetroColorsGridAdapter(context,arrayToList(colors));
        gridView.setAdapter(adapter);
        initListener();
    }

    private void initListener(){
    }

    private List<Integer> arrayToList(int[] intArray){
        List<Integer> list=new ArrayList();
        try {
            for(Integer integer:intArray){
                list.add(integer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
