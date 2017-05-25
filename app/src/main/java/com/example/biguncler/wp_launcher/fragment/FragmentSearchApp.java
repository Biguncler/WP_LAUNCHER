package com.example.biguncler.wp_launcher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.biguncler.wp_launcher.R;

/**
 * Created by Biguncler on 06/03/2017.
 */

public class FragmentSearchApp extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.fragment_search_app,container,false);
        return layout;
    }
}
