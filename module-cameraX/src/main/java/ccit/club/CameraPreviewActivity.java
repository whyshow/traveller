package ccit.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import ccit.club.databinding.AvtivityCameraBinding;
import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;

/**
 * Copyright (C), 2011-2021, 万臻生态科技有限公司
 * FileName: CameraPreview
 *
 * @author: swzhang3
 * Date: 2021/11/29 9:07 上午
 * Description: 相机预览
 * Version:
 */
@Route(path = AppRouter.PATH_CAMERA_VIEW)
public class CameraPreviewActivity extends BaseActivity<AvtivityCameraBinding> {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
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
    ImageCapture imageCapture;
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        // 预览图片分辨率
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1920, 1080))
                        .build();
        // 拍摄时配置
        imageCapture = new ImageCapture.Builder()
                .setTargetRotation(binding.previewView.getDisplay().getRotation())
                .setTargetResolution(new Size(1080,1920 ))
                        .build();
        initRevolve();
        // 创建 Preview
        Preview preview = new Preview.Builder()
                .build();

        // 指定所需的相机 LensFacing 选项
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        // 将所选相机和任意用例绑定到生命周期
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview,imageAnalysis,imageCapture);
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.previewView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("MainActivity", "X坐标：" + event.getX() + ",Y坐标：" + event.getY());
                float x = event.getX();
                float y = event.getY();
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

                // Monitors orientation values to determine the target rotation value
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
    protected AvtivityCameraBinding getViewBinding() {
        return AvtivityCameraBinding.inflate(getLayoutInflater());
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
        File file = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/Camera/test.jpg").getAbsolutePath());
        }else {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/Camera/test.jpg").getAbsolutePath());
        }
        ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        // insert your code here.
                        myToast("成功");
                    }
                    @Override
                    public void onError(ImageCaptureException error) {
                        // insert your code here.
                        myToast("失败");
                    }
                }
        );
    }
}
