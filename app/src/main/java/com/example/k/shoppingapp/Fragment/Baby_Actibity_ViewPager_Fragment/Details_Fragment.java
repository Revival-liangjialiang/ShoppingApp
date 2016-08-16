package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.k.shoppingapp.Activity.Baby_Activity;
import com.example.k.shoppingapp.Adapter.BabyActivity_Details_RecyclerViewAdapter;
import com.example.k.shoppingapp.Extend.Baby_Activity_Extend.FullyLinearLayoutManager;
import com.example.k.shoppingapp.Other.pic_path;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.BabyActivityUtil.RequestListener;
import com.example.k.shoppingapp.Util.BabyActivityUtil.Volley_DetailsPageRequest;
import com.example.k.shoppingapp.Util.BabyActivityUtil.Volley_Request;

/**
 * Created by k on 2016/8/14.
 */
public class Details_Fragment extends Fragment {
    Baby_Activity b;
    int value = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.baby_details_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动宝贝活动的图片请求
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = (Baby_Activity) getActivity();
        initView();
        startRequest();
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(b);
        recyclerView.setNestedScrollingEnabled(false);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.baby_recyclerView);

    }

    public RecyclerView recyclerView;
    private void startRequest() {
        new Volley_Request(pic_path.baby_hrad_pic_address[b.head_pic_path_value], b).StartImageRequest();
        Volley_DetailsPageRequest v = new Volley_DetailsPageRequest(b, pic_path.details_page_pic_address[b.details_pic_address_value]);
        v.setRequestListener(new RequestListener() {
            @Override
            public void getImage(Bitmap bitmap) {
                Log.i("ok","---------------------------value = "+value);
                b.cache.put(pic_path.details_page_pic_address[b.details_pic_address_value][value], bitmap);
                value++;
                if (value == pic_path.details_page_pic_address[b.details_pic_address_value].length) {
                    recyclerView.setAdapter(new BabyActivity_Details_RecyclerViewAdapter(b, pic_path.details_page_pic_address[b.details_pic_address_value]));
                value=0;
                }
            }
        });
        v.startRequest();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ok","----------------onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("ok","----------------onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("ok","----------------onDestroyView()");
    }
}
