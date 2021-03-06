package com.example.k.shoppingapp.Activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.shoppingapp.Extend.DynamicTagFlowLayout;
import com.example.k.shoppingapp.Other.SystemBarTintManager;
import com.example.k.shoppingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 2016/8/3.
 */
public class Search_Activity extends AppCompatActivity{
    private DynamicTagFlowLayout dynamicTagFlowLayout;
    Button button;
    TextView clear;
    EditText input_edit_text;
    List<String> tags = new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);
        getData();
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.style_gray);// 通知栏颜色
        }
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            //第一个参数控制的是即将进来的活动，
            overridePendingTransition(R.anim.my_in_from_right_1, R.anim.my_in_from_right_2);
        }
        return false;
    }
    private void getData() {
        String str;
        SharedPreferences input = getSharedPreferences("data",MODE_PRIVATE);
        int a = input.getInt("value",0);
        for(int b = 1;b<=a;b++){
            str = input.getString(""+b,"null");
            tags.add(str);
        }
    }
    private void initView() {
        clear = (TextView)findViewById(R.id.clear);
        input_edit_text = (EditText) findViewById(R.id.Edit_Text);
        button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a;
                SharedPreferences.Editor output_editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                SharedPreferences input = getSharedPreferences("data",MODE_PRIVATE);
                String str = input_edit_text.getText().toString();
                if(!str.equals("")){
                    a = input.getInt("value",0);
                    a++;
                    output_editor.putString(""+a,str);
                    output_editor.putInt("value",a);
                    output_editor.commit();
                    getData();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor clear_output_editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                clear_output_editor.clear();
                clear_output_editor.commit();
                for(int g = 0;g<tags.size();g++) {
                    dynamicTagFlowLayout.removeView(dynamicTagFlowLayout.button_attay[g]);
                }
                tags.clear();
            }
        });
        dynamicTagFlowLayout = (DynamicTagFlowLayout) findViewById(R.id.dynamic_tag);
        dynamicTagFlowLayout.setTags(tags);
        dynamicTagFlowLayout.setOnTagItemClickListener(new DynamicTagFlowLayout.OnTagItemClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
            }
        });
    }
}
