package com.example.biguncler.wp_launcher.fragment;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.TimeUnit;
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
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentHome extends BaseFragment {
    public static final String ACTION_UPDATE_TILE_TRANSPARENCY="action_update_tile_transparency";
    public static final String ACTION_UPDATE_TILE_SPACIING="action_update_tile_spacing";
    public static final String ACTION_UPDATE_TILE_COLUMN="action_update_tile_column";

    private GridView gridView;
    private MetroGridAdapter gridAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_TILE_TRANSPARENCY);
        intentFilter.addAction(ACTION_UPDATE_TILE_SPACIING);
        intentFilter.addAction(ACTION_UPDATE_TILE_COLUMN);
        getActivity().registerReceiver(receiver,intentFilter);
        Observable.interval(3,15, java.util.concurrent.TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if(gridAdapter!=null){
                    Map<Integer,WeakReference<View>> map= gridAdapter.getViewMap();
                    if(!map.isEmpty()){
                        int postion=(int)(Math.random()*(map.size()));
                        View view=map.get(postion).get();
                        if(view!=null){
                            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationX", 0, 360);
                            animator.setDuration(2000).start();
                        }
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.fragment_home,container,false);
        gridView= (GridView) layout.findViewById(R.id.view_gv_home);
        int padding =SharedPreferenceDB.getInt(getActivity(),SharedPreferenceDB.TILE_SPACING);
        padding= PixUtil.dip2px(getActivity(),padding);
        gridView.setPadding(padding,0,padding,0);
        int column =SharedPreferenceDB.getInt(getActivity(),SharedPreferenceDB.TILE_COLUMN);
        if(column==0) column=3;
        SharedPreferenceDB.saveInt(getActivity(),SharedPreferenceDB.TILE_COLUMN,column);
       // gridView.setNumColumns(column);
        gridAdapter=new MetroGridAdapter(getActivity(), new AppManager().getHomeApps(getActivity()));
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
            }else if(ACTION_UPDATE_TILE_SPACIING.equals(action)){
                int padding =SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_SPACING);
                padding= PixUtil.dip2px(context,padding);
                gridView.setPadding(padding,0,padding,0);
                gridAdapter.notifyDataSetChanged();

            }else if(ACTION_UPDATE_TILE_COLUMN.equals(action)){
                int column =SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_COLUMN);
                gridView.setNumColumns(column);
                gridAdapter.notifyDataSetChanged();
            }
        }
    };
}
