package com.example.k.shoppingapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.shoppingapp.Activity.Baby_Activity;
import com.example.k.shoppingapp.Activity.Search_Activity;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.We_Code.CaptureActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by k on 2016/7/19.
 */
@SuppressLint("ValidFragment")
public class principal_sheet_fragment extends Fragment implements View.OnClickListener {
    final int WE_CODE = 5;
    //Home navigation
    ImageView we_code_ImageView;
    ImageView xianshiImage;
    TextView search_TextView;
    //今日头条下的四个展示位
    int jishiPicArrayId[] = {R.mipmap.a54,R.mipmap.a625,R.mipmap.a325,R.mipmap.a325_2};
    ImageView jishi,jishiyou,jishiyouxiao1,jishiyouxiao2,chaoshihuiTOP;
    ImageView chao1,chao2,chao3,chao4,chao5,chao6,chao7,chao8;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    Button nvzhuang, nanzhuang, nvxie, nanxie, kuzi, maozi, diannao, shouji, shipin, qinglvzhuang;
    private RollPagerView mRollViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.principal_sheet_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initMarqueeView();
        initCountdownView();
        initjishiView();
    }

    private void initjishiView() {
        //TODO-----------------------------------------
        chaoshihuiTOP = (ImageView)getActivity().findViewById(R.id.chaoshihuiTOP);
        jishi = (ImageView)getActivity().findViewById(R.id.xianshiImager);
        jishiyou  = (ImageView)getActivity().findViewById(R.id.jishiyouImager);
        jishiyouxiao1 = (ImageView)getActivity().findViewById(R.id.jishiyouxiao1);
        jishiyouxiao2  = (ImageView)getActivity().findViewById(R.id.jishiyouxiao2);
        try {
            chaoshihuiTOP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.mipmap.sky,800 ,214));
            jishi.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[0],300 ,400));
            jishiyou.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[1],300 ,175));
            jishiyouxiao1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[2],300 ,250));
            jishiyouxiao2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[3],300 ,250));
            jishi.setOnClickListener(this);
            jishiyou.setOnClickListener(this);
            jishiyouxiao1.setOnClickListener(this);
            jishiyouxiao2.setOnClickListener(this);
            chaoshihuiTOP.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        chao1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao1));
        chao2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao3));
        chao3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao8));
        chao4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao5));
        chao5.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao2));
        chao6.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao6));
        chao7.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao7));
        chao8.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.chao4));
    }

    private void initCountdownView() {
        CountdownView mCvCountdownViewTest1 = (CountdownView)getActivity().findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.setTag("test1");
        long time1 = (long)5 * 60 * 60 * 1000;
        mCvCountdownViewTest1.start(time1);
    }

    private void initMarqueeView() {
        MarqueeView marqueeView = (MarqueeView) getActivity().findViewById(R.id.marqueeView);
        List<String> info = new ArrayList<>();
        info.add("标题1");
        info.add("标题2");
        info.add("标题3");
        info.add("标题4");
        info.add("标题5");
        info.add("标题6");
        marqueeView.startWithList(info);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(getContext(), textView.getText()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        chao1 = (ImageView)getActivity().findViewById(R.id.chao1);
        chao2 = (ImageView)getActivity().findViewById(R.id.chao2);
        chao3 = (ImageView)getActivity().findViewById(R.id.chao3);
        chao4 = (ImageView)getActivity().findViewById(R.id.chao4);
        chao5 = (ImageView)getActivity().findViewById(R.id.chao5);
        chao6 = (ImageView)getActivity().findViewById(R.id.chao6);
        chao7 = (ImageView)getActivity().findViewById(R.id.chao7);
        chao8 = (ImageView)getActivity().findViewById(R.id.chao8);
        search_TextView = (TextView)getActivity().findViewById(R.id.search_TextView);
        we_code_ImageView = (ImageView)getActivity().findViewById(R.id.we_code_ImageView);
        mRollViewPager = (RollPagerView) getActivity().findViewById(R.id.roll_view_pager);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));
        mRollViewPager.setHintView(new IconHintView(getActivity(), R.drawable.point_focus, R.drawable.point_normal));
        nvzhuang = (Button) getActivity().findViewById(R.id.nvzhuang);
        nanzhuang = (Button) getActivity().findViewById(R.id.nanzhuang);
        nvxie = (Button) getActivity().findViewById(R.id.nvxie);
        nanxie = (Button) getActivity().findViewById(R.id.nanxie);
        kuzi = (Button) getActivity().findViewById(R.id.kuzi);
        maozi = (Button) getActivity().findViewById(R.id.maozi);
        diannao = (Button) getActivity().findViewById(R.id.diannao);
        shouji = (Button) getActivity().findViewById(R.id.shouji);
        shipin = (Button) getActivity().findViewById(R.id.shipin);
        qinglvzhuang = (Button) getActivity().findViewById(R.id.qinglvzhuang);
        nvzhuang.setOnClickListener(this);
        nanzhuang.setOnClickListener(this);
        nvxie.setOnClickListener(this);
        nanxie.setOnClickListener(this);
        kuzi.setOnClickListener(this);
        maozi.setOnClickListener(this);
        diannao.setOnClickListener(this);
        shouji.setOnClickListener(this);
        shipin.setOnClickListener(this);
        qinglvzhuang.setOnClickListener(this);
        we_code_ImageView.setOnClickListener(this);
        search_TextView.setOnClickListener(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        chao1.setOnClickListener(this);
        chao2.setOnClickListener(this);
        chao3.setOnClickListener(this);
        chao4.setOnClickListener(this);
        chao5.setOnClickListener(this);
        chao6.setOnClickListener(this);
        chao7.setOnClickListener(this);
        chao8.setOnClickListener(this);
    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        public int[] imgs = {
                R.mipmap.ok1,
                R.mipmap.ok2,
                R.mipmap.ok3,
                R.mipmap.ok4,
                R.mipmap.ok5,
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setId(position);
            view.setOnClickListener(principal_sheet_fragment.this);

            try {
                //宽/高
                view.setImageBitmap(decodeSampledBitmapFromResource(getResources(), imgs[position],720 ,300));
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }
    //图片点击事件
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                //轮播图第一张
                Intent intent_0 = new Intent(getContext(),Baby_Activity.class);
                intent_0.putExtra("value",0);
                intent_0.putExtra("value_2",0);
                intent_0.putExtra("value_3",0);
                startActivity(intent_0);
                break;
            case 1:
                //轮播图第二张
                Intent intent_1 = new Intent(getContext(),Baby_Activity.class);
                intent_1.putExtra("value",1);
                intent_1.putExtra("value_2",1);
                intent_1.putExtra("value_3",1);
                startActivity(intent_1);
                break;
            case 2:
                //轮播图第三张
                Intent intent_2 = new Intent(getContext(),Baby_Activity.class);
                intent_2.putExtra("value",2);
                intent_2.putExtra("value_2",2);
                intent_2.putExtra("value_3",2);
                startActivity(intent_2);
                break;
            case 3:
                //轮播图第四张
                Intent intent_3 = new Intent(getContext(),Baby_Activity.class);
                intent_3.putExtra("value",3);
                intent_3.putExtra("value_2",3);
                intent_3.putExtra("value_3",3);
                startActivity(intent_3);
                break;
            case 4:
                //轮播图第五张
                Intent intent_4 = new Intent(getContext(),Baby_Activity.class);
                intent_4.putExtra("value",4);
                intent_4.putExtra("value_2",4);
                intent_4.putExtra("value_3",4);
                startActivity(intent_4);
                break;
            //TODO----------------------------------
            //限时展示图片1事件
            case R.id.xianshiImager:
                Intent intent_5 = new Intent(getContext(),Baby_Activity.class);
                intent_5.putExtra("value",2);
                intent_5.putExtra("value_2",2);
                intent_5.putExtra("value_3",2);
                startActivity(intent_5);
                break;
            //限时展示右边第一张图片点击事件
            case R.id.jishiyouImager:
                Intent intent_6 = new Intent(getContext(),Baby_Activity.class);
                intent_6.putExtra("value",0);
                intent_6.putExtra("value_2",0);
                intent_6.putExtra("value_3",0);
                startActivity(intent_6);
                break;
            //限时展示右边下面第一张图片点击事件
            case R.id.jishiyouxiao1:
                Intent intent_7 = new Intent(getContext(),Baby_Activity.class);
                intent_7.putExtra("value",0);
                intent_7.putExtra("value_2",0);
                intent_7.putExtra("value_3",0);
                startActivity(intent_7);
                break;
            //限时展示右边下面第二张图片点击事件
            case R.id.jishiyouxiao2:
                Intent intent_8 = new Intent(getContext(),Baby_Activity.class);
                intent_8.putExtra("value",1);
                intent_8.putExtra("value_2",1);
                intent_8.putExtra("value_3",1);
                startActivity(intent_8);
                break;
            case R.id.chaoshihuiTOP:
                Intent intent_9 = new Intent(getContext(),Baby_Activity.class);
                intent_9.putExtra("value",4);
                intent_9.putExtra("value_2",4);
                intent_9.putExtra("value_3",4);
                startActivity(intent_9);
                break;
            case R.id.chao1:
                Intent intent_10 = new Intent(getContext(),Baby_Activity.class);
                intent_10.putExtra("value",1);
                intent_10.putExtra("value_2",1);
                intent_10.putExtra("value_3",1);
                startActivity(intent_10);
                break;
            case R.id.chao2:
                Intent intent_11 = new Intent(getContext(),Baby_Activity.class);
                intent_11.putExtra("value",4);
                intent_11.putExtra("value_2",4);
                intent_11.putExtra("value_3",4);
                startActivity(intent_11);
                break;
            case R.id.chao3:
                Intent intent_12 = new Intent(getContext(),Baby_Activity.class);
                intent_12.putExtra("value",4);
                intent_12.putExtra("value_2",4);
                intent_12.putExtra("value_3",4);
                startActivity(intent_12);
                break;
            case R.id.chao4:
                Intent intent_13 = new Intent(getContext(),Baby_Activity.class);
                intent_13.putExtra("value",5);
                intent_13.putExtra("value_2",5);
                intent_13.putExtra("value_3",5);
                startActivity(intent_13);
                break;
            case R.id.chao5:
                Intent intent_14 = new Intent(getContext(),Baby_Activity.class);
                intent_14.putExtra("value",2);
                intent_14.putExtra("value_2",2);
                intent_14.putExtra("value_3",2);
                startActivity(intent_14);
                break;
            case R.id.chao6:
                Intent intent_15 = new Intent(getContext(),Baby_Activity.class);
                intent_15.putExtra("value",2);
                intent_15.putExtra("value_2",2);
                intent_15.putExtra("value_3",2);
                startActivity(intent_15);
                break;
            case R.id.chao7:
                Intent intent_16 = new Intent(getContext(),Baby_Activity.class);
                intent_16.putExtra("value",2);
                intent_16.putExtra("value_2",2);
                intent_16.putExtra("value_3",2);
                startActivity(intent_16);
                break;
            case R.id.chao8:
                Intent intent_17 = new Intent(getContext(),Baby_Activity.class);
                intent_17.putExtra("value",1);
                intent_17.putExtra("value_2",1);
                intent_17.putExtra("value_3",1);
                startActivity(intent_17);
                break;
            case R.id.nvzhuang:
                Toast.makeText(getActivity(), "女装", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nanzhuang:
                Toast.makeText(getActivity(), "男装", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nvxie:
                Toast.makeText(getActivity(), "女鞋", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nanxie:
                Toast.makeText(getActivity(), "男鞋", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kuzi:
                Toast.makeText(getActivity(), "裤子", Toast.LENGTH_SHORT).show();
                break;
            case R.id.maozi:
                Toast.makeText(getActivity(), "帽子", Toast.LENGTH_SHORT).show();
                break;
            case R.id.diannao:
                Toast.makeText(getActivity(), "电脑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shouji:
                Toast.makeText(getActivity(), "手机", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shipin:
                Toast.makeText(getActivity(), "食品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qinglvzhuang:
                Toast.makeText(getActivity(), "情侣装", Toast.LENGTH_SHORT).show();
                break;
            case R.id.we_code_ImageView:
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), WE_CODE);
                break;
            case R.id.search_TextView:
                startActivity(new Intent(getContext(), Search_Activity.class));
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            default:
                break;
        }
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int MyWidth, int MyHeight) throws Exception {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        /**被赋值为true返回的Bitmap为null，虽然Bitmap是null了，但是BitmapFactory.Options的outWidth、
         *outHeight和outMimeType属性都会被赋值。这个技巧让我们可以在加载图片之前就获取到图片的长宽值和MIME类型，从而根据情况对图片进行压缩
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //得到压缩倍数。
        options.inSampleSize = calculateInSampleSize(options, MyWidth, MyHeight);
        //设置为false，就能得到Bitmap.
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WE_CODE && resultCode == getActivity().RESULT_OK) {
            Toast.makeText(getContext(), "扫描数据="+data.getStringExtra(CaptureActivity.EXTRA_RESULT), Toast.LENGTH_SHORT).show();
        } else {

        }
    }
}