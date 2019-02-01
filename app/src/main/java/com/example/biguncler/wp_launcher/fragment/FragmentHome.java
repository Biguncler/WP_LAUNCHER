package com.example.biguncler.wp_launcher.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.MetroGridAdapter;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.util.AppUtil;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentHome extends BaseFragment {
    public static final String ACTION_UPDATE_TILE_TRANSPARENCY="action_update_tile_transparency";
    private GridView gridView;
    private MetroGridAdapter gridAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_TILE_TRANSPARENCY);
        getActivity().registerReceiver(receiver,intentFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.fragment_home,container,false);
        gridView= (GridView) layout.findViewById(R.id.view_gv_home);
        gridAdapter=new MetroGridAdapter(getActivity(), new AppManager().getHomeApps(getActivity()),3);
        gridView.setAdapter(gridAdapter);

        initListener();
        return layout;
    }

    private void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        gridAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ACTION_UPDATE_TILE_TRANSPARENCY.equals(action)){
                gridAdapter.notifyDataSetChanged();
            }
        }
    };
}
