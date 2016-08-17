package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.k.shoppingapp.Adapter.Baby_Activity_RecommendAdapter;
import com.example.k.shoppingapp.Extend.Baby_Activity_Extend.DividerGridItemDecoration;
import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/8/14.
 */
public class Recommend_Fragment extends Fragment {
    RecyclerView re;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.baby_recommend_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        re = (RecyclerView)getActivity().findViewById(R.id.baby_Recommend_re);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        re.setLayoutManager(gridLayoutManager);
        re.setHasFixedSize(false);
        Baby_Activity_RecommendAdapter b = new Baby_Activity_RecommendAdapter();
        re.setAdapter(b);
        b.setRecyclerViewItemClickListener(new Baby_Activity_RecommendAdapter.RecyclerViewItemClickListener() {
            @Override
            public void ItemClick(View v, int position) {
                Toast.makeText(getContext(), "position = "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
