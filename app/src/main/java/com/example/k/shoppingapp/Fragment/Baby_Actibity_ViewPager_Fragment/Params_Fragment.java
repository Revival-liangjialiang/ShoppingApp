package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.k.shoppingapp.Adapter.Baby_Activity_Params_RecyclerView_Adapter;
import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/8/14.
 */
public class Params_Fragment extends Fragment {
    private RecyclerView re;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.baby_params_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        re = (RecyclerView)getActivity().findViewById(R.id.baby_params_re);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        re.setLayoutManager(linearLayoutManager);
        re.setAdapter(new Baby_Activity_Params_RecyclerView_Adapter());
    }
}
