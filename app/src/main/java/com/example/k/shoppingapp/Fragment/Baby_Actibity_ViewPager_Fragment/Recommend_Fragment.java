package com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.k.shoppingapp.Activity.Baby_Activity;
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
        re = (RecyclerView) getActivity().findViewById(R.id.baby_Recommend_re);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        re.setLayoutManager(gridLayoutManager);
        re.setHasFixedSize(false);
        Baby_Activity_RecommendAdapter b = new Baby_Activity_RecommendAdapter();
        re.setAdapter(b);
        b.setRecyclerViewItemClickListener(new Baby_Activity_RecommendAdapter.RecyclerViewItemClickListener() {
            @Override
            public void ItemClick(View v, int position) {
                switch (position) {
                    case 0:
                        Intent intent_0 = new Intent(getContext(), Baby_Activity.class);
                        intent_0.putExtra("value", 0);
                        intent_0.putExtra("value_2", 0);
                        intent_0.putExtra("value_3", 0);
                        startActivity(intent_0);
                        break;
                    case 1:
                        Intent intent_1 = new Intent(getContext(),Baby_Activity.class);
                        intent_1.putExtra("value",1);
                        intent_1.putExtra("value_2",1);
                        intent_1.putExtra("value_3",1);
                        startActivity(intent_1);
                        break;
                    case 2:
                        Intent intent_2 = new Intent(getContext(),Baby_Activity.class);
                        intent_2.putExtra("value",2);
                        intent_2.putExtra("value_2",2);
                        intent_2.putExtra("value_3",2);
                        startActivity(intent_2);
                        break;
                    case 3:
                        Intent intent_3 = new Intent(getContext(),Baby_Activity.class);
                        intent_3.putExtra("value",3);
                        intent_3.putExtra("value_2",3);
                        intent_3.putExtra("value_3",3);
                        startActivity(intent_3);
                        break;
                    case 4:
                        Intent intent_4 = new Intent(getContext(),Baby_Activity.class);
                        intent_4.putExtra("value",4);
                        intent_4.putExtra("value_2",4);
                        intent_4.putExtra("value_3",4);
                        startActivity(intent_4);
                        break;
                    case 5:
                        Intent intent_5 = new Intent(getContext(),Baby_Activity.class);
                        intent_5.putExtra("value",5);
                        intent_5.putExtra("value_2",5);
                        intent_5.putExtra("value_3",5);
                        startActivity(intent_5);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
