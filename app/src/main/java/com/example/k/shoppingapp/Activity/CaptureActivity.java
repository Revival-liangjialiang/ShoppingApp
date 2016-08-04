package com.example.k.shoppingapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.We_Code.camera.CameraManager;
import com.example.k.shoppingapp.We_Code.camera.PreviewFrameShotListener;
import com.example.k.shoppingapp.We_Code.camera.Size;
import com.example.k.shoppingapp.We_Code.decode.DecodeListener;
import com.example.k.shoppingapp.We_Code.decode.DecodeThread;
import com.example.k.shoppingapp.We_Code.decode.LuminanceSource;
import com.example.k.shoppingapp.We_Code.decode.PlanarYUVLuminanceSource;
import com.example.k.shoppingapp.We_Code.decode.RGBLuminanceSource;
import com.example.k.shoppingapp.We_Code.util.DocumentUtil;
import com.example.k.shoppingapp.We_Code.view.CaptureView;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;


public class CaptureActivity extends Activity implements SurfaceHolder.Callback, PreviewFrameShotListener, DecodeListener,
        OnCheckedChangeListener, OnClickListener {

    private static final long VIBRATE_DURATION = 200L;
    private static final int REQUEST_CODE_ALBUM = 0;
    public static final String EXTRA_RESULT = "result";
    public static final String EXTRA_BITMAP = "bitmap";

    private SurfaceView previewSv;
    private CaptureView captureView;
    private CheckBox flashCb;
    private ImageButton backBtn;
    private Button albumBtn;

    private CameraManager mCameraManager;
    private DecodeThread mDecodeThread;
    private Rect previewFrameRect = null;
    private boolean isDecoding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        previewSv = (SurfaceView) findViewById(R.id.sv_preview);
        captureView = (CaptureView) findViewById(R.id.cv_capture);
        flashCb = (CheckBox) findViewById(R.id.cb_capture_flash);
        flashCb.setOnCheckedChangeListener(this);
        flashCb.setEnabled(false);
        backBtn = (ImageButton) findViewById(R.id.btn_back);
        backBtn.setOnClickListener(this);
        albumBtn = (Button) findViewById(R.id.btn_album);
        albumBtn.setOnClickListener(this);
        //获得当前系统的API版本
        //Build.VERSION_CODES.KITKAT = 19
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            albumBtn.setVisibility(View.GONE);
        }
        previewSv.getHolder().addCallback(this);
        //实例化相机管理器
        mCameraManager = new CameraManager(this);
        //注册预览帧镜头侦听器
        mCameraManager.setPreviewFrameShotListener(this);
    }
    //表面产生SurfaceHolder.Callback接口内的方法
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化相机
        mCameraManager.initCamera(holder);

        if (!mCameraManager.isCameraAvailable()) {
            Toast.makeText(CaptureActivity.this, R.string.capture_camera_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //判断手机有没有手电筒
        if (mCameraManager.isFlashlightAvailable()) {
            flashCb.setEnabled(true);
        }
        //开始预览
        mCameraManager.startPreview();
        if (!isDecoding) {
            //请求预览帧拍摄
            mCameraManager.requestPreviewFrameShot();
        }
    }
//表面改变
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
//摧毁
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //停止预览
        mCameraManager.stopPreview();
        if (mDecodeThread != null) {
            //解码线程，取消
            mDecodeThread.cancel();
        }
        //释放
        mCameraManager.release();
    }
//活动被销毁之前调用，之后活动为销毁状态
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//PreviewFrame：预览框
    @Override
    public void onPreviewFrame(byte[] data, Size dataSize) {
        if (mDecodeThread != null) {
            mDecodeThread.cancel();
        }
        if (previewFrameRect == null) {
            //得到预览框矩形
            previewFrameRect = mCameraManager.getPreviewFrameRect(captureView.getFrameRect());
        }
        //YUV源亮度平面
        PlanarYUVLuminanceSource luminanceSource = new PlanarYUVLuminanceSource(data, dataSize, previewFrameRect);
        mDecodeThread = new DecodeThread(luminanceSource, CaptureActivity.this);
        isDecoding = true;
        //开始解码
        mDecodeThread.execute();
    }
//解码成功
    @Override
    public void onDecodeSuccess(Result result, LuminanceSource source, Bitmap bitmap) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_DURATION);
        isDecoding = false;
        if(bitmap.getWidth()>100||bitmap.getHeight()>100){
            //矩阵
            Matrix matrix = new Matrix();
            matrix.postScale(100f/bitmap.getWidth(),100f/bitmap.getHeight());
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            //回收
            bitmap.recycle();
            bitmap = resizeBmp;
        }
        Intent resultData = new Intent();
        resultData.putExtra(EXTRA_RESULT, result.getText());
        resultData.putExtra(EXTRA_BITMAP, bitmap);
        setResult(RESULT_OK, resultData);
        finish();
    }
   // 解码失败
    @Override
    public void onDecodeFailed(LuminanceSource source) {
        if (source instanceof RGBLuminanceSource) {
            Toast.makeText(CaptureActivity.this, R.string.capture_decode_failed, Toast.LENGTH_SHORT).show();
        }
        isDecoding = false;
        //请求预览帧拍摄
        mCameraManager.requestPreviewFrameShot();
    }

    @Override
    public void foundPossibleResultPoint(ResultPoint point) {
        //添加可能的结果点
        captureView.addPossibleResultPoint(point);
    }
//CheckBox检查改变
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //使手电筒
            mCameraManager.enableFlashlight();
        } else {
            //关闭电筒
            mCameraManager.disableFlashlight();
        }
    }

//除了主页键，其他键被点击都会调用此方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键被点击进入判断
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_album:
                Intent intent = null;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    Log.i("ok","intent="+Intent.ACTION_OPEN_DOCUMENT);
                }
                //添加类别
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                //打开相册
                startActivityForResult(intent, REQUEST_CODE_ALBUM);
                break;
            default:
                break;
        }
    }
//选好图片调用此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //请求和响应成功就进入此判断
        if (requestCode == REQUEST_CODE_ALBUM && resultCode == RESULT_OK && data != null) {
            Bitmap cameraBitmap = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //DocumentUtil：文件利用
                String path = DocumentUtil.getPath(CaptureActivity.this, data.getData());
                cameraBitmap = DocumentUtil.getBitmap(path);
            } else {
                // Not supported in SDK lower that KitKat
            }
            if (cameraBitmap != null) {
                if (mDecodeThread != null) {
                    mDecodeThread.cancel();
                }
                int width = cameraBitmap.getWidth();
                int height = cameraBitmap.getHeight();
                int[] pixels = new int[width * height];
                //得到像素
                cameraBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                RGBLuminanceSource luminanceSource = new RGBLuminanceSource(pixels, new Size(width, height));
                mDecodeThread = new DecodeThread(luminanceSource, CaptureActivity.this);
                isDecoding = true;
                mDecodeThread.execute();
            }
        }
    }
}
