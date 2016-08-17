package com.example.k.shoppingapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.k.shoppingapp.R;

import org.w3c.dom.Text;

/**
 * Created by k on 2016/8/16.
 */
public class Baby_Activity_Params_RecyclerView_Adapter extends RecyclerView.Adapter<Baby_Activity_Params_RecyclerView_Adapter.MyViewHolder> {
    String[] str1 = {"流行元素", "袖长", "服装版型", "衣长", "领型", "袖型", "品牌", "成分含量", "面料", "图案文化", "适用年龄", "风格", "通勤", "年份季节", "尺码"};
    String[] str2 = {"口袋", "长袖", "宽松", "常规型", "圆领", "常规", "桃花小妹", "30%及以下", "棉", "条纹", "18-24周岁", "通勤", "韩版", "2016年秋季", "M,L,XL"};

    @Override
    public Baby_Activity_Params_RecyclerView_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_params_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(str1[position]);
        holder.textView2.setText(str2[position]);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.baby_params_text_1);
            textView2 = (TextView) itemView.findViewById(R.id.baby_params_text_2);
        }
    }
}
