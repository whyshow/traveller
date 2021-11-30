package club.ccit.camerax;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import club.ccit.basic.BaseActivity;
import club.ccit.camerax.databinding.AvtivityPhotographBinding;
import club.ccit.common.AppRouter;

/**
 * @author: 张帅威
 * Date: 2021/11/29 9:07 上午
 * Description: 相机预览
 * Version:
 */
@Route(path = AppRouter.PATH_CAMERA_PHOTOGRAPH)
public class PhotographActivity extends BaseActivity<AvtivityPhotographBinding> {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private Preview preview;
    private ImageCapture imageCapture;
    private File file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            ProcessCameraProvider cameraProvider = null;
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    /**
     * 绑定预览视图
     * @param cameraProvider
     */
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        // 创建 Preview
        preview = new Preview.Builder()
                .build();
        // 指定所需的相机 LensFacing 选项
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        // 将所选相机和任意用例绑定到生命周期
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview,setImageAnalysis(),setImageCapture());
    }

    /**
     * 设置拍摄配置
     * @return
     */
    private ImageCapture setImageCapture(){
        imageCapture = new ImageCapture.Builder()
                .setTargetRotation(binding.previewView.getDisplay().getRotation())
                // 设置照片大小
                .setTargetResolution(new Size(1080,1920 ))
                // 设置高画质
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                //设置闪光灯
                .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                .build();
         initRevolve();
        return imageCapture;
    }

    /**
     * 设置分析配置
     * @return
     */
    private ImageAnalysis setImageAnalysis(){
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1080,1920))
                        .build();
        return imageAnalysis;
    }

    /**
     * 设置对焦框
     */
    @Override
    protected void initListener() {
        super.initListener();

        binding.previewView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取触摸的坐标
                float x = event.getX();
                float y = event.getY();
                // 将对焦框以触摸坐标为中心显示出来。
                PreviewView previewView = (PreviewView) findViewById(R.id.previewView);
                CameraFocusView cameraFocusView = new CameraFocusView(PhotographActivity.this);
                previewView.addView(cameraFocusView);
                cameraFocusView.setTouchFocusRect(x,y);
                // 创建监听
                CameraControl cameraControl = camera.getCameraControl();
                MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(binding.previewView.getWidth(), binding.previewView.getHeight());
                MeteringPoint point = factory.createPoint(x, y);
                FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                        .addPoint(point, FocusMeteringAction.FLAG_AE) // could have many
                        // auto calling cancelFocusAndMetering in 5 seconds
                        .setAutoCancelDuration(5, TimeUnit.SECONDS)
                        .build();

                ListenableFuture future = cameraControl.startFocusAndMetering(action);
                future.addListener( () -> {
                    try {
                        cameraFocusView.disDrawTouchFocusRect();
                        // process the result
                    } catch (Exception e) {
                    }
                } , ContextCompat.getMainExecutor(PhotographActivity.this));


                return false;
            }
        });
    }

    /**
     * 设置旋转
     */
    private void initRevolve() {
        OrientationEventListener orientationEventListener = new OrientationEventListener((Context)this) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation;
                // 监视方向值以确定目标旋转值
                if (orientation >= 45 && orientation < 135) {
                    rotation = Surface.ROTATION_270;
                } else if (orientation >= 135 && orientation < 225) {
                    rotation = Surface.ROTATION_180;
                } else if (orientation >= 225 && orientation < 315) {
                    rotation = Surface.ROTATION_90;
                } else {
                    rotation = Surface.ROTATION_0;
                }
                imageCapture.setTargetRotation(rotation);
            }
        };
        orientationEventListener.enable();

    }

    @Override
    protected AvtivityPhotographBinding getViewBinding() {
        return AvtivityPhotographBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     *
     * @param view
     */
    @SuppressLint("RestrictedApi")
    public void buttonImageView(View view) {
        ImageCapture.OutputFileOptions outputFileOptions;
        // 文件名
        @SuppressLint("SimpleDateFormat") String imageName = "IMG_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // 大于等于Android 10 保存在系统相册目录中。
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName+".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera/");
            outputFileOptions =  new ImageCapture.OutputFileOptions.Builder(
                    getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    .build();
        }else {
            // 小于Android 10保存在系统相册目录中。
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/Camera/"+imageName+".jpg").getAbsolutePath());
            outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        }
        // 保存
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // insert your code here.
                        myToast("成功");
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                            // 通知相册有照片更新
                            String[] paths = new String[]{file.getAbsolutePath()};
                            MediaScannerConnection.scanFile(PhotographActivity.this, paths, null, null);
                            file = null;
                        }
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException error) {
                        // insert your code here.
                        Log.i("LOG111",error.toString());
                        myToast("失败");
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
