package com.example.biguncler.wp_launcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        ImageView ivBbg = (ImageView) view.findViewById(R.id.iv_bbg);
        ImageView ivFbg = (ImageView) view.findViewById(R.id.iv_fbg);
        ContactCellInfo data = datas.get(position);
        ivBbg.setBackgroundResource(data.getBbgId());
        ivFbg.setBackgroundResource(data.getFbgId());

        return view;
    }

    public void updateDate(List<ContactCellInfo> datas) {
        if (datas == null) return;
        this.datas = datas;
        notifyDataSetChanged();
    }
}
