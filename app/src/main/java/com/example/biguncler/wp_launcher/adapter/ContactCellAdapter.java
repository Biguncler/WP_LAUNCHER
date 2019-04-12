package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.mode.ContactCellInfo;

import java.util.ArrayList;
import java.util.List;

public class ContactCellAdapter extends BaseAdapter {
    private Context context;
    private List<ContactCellInfo> datas = new ArrayList<>();

    public ContactCellAdapter(Context context) {
        this.context = context;
    }

    public ContactCellAdapter(Context context, List<ContactCellInfo> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact_cell, parent, false);
        TextView tvBbg = (TextView) view.findViewById(R.id.tv_bbg);
        TextView tvFbg = (TextView) view.findViewById(R.id.tv_fbg);
        Typeface tf= Typeface.createFromAsset(context.getAssets(), "fonts/zhuanti.ttf");
        tvBbg.setTypeface(tf);
        tvFbg.setTypeface(tf);
        ContactCellInfo data = datas.get(position);
        tvBbg.setText(data.getBbgText());
        tvFbg.setText(data.getFbgText());

        return view;
    }

    public void updateDate(List<ContactCellInfo> datas) {
        if (datas == null) return;
        this.datas = datas;
        notifyDataSetChanged();
    }
}
