package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/8/5.
 */
public class Fragment_1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_1_layout, container,false );
        return view;
    }
}
