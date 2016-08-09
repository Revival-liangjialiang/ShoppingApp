package com.example.k.shoppingapp.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.shoppingapp.Activity.MainActivity;
import com.example.k.shoppingapp.Adapter.MyPullListener;
import com.example.k.shoppingapp.Other.MyView;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.Register_Response;
import com.example.k.shoppingapp.Util.Sign_in_Response;
import com.flyco.tablayout.CommonTabLayout;
import com.google.gson.Gson;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by k on 2016/7/19.
 */
@SuppressLint("ValidFragment")
public class my_account_fragment extends Fragment implements View.OnClickListener {
    //处理头像的字段
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PH = 3;
    public static final int ALBUM_CUT = 4;
    Uri imageUri;
    Bitmap bitMap;
    Uri album_pic_uri;
    Uri cut_pic_uri;
    Uri loop_uri;

    //换头像字段
    OutputStream pic_out;
    int pic_data_len;
    BufferedWriter writer;
    FileInputStream pic_input;
    Button start_camera, open_album;
    CommonTabLayout commonTabLayout;
    //登录界面控件声明处
    TextView sign_in_id_TextView;
    boolean sign_in_boolean = true;
    ImageView back_image;
    ImageButton pic_modify_button;
   public RelativeLayout modify_layout;
    Button modify_buuton;
    RelativeLayout sign_success_main_layout;
    public MyView myView;
    public ScrollView scrollView,my_scrollView;

    //注册界面导航字段
    boolean register_boolean = true;

    //下拉控件导航字段
    PullToRefreshLayout pullToRefreshLayout;

    final int NETWORK = 5;
    final int REGISTER_SUCCESS = 0;
    final int SIGN_IN_SUCCESS = 1;
    final int REGISTER_FAIL = 2;
    final int SIGN_IN_PASSWORD_ERROR = 3;
    final int NO_USER = 4;
    CheckBox checkBox;
    boolean checkBoxBooleanValue = false;
    HttpURLConnection connection;
    Gson gson = new Gson();
    HttpURLConnection sign_in_connection;
    URL url;
    URL sign_in_url;
    String register_user_name;
    String register_user_pw;
    FrameLayout f;
    Button select_pic_source_cancel_button;
    ImageView back_IV;
    EditText sign_in_UserNameET, sign_in_userPasswordET, register_UserNameET, register_UserPasswordET, register_UserPassword_again_inputET;
    TextView registerTV;
    TextView user_name_TextView;
    public MainActivity m;
    public LinearLayout l;
    LinearLayout sign_in_success_layout;
    public RelativeLayout selerct_pic_source_linLayout;
    Button sign_in_button, register_button;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    LinearLayout l = (LinearLayout) getActivity().findViewById(R.id.linearLayout);
                    l.setVisibility(View.GONE);
                    my_scrollView.setVisibility(View.GONE);
                    sign_in_UserNameET.setText((String) msg.obj);
                    Toast.makeText(getContext(), "注册成功账号为：" + (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case SIGN_IN_SUCCESS:
                    sign_in_id_TextView.setText(sign_in_UserNameET.getText().toString());
                    Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                    Log.i("ok", "启动线程");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("ok", "线程已启动");
                            try {
                                URL sign_in_url_2 = new URL("http://120.27.103.151:8088/PicServiceDemo/MyServlet?user_id_=" + sign_in_UserNameET.getText().toString());
                                HttpURLConnection sign_in_connection_2 = (HttpURLConnection) sign_in_url_2.openConnection();
                                sign_in_connection_2.setRequestMethod("GET");
                                sign_in_connection_2.setReadTimeout(8000);
                                sign_in_connection_2.setConnectTimeout(8000);
                                sign_in_connection_2.setDoInput(true);
                                sign_in_connection_2.setRequestProperty("charset", "UTF-8");
                                if (sign_in_connection_2.getResponseCode() == 200) {
                                    Log.i("ok", "响应成功");
                                    Bitmap bitmap_2 = BitmapFactory.decodeStream(sign_in_connection_2.getInputStream());
                                        Message message = new Message();
                                        message.what = 10;
                                        message.obj = bitmap_2;
                                        handler.sendMessage(message);
                                }
                            } catch (MalformedURLException e) {
                                Log.i("ok", "丫丫错误1");
                                e.printStackTrace();
                            } catch (IOException e) {
                                Log.i("ok", "丫丫错误2");
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    LinearLayout sign_in_successs_linearLayout = (LinearLayout) getActivity().findViewById(R.id.sign_in_success);
                    LinearLayout sign_in_layout = (LinearLayout) getActivity().findViewById(R.id.sign_in_layout);
                    Sign_in_Response back_data = (Sign_in_Response) msg.obj;
                    user_name_TextView.setText(back_data.user_name);
                    if (checkBoxBooleanValue) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putString("user_name", sign_in_UserNameET.getText().toString());
                        editor.putString("user_password", sign_in_userPasswordET.getText().toString());
                        editor.commit();
                    }
                    scrollView.setVisibility(View.GONE);
                    sign_success_main_layout.setVisibility(View.VISIBLE);
                    sign_in_successs_linearLayout.setVisibility(View.VISIBLE);
                    break;
                case REGISTER_FAIL:
                    Toast.makeText(getContext(), "Register fail!", Toast.LENGTH_SHORT).show();
                    break;
                case SIGN_IN_PASSWORD_ERROR:
                    Toast.makeText(getContext(), "密码错误！", Toast.LENGTH_SHORT).show();
                    break;
                case NO_USER:
                    Toast.makeText(getContext(), "用户不存在！", Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK:
                    Toast.makeText(getContext(), "NetWork Abnormal!", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    sign_success_main_layout.setVisibility(View.VISIBLE);
                    commonTabLayout.setVisibility(View.VISIBLE);
                    break;
                case 10:
                    if(msg.obj!=null) {
                        myView.bitmap = (Bitmap) msg.obj;
                        myView.postInvalidate();
                    }else{
                        myView.bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bb);
                        myView.postInvalidate();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.my_account_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initCheckBoc();
        m.setFragment(this);
        getUserNameAndPassword();
        initMyView();
        init_selerct_pic_source_linLayout();
        //改变头像
        change_head_portrait();
        init_selerct_pic_source_cancel_button();
        initPullToRefresh();
    }


    private void init_selerct_pic_source_cancel_button() {
        select_pic_source_cancel_button = (Button) getActivity().findViewById(R.id.select_pic_source_layout_cancel_button);
        select_pic_source_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selerct_pic_source_linLayout.setVisibility(View.GONE);
            }
        });
    }

    private void change_head_portrait() {

        commonTabLayout = (CommonTabLayout) getActivity().findViewById(R.id.tl_2);
        sign_in_success_layout = (LinearLayout) getActivity().findViewById(R.id.sign_in_success);
        start_camera = (Button) getActivity().findViewById(R.id.start_camera);
        open_album = (Button) getActivity().findViewById(R.id.open_album);
        open_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent,CHOOSE_PH);//打开相册。
            }
        });
        start_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");// Environment.getExternalStorageDirectory()获取SD卡的根目录.
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();

                } catch (Exception e) {
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // 个人理解拍完放在imageUri下。
                startActivityForResult(intent, TAKE_PHOTO);// 启动相机程序。
            }
        });

    }

    private void init_selerct_pic_source_linLayout() {
        selerct_pic_source_linLayout = (RelativeLayout) getActivity().findViewById(R.id.select_pic_source_layout);
        selerct_pic_source_linLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selerct_pic_source_linLayout.setVisibility(View.GONE);
            }
        });
    }

    /*
    在这实现更换头像功能
     */
    private void initMyView() {
        myView = (MyView) getActivity().findViewById(R.id.MyView);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.setVisibility(View.GONE);
                my_scrollView.setVisibility(View.GONE);
                sign_in_success_layout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                selerct_pic_source_linLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getUserNameAndPassword() {
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        sign_in_UserNameET.setText(pref.getString("user_name", ""));
        sign_in_userPasswordET.setText(pref.getString("user_password", ""));
    }

    private void initCheckBoc() {
        checkBox = (CheckBox) getActivity().findViewById(R.id.Remember_password);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxBooleanValue = b;
            }
        });
    }

    private void initView() {
        my_scrollView = (ScrollView)getActivity().findViewById(R.id.MyScrollView_2);
        scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView);
        sign_success_main_layout = (RelativeLayout)getActivity().findViewById(R.id.sign_in_success_main_layout);
        modify_buuton = (Button)getActivity().findViewById(R.id.modify_button);
        modify_layout = (RelativeLayout)getActivity().findViewById(R.id.modify_select_layout);
        pic_modify_button = (ImageButton)getActivity().findViewById(R.id.modify);
        back_image = (ImageView)getActivity().findViewById(R.id.back_main);
        sign_in_id_TextView = (TextView)getActivity().findViewById(R.id.user_id_TextView);
        user_name_TextView = (TextView) getActivity().findViewById(R.id.user_name_TextView);
        sign_in_button = (Button) getActivity().findViewById(R.id.sign_inButton);
        register_button = (Button) getActivity().findViewById(R.id.registerButton);
        sign_in_UserNameET = (EditText) getActivity().findViewById(R.id.sign_in_userNameEditText);
        sign_in_userPasswordET = (EditText) getActivity().findViewById(R.id.sign_in_userPasswordEditText);
        register_UserNameET = (EditText) getActivity().findViewById(R.id.registerUserNameEditText);
        register_UserPasswordET = (EditText) getActivity().findViewById(R.id.registerUserNamePassword);
        register_UserPassword_again_inputET = (EditText) getActivity().findViewById(R.id.register_again_input_user_password);
        f = (FrameLayout) getActivity().findViewById(R.id.frameLayout);
        l = (LinearLayout) getActivity().findViewById(R.id.linearLayout);
        registerTV = (TextView) getActivity().findViewById(R.id.registerTextView);
        back_IV = (ImageView) getActivity().findViewById(R.id.backClickPic);
        registerTV.setOnClickListener(this);
        back_IV.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);
        register_button.setOnClickListener(this);
        back_image.setOnClickListener(this);
        pic_modify_button.setOnClickListener(this);
        modify_layout.setOnClickListener(this);
        modify_buuton.setOnClickListener(this);
}

    public my_account_fragment(MainActivity m) {
        this.m = m;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerTextView:
                l.setVisibility(View.VISIBLE);
                my_scrollView.setVisibility(View.VISIBLE);
                break;
            case R.id.registerButton:
                if(register_boolean) {
                    register_user_name = register_UserNameET.getText().toString();
                    register_user_pw = register_UserPasswordET.getText().toString();
                    String register_again_input_user_pw = register_UserPassword_again_inputET.getText().toString();
                    //两次密码一致才会进入
                    if (register_user_pw.equals(register_again_input_user_pw)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    url = new URL("http://120.27.103.151:8088/PicServiceDemo/MyServlet?username=" + register_user_name + "&userpassword=" + register_user_pw);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    connection = (HttpURLConnection) url.openConnection();
                                } catch (IOException e) {
                                    register_boolean = true;
                                    Message message = new Message();
                                    message.what = NETWORK;
                                    handler.sendMessage(message);
                                    e.printStackTrace();
                                }
                                try {
                                    connection.setRequestMethod("GET");
                                } catch (ProtocolException e) {
                                    register_boolean = true;
                                    e.printStackTrace();
                                }
                                connection.setRequestProperty("charset", "UTF-8");
                                connection.setReadTimeout(8000);
                                connection.setConnectTimeout(8000);
                                StringBuilder s = new StringBuilder();
                                try {
                                    if (connection.getResponseCode() == 200) {
                                        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        StringBuilder builder = new StringBuilder();
                                        String line;
                                        try {
                                            while ((line = buff.readLine()) != null) {
                                                builder.append(line);
                                            }
                                        } catch (IOException e) {
                                            register_boolean = true;
                                            Message message = new Message();
                                            message.what = NETWORK;
                                            handler.sendMessage(message);
                                            e.printStackTrace();
                                        }
                                        String register_response_json = builder.toString();
                                        Register_Response register_response = gson.fromJson(register_response_json, Register_Response.class);
                                        if (register_response.Response_code.equals("ok")) {
                                            Message message = new Message();
                                            message.what = REGISTER_SUCCESS;
                                            message.obj = register_response.ID;
                                            handler.sendMessage(message);
                                        } else {
                                            register_boolean = true;
                                            Message message = new Message();
                                            message.what = REGISTER_FAIL;
                                            handler.sendMessage(message);
                                        }
                                    }
                                } catch (IOException e) {
                                    register_boolean = true;
                                    Message message = new Message();
                                    message.what = NETWORK;
                                    handler.sendMessage(message);
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                    } else {
                        register_boolean = true;
                        Toast.makeText(getContext(), "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            case R.id.backClickPic:
                l.setVisibility(View.GONE);
                my_scrollView.setVisibility(View.GONE);
                break;
            case R.id.sign_inButton:
                if(sign_in_boolean) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()&&getActivity().getCurrentFocus()!=null){
                        if (getActivity().getCurrentFocus().getWindowToken()!=null) {
                            Log.i("ok","有软键盘！");
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    sign_in_boolean = false;
                    final String sign_in_userName = sign_in_UserNameET.getText().toString();
                    final String sign_in_user_pw = sign_in_userPasswordET.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sign_in_url = new URL("http://120.27.103.151:8088/PicServiceDemo/MyServlet?ID=" + sign_in_userName + "&userpassword=" + sign_in_user_pw);
                                sign_in_connection = (HttpURLConnection) sign_in_url.openConnection();
                                sign_in_connection.setRequestMethod("POST");
                                sign_in_connection.setConnectTimeout(8000);
                                sign_in_connection.setReadTimeout(8000);
                                sign_in_connection.setRequestProperty("charset", "UTF-8");
                                StringBuilder sign_in_builder = new StringBuilder();
                                String sign_in_line;
                                if (sign_in_connection.getResponseCode() == 200) {
                                    BufferedReader sign_in_reader = new BufferedReader(new InputStreamReader(sign_in_connection.getInputStream()));
                                    while ((sign_in_line = sign_in_reader.readLine()) != null) {
                                        sign_in_builder.append(sign_in_line);
                                    }
                                    Sign_in_Response sign_in_response = gson.fromJson(sign_in_builder.toString(), Sign_in_Response.class);
                                    try {
                                        if (sign_in_response.Login_results.equals("ok")) {
                                            Message message = new Message();
                                            message.what = SIGN_IN_SUCCESS;
                                            message.obj = sign_in_response;
                                            handler.sendMessage(message);
                                        } else if (sign_in_response.Login_results.equals("no_pass_word")) {
                                            sign_in_boolean = true;
                                            Message message = new Message();
                                            message.what = SIGN_IN_PASSWORD_ERROR;
                                            handler.sendMessage(message);
                                        } else {
                                            sign_in_boolean = true;
                                            Message message = new Message();
                                            message.what = NO_USER;
                                            handler.sendMessage(message);
                                        }
                                    } catch (Exception e) {
                                        sign_in_boolean = true;
                                        Log.i("ok", "登录结果判断块错误！");
                                    }
                                }

                            } catch (MalformedURLException e) {
                                sign_in_boolean = true;
                                e.printStackTrace();
                            } catch (IOException e) {
                                sign_in_boolean = true;
                                Message message = new Message();
                                message.what = NETWORK;
                                handler.sendMessage(message);
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                }
                break;
                case R.id.back_main:
                    m.viewPager.setCurrentItem(0);
                    break;
                case R.id.modify:
                 modify_layout.setVisibility(View.VISIBLE);
                    break;
            case R.id.modify_button:
                selerct_pic_source_linLayout.setVisibility(View.VISIBLE);
                modify_layout.setVisibility(View.GONE);
                break;
            case R.id.modify_select_layout:
                modify_layout.setVisibility(View.GONE);
            default:
                break;
        }
    }





    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) throws Exception {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    /*
    *
    * 以下全是处理相册和相机的
    *
    *
    *
    *
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // requestCode:拍完照传回1，裁剪完传回2。
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        //TODO
                        File loop_file = new File(Environment.getExternalStorageDirectory(), "ok.jpg");// Environment.getExternalStorageDirectory()获取SD卡的根目录.
                        loop_uri = Uri.fromFile(loop_file);
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra("aspectX", 1);// 裁剪框比例
                        intent.putExtra("aspectY", 1);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, loop_uri);// 剪完放在放在imageUri下。
                        startActivityForResult(intent, CROP_PHOTO);// 启动裁剪程序。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        bitMap = BitmapFactory
                                .decodeStream(getActivity().getContentResolver().openInputStream(
                                        loop_uri));// 解码。
                       SendData(bitMap);
                        bitMap.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PH:
                if(resultCode == getActivity().RESULT_OK ){
                    //判断当前系统版本。
                    if(Build.VERSION.SDK_INT>=19){
//					4.4以上的系统版本使用这个方法处理。
                        handleImageOnKitKat(data);
                    }else{
//					4.4以下的系统版本使用这个方法处理。
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case ALBUM_CUT:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        bitMap = BitmapFactory
                                .decodeStream(getActivity().getContentResolver().openInputStream(
                                        cut_pic_uri));// 解码。
                        SendData(bitMap);
                        bitMap.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path = null;
//		getContentResolver：获得内容解析；query：查询。
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
//				getColumnIndex：获得列索引。
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        Log.i("ok","相册路径="+imagePath);
        if(imagePath!=null){
            File album_pic_file = new File(imagePath);
            album_pic_uri = Uri.fromFile(album_pic_file);
            File cut_pic_file = new File(Environment.getExternalStorageDirectory(), "cut_pic_file.jpg");
            try {
                if (cut_pic_file.exists()) {
                    cut_pic_file.delete();
                }
                cut_pic_file.createNewFile();

            } catch (Exception e) {
            }
            cut_pic_uri = Uri.fromFile(cut_pic_file);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(album_pic_uri, "image/*");
            intent.putExtra("scale", true);
            intent.putExtra("aspectX", 1);// 裁剪框比例
            intent.putExtra("aspectY", 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cut_pic_uri);// 剪完放在放在imageUri下。
            startActivityForResult(intent, ALBUM_CUT);// 启动裁剪程序。
        }else{
            Toast.makeText(getContext(), "图片路径异常！", Toast.LENGTH_LONG).show();
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        Log.i("test","Uri="+uri);
        if(DocumentsContract.isDocumentUri(getContext(), uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                Log.i("test","4.4以上的imagePath="+imagePath);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
                Log.i("test","4.4以上的imagePath="+imagePath);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
            Log.i("test","4.4以上的imagePath="+imagePath);
        }
        displayImage(imagePath);
    }
    public void SendData(Bitmap bitmap){
        //剪完之后的图片
        sign_in_success_layout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        selerct_pic_source_linLayout.setVisibility(View.GONE);
        final Bitmap bitmap_2 = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        myView.bitmap = bitmap_2;
        myView.postInvalidate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://120.27.103.151:8088/PicServiceDemo/MyServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("DELETE");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write("{\"pic_id\":\"" + sign_in_UserNameET.getText().toString() + "\"}");
                    writer.flush();
                    writer.close();
                    if (connection.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        Log.i("ok", "--------------------------" + builder.toString());
                    }
                    //发送图片资源到服务器
                    URL pic_url = new URL("http://120.27.103.151:8088/PicServiceDemo/MyServlet");
                    HttpURLConnection pic_connection = (HttpURLConnection) pic_url.openConnection();
                    pic_connection.setDoOutput(true);
                    pic_connection.setDoInput(true);
                    pic_connection.setRequestMethod("PUT");
                    pic_connection.setConnectTimeout(8000);
                    pic_connection.setReadTimeout(8000);
                    pic_out = pic_connection.getOutputStream();
                    pic_input = new FileInputStream(saveFile(bitmap_2, Environment.getExternalStorageDirectory().getPath(), "ok_1.png"));
                    while ((pic_data_len = pic_input.read()) != -1) {
                        pic_out.write(pic_data_len);
                    }
                    pic_input.close();
                    pic_out.close();
                    if (pic_connection.getResponseCode() == 200) {
                        BufferedReader reader1 = new BufferedReader(new InputStreamReader(pic_connection.getInputStream()));
                        StringBuilder builder1 = new StringBuilder();
                        String line1;
                        while ((line1 = reader1.readLine()) != null) {
                            builder1.append(line1);
                        }
                        Log.i("ok", "--------------------------图片请求返回的数据=" + builder1.toString());
                    }
                } catch (MalformedURLException e) {
                    Log.i("ok", "MalformedURLException 错误");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i("ok", "IOException 错误");
                    e.printStackTrace();
                }
            }
        }).start();

        myView.postInvalidate();
        Message message = new Message();
        message.what = 8;
        handler.sendMessage(message);
    }
    //把位图变成文件存储起来，返回文件存储的路径
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (dirFile.exists()) {
            dirFile.delete();
            dirFile.createNewFile();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        Log.i("ok", "执行文件存储成功！");
        return myCaptureFile;
    }
    //初始化下拉控件
    private void initPullToRefresh() {
         pullToRefreshLayout = ((PullToRefreshLayout) getActivity().findViewById(R.id.refresh_view));
        pullToRefreshLayout.setOnPullListener(new MyPullListener());
        //加载更多的监听
        pullToRefreshLayout.setOnLoadmoreProcessListener(new MyOnPullProcessListener());
        //下拉刷新的监听
        pullToRefreshLayout.setOnRefreshProcessListener(new MyOnPullProcessListener());
    }
    public class MyOnPullProcessListener implements PullToRefreshLayout.OnPullProcessListener
    {
        //一下拉第一个调用的方法调用一次
        @Override
        public void onPrepare(View v, int which)
        {
            // TODO Auto-generated method stub
        }
        //当触发了刷新操作就会被调用一次
        @Override
        public void onStart(View v, int which)
        {
            // TODO Auto-generated method stub
        }
        //当下拉刷新已经被触发后，在放开的时候就会被调用一次
        @Override
        public void onHandling(View v, int which)
        {
            // TODO Auto-generated method stub

        }
        //刷新完成的时候调用一次
        @Override
        public void onFinish(View v, int which)
        {
            // TODO Auto-generated method stub

        }
        //一直往下拉就会不断连续的调用
        @Override
        public void onPull(View v, float pullDistance, int which)
        {
            // TODO Auto-generated method stub
        }

    }
}
