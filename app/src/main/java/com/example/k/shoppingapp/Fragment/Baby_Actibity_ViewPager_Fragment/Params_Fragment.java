package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/8/14.
 */
public class Params_Fragment extends Fragment {
    LinearLayout l;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.baby_params_fragment_layout, container, false);
        return view;
    }

}
