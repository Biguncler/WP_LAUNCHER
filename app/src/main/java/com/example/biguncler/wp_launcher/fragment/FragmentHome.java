package com.example.biguncler.wp_launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.GridAdapter;
import com.example.biguncler.wp_launcher.application.MyApplication;
import com.example.biguncler.wp_launcher.biz.AppManager;
import com.example.biguncler.wp_launcher.util.AppUtil;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentHome extends BaseFragment {
    private GridView gridView;
    private GridAdapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.fragment_home,container,false);
        gridView= (GridView) layout.findViewById(R.id.view_gv_home);
        gridAdapter=new GridAdapter(getActivity(), new AppManager().getHomeApps(getActivity()),3);
        gridView.setAdapter(gridAdapter);

        initListener();
        return layout;
    }

    private void initListener() {
        // 单击启动对应activity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pk=gridAdapter.getList().get(i).getPackageName();
                boolean result= AppUtil.luanchApp(getActivity(),pk,view);
                if(!result){
                    Toast.makeText(getActivity(),"启动失败",Toast.LENGTH_SHORT).show();;
                }
            }
        });
       /* // 长按删除对应activity
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pk=gridAdapter.getList().get(i).getPackageName();
                boolean result=AppUtil.uninstallApp(getActivity(),pk);
                return true;
            }
        });*/

    }


    @Override
    public void onMetroColorChanged(Intent intent) {
        super.onMetroColorChanged(intent);
        gridAdapter.notifyDataSetChanged();
    }
}
