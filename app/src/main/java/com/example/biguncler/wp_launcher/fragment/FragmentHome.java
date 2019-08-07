package com.example.biguncler.wp_launcher.fragment;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.CellLayoutAdapter;
import com.example.biguncler.wp_launcher.adapter.MetroGridAdapter;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.biz.VoiceTextManager;
import com.example.biguncler.wp_launcher.db.SharedPreferenceDB;
import com.example.biguncler.wp_launcher.mode.AppMode;
import com.example.biguncler.wp_launcher.mode.BatteryCellInfo;
import com.example.biguncler.wp_launcher.mode.CellInfo;
import com.example.biguncler.wp_launcher.mode.ContactCellInfo;
import com.example.biguncler.wp_launcher.mode.GalleryCellInfo;
import com.example.biguncler.wp_launcher.mode.IconCellInfo;
import com.example.biguncler.wp_launcher.util.AppUtil;
import com.example.biguncler.wp_launcher.util.PixUtil;
import com.example.biguncler.wp_launcher.util.StatusBarUtil;
import com.example.biguncler.wp_launcher.view.CellLayout;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
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
    public static final String ACTION_UPDATE_TILE_SPACIING="action_update_tile_spacing";
    public static final String ACTION_UPDATE_TILE_COLUMN="action_update_tile_column";

    /*private GridView gridView;
    private MetroGridAdapter gridAdapter;*/

    private CellLayout cellLayout;
    private CellLayoutAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_TILE_SPACIING);
        intentFilter.addAction(ACTION_UPDATE_TILE_COLUMN);
        getActivity().registerReceiver(receiver,intentFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.fragment_home,container,false);
        cellLayout = (CellLayout) layout.findViewById(R.id.view_cell_layout);
        // 设置布局和状态栏的关系
        cellLayout.setPadding(cellLayout.getPaddingLeft(),cellLayout.getPaddingTop()+ StatusBarUtil.getStatusBarHeight(getActivity()),cellLayout.getPaddingRight(),cellLayout.getPaddingBottom());
        adapter=new CellLayoutAdapter(getActivity(),getData());
        cellLayout.setAdapter(adapter);
        int padding =SharedPreferenceDB.getInt(getActivity(),SharedPreferenceDB.TILE_SPACING);
        padding= PixUtil.dip2px(getActivity(),padding);
        cellLayout.setSpace(padding);

        initListener();
        return layout;
    }

    private List<CellInfo> getData(){
        List<CellInfo> data=new ArrayList<>();
        List<AppMode> appModes=new AppManager().getHomeApps(getActivity());
        VoiceTextManager voiceTextManager=new VoiceTextManager(getContext());
        for(AppMode appMode:appModes){
            String gallery=MyApplication.appMap.get(voiceTextManager.transfer("相册"));
            if(!TextUtils.isEmpty(gallery) &&TextUtils.equals(gallery,appMode.getPackageName())){
                GalleryCellInfo galleryCellInfo=new GalleryCellInfo();
                galleryCellInfo.setType(CellLayoutAdapter.TYPE_GALLERY);
                galleryCellInfo.setMode(appMode);
                data.add(galleryCellInfo);
                continue;
            }

            String contact=MyApplication.appMap.get(voiceTextManager.transfer("PHONE"));
            if(!TextUtils.isEmpty(contact) &&TextUtils.equals(contact,appMode.getPackageName())){
                ContactCellInfo contactCellInfo=new ContactCellInfo();
                contactCellInfo.setType(CellLayoutAdapter.TYPE_CONTACT);
                contactCellInfo.setMode(appMode);
                data.add(contactCellInfo);
                continue;
            }

            String calender=MyApplication.appMap.get(voiceTextManager.transfer("日历"));
            if(!TextUtils.isEmpty(calender) &&TextUtils.equals(calender,appMode.getPackageName())){
                CellInfo dateCell=new CellInfo();
                dateCell.setType(CellLayoutAdapter.TYPE_DATE);
                dateCell.setMode(appMode);
                data.add(dateCell);
                continue;
            }

           IconCellInfo iconCellInfo= new IconCellInfo();
            iconCellInfo.setType(CellLayoutAdapter.TYPE_ICON);
            iconCellInfo.setMode(appMode);
            data.add(iconCellInfo);
        }
        BatteryCellInfo batteryCellInfo=new BatteryCellInfo();
        batteryCellInfo.setType(CellLayoutAdapter.TYPE_BATTERY);
        data.add(1,batteryCellInfo);

        return data;
    }

    private void initListener() {
        cellLayout.setOnItemClickListener(new CellLayout.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position, long id) {
                try{
                    CellInfo cellInfo= (CellInfo) adapter.getItem(position);
                    String pn=cellInfo.getMode().getPackageName();
                    boolean result= AppUtil.luanchApp(getActivity(),pn,view);
                    if(!result){
                        Toast.makeText(getActivity(),"启动失败",Toast.LENGTH_SHORT).show();;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onTileTransparency(Intent intent) {
        super.onTileTransparency(intent);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        adapter.notifyDataSetChanged();
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ACTION_UPDATE_TILE_SPACIING.equals(action)){
                int padding =SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_SPACING);
                padding= PixUtil.dip2px(context,padding);
                cellLayout.setSpace(padding);
                adapter.notifyDataSetChanged();
            }else if(ACTION_UPDATE_TILE_COLUMN.equals(action)){
                int column =SharedPreferenceDB.getInt(context,SharedPreferenceDB.TILE_COLUMN);
                cellLayout.setColumCount(column);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
