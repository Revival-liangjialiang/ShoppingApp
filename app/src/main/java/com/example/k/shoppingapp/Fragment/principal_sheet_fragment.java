package com.example.k.shoppingapp.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.k.shoppingapp.R;
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
    int jishiPicArrayId[] = {R.mipmap.jishi,R.mipmap.jishiyou,R.mipmap.jishiyouxiao1,R.mipmap.jishiyouxiao2};
    ImageView jishi,jishiyou,jishiyouxiao1,jishiyouxiao2,chaoshihuiTOP;
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
        chaoshihuiTOP = (ImageView)getActivity().findViewById(R.id.chaoshihuiTOP);
        jishi = (ImageView)getActivity().findViewById(R.id.xianshiImager);
        jishiyou  = (ImageView)getActivity().findViewById(R.id.jishiyouImager);
        jishiyouxiao1 = (ImageView)getActivity().findViewById(R.id.jishiyouxiao1);
        jishiyouxiao2  = (ImageView)getActivity().findViewById(R.id.jishiyouxiao2);
        try {
            chaoshihuiTOP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.mipmap.chaoshihuitop,800 ,214));
            jishi.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[0],300 ,400));
            jishiyou.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[1],300 ,175));
            jishiyouxiao1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[2],300 ,250));
            jishiyouxiao2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), jishiPicArrayId[3],300 ,250));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        public int[] imgs = {
                R.mipmap.a,
                R.mipmap.b,
                R.mipmap.c,
                R.mipmap.d,
                R.mipmap.e,
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
                Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
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
}