package club.ccit.camerax.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.media.Image;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import club.ccit.basic.BaseActivity;
import club.ccit.camerax.view.CameraFocusView;
import club.ccit.camerax.R;
import club.ccit.camerax.databinding.AvtivityPhotographBinding;
import club.ccit.camerax.permission.PermissionListener;
import club.ccit.camerax.permission.PermissionsUtil;
import club.ccit.common.AppRouter;
import club.ccit.common.LogUtils;

/**
 * @author: 张帅威
 * Date: 2021/11/29 9:07 上午
 * Description: 相机预览
 * Version:
 */
@Route(path = AppRouter.PATH_CAMERA_PHOTOGRAPH)
public class PhotographActivity extends BaseActivity<AvtivityPhotographBinding> implements ImageAnalysis.Analyzer {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private Preview preview;
    private ImageView mImageView;
    private ImageCapture imageCapture;
    private File file;
    private int rotation;
    private ImageAnalysis imageAnalysis;
    private Size size = new Size(1080, 1920);
    private Bitmap bitmap = null;
    /**
     * 线程池
     */
    private ExecutorService cameraExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        boolean load = OpenCVLoader.initDebug();
        if (load) {
            Log.i("CV", "OpenCV init success");
        } else {
            Log.e("CV", "OpenCV init failed");
        }
        mImageView = binding.myImageView;
        // 判断是否有权限
        PermissionsUtil.requestPermission(getApplicationContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                cameraProviderFuture = ProcessCameraProvider.getInstance(PhotographActivity.this);
                cameraProviderFuture.addListener(() -> {
                    ProcessCameraProvider cameraProvider = null;
                    try {
                        cameraProvider = cameraProviderFuture.get();
                        bindPreview(cameraProvider);
                        initView();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }, ContextCompat.getMainExecutor(PhotographActivity.this));
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                finish();
            }
        }, Manifest.permission.CAMERA);

        setOnClickListener(binding.buttonImageView);
    }

    /**
     * 绑定预览视图
     *
     * @param cameraProvider
     */
    @SuppressLint("UnsafeOptInUsageError")
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        // 创建线程
        cameraExecutor = Executors.newFixedThreadPool(4);
        // 创建 Preview
        preview = new Preview.Builder()
                .build();

        // 指定所需的相机 LensFacing 选项
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        // 将所选相机和任意用例绑定到生命周期
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

        @SuppressLint("UnsafeOptInUsageError") UseCaseGroup useCaseGroup = new UseCaseGroup.Builder()
                .addUseCase(preview)
                .addUseCase(setImageAnalysis()).addUseCase(setImageCapture())
                .build();
        setImageAnalysis();
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, useCaseGroup);
    }

    /**
     * 设置拍摄配置
     *
     * @return
     */
    private ImageCapture setImageCapture() {
        imageCapture = new ImageCapture.Builder()
                .setTargetRotation(binding.previewView.getDisplay().getRotation())
                // 设置照片大小
                .setTargetResolution(size)
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
     *
     * @return
     */
    private ImageAnalysis setImageAnalysis() {
        imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1080, 1920))
                .build();
        imageAnalysis.setAnalyzer(cameraExecutor, this);
        return imageAnalysis;
    }

    /**
     * 设置对焦框
     */
    protected void initView() {
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
                cameraFocusView.setTouchFocusRect(x, y);
                // 创建监听
                CameraControl cameraControl = camera.getCameraControl();
                MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(binding.previewView.getWidth(), binding.previewView.getHeight());
                MeteringPoint point = factory.createPoint(x, y);
                FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                        .addPoint(point, FocusMeteringAction.FLAG_AE)
                        // 设置自动取消时间，设置为5秒
                        .setAutoCancelDuration(5, TimeUnit.SECONDS)
                        .build();

                ListenableFuture future = cameraControl.startFocusAndMetering(action);
                future.addListener(() -> {
                    try {
                        cameraFocusView.disDrawTouchFocusRect();
                        // process the result
                    } catch (Exception e) {
                    }
                }, ContextCompat.getMainExecutor(PhotographActivity.this));


                return false;
            }
        });
    }

    /**
     * 设置旋转
     */
    private void initRevolve() {
        OrientationEventListener orientationEventListener = new OrientationEventListener((Context) this) {
            @Override
            public void onOrientationChanged(int orientation) {
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
    protected AvtivityPhotographBinding onSetViewBinding() {
        return AvtivityPhotographBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.buttonImageView) {
            ImageCapture.OutputFileOptions outputFileOptions;
            // 文件名
            @SuppressLint("SimpleDateFormat") String imageName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 大于等于Android 10 保存在系统相册目录中。
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera/");
                outputFileOptions = new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                        .build();
            } else {
                // 小于Android 10保存在系统相册目录中。
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera/" + imageName + ".jpg").getAbsolutePath());
                outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            }
            // 保存
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            // insert your code here.
                            showToast("成功");
                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                                // 通知相册有照片更新
                                String[] paths = new String[]{file.getAbsolutePath()};
                                MediaScannerConnection.scanFile(PhotographActivity.this, paths, null, null);
                                file = null;
                            }
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException error) {
                            // insert your code here.
                            showToast("失败");
                        }
                    }
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
        if (imageAnalysis != null) {
            imageAnalysis.clearAnalyzer();
        }

    }

    /**
     * 色彩转换
     *
     * @param image
     */
    @Override
    public void analyze(@NonNull ImageProxy image) {
        LogUtils.i("开始");
        @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
        Mat src = imageToMat(image1);
        Mat temp = new Mat();
        Mat dst = new Mat();
        //色彩空间转换
        Imgproc.cvtColor(src, temp, Imgproc.COLOR_BGRA2BGR);
        // COLOR_BGR2GRAY : 颜色_BGR2灰色
        Imgproc.cvtColor(temp, dst, Imgproc.COLOR_BGR2GRAY);
        Mat newMat = rotateRight(src);
        //矩阵 转 bitmap
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(newMat.width(), newMat.height(), Bitmap.Config.ARGB_8888);
        }
        Utils.matToBitmap(newMat, bitmap);
        LogUtils.i("结束");
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                // 显示在屏幕上
                mImageView.setImageBitmap(bitmap);
                // 关闭释放内存
                image.close();
            }
        });
    }

    /**
     * Mat 逆时针旋转
     *
     * @param src
     * @return
     */
    public static Mat rotateRight(Mat src) {
        Mat tmp = new Mat();
        // 此函数是转置、（即将图像逆时针旋转90度，然后再关于x轴对称）
        Core.transpose(src, tmp);
        Mat result = new Mat();
        // flipCode = 0 绕x轴旋转180， 也就是关于x轴对称
        // flipCode = 1 绕y轴旋转180， 也就是关于y轴对称
        // flipCode = -1 此函数关于原点对称
        Core.flip(tmp, result, 1);
        return result;
    }

    /**
     * Image 转 OpenCV Mat
     *
     * @param image
     * @return
     */
    private Mat imageToMat(Image image) {
        ByteBuffer buffer;
        int rowStride;
        int pixelStride;
        int width = image.getWidth();
        int height = image.getHeight();
        int offset = 0;
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[image.getWidth() * image.getHeight() * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];
        for (int i = 0; i < planes.length; i++) {
            buffer = planes[i].getBuffer();
            rowStride = planes[i].getRowStride();
            pixelStride = planes[i].getPixelStride();
            int w = (i == 0) ? width : width / 2;
            int h = (i == 0) ? height : height / 2;
            for (int row = 0; row < h; row++) {
                int bytesPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8;
                if (pixelStride == bytesPerPixel) {
                    int length = w * bytesPerPixel;
                    buffer.get(data, offset, length);
                    if (h - row != 1) {
                        buffer.position(buffer.position() + rowStride - length);
                    }
                    offset += length;
                } else {
                    if (h - row == 1) {
                        buffer.get(rowData, 0, width - pixelStride + 1);
                    } else {
                        buffer.get(rowData, 0, rowStride);
                    }
                    for (int col = 0; col < w; col++) {
                        data[offset++] = rowData[col * pixelStride];
                    }
                }
            }
        }
        Mat mat = new Mat(height, width, CvType.CV_8UC1);
        mat.put(0, 0, data);
        return mat;
    }
}
