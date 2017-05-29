package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.AppsGridAdapter;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.util.AppUtil;

/**
 * Created by Biguncler on 05/29/17.
 */

public class AppsLayout extends LinearLayout {
    private Context context;
    private GridView gridView;
    private AppsGridAdapter adapter;

    public AppsLayout(Context context) {
        super(context);
        init(context);
    }

    public AppsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_apps, this);
        gridView = (GridView) layout.findViewById(R.id.view_gv_apps);
        gridView = (GridView) layout.findViewById(R.id.view_gv_apps);
        adapter = new AppsGridAdapter(context, MyApplication.apps);
        gridView.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
    }

    public AppsGridAdapter getAdapter() {
        return adapter;
    }

    public GridView getGridView() {
        return gridView;
    }
}
