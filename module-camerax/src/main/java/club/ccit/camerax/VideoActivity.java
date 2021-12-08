package club.ccit.camerax;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import club.ccit.basic.BaseActivity;
import club.ccit.camerax.databinding.AvtivityVideoBinding;
import club.ccit.common.AppRouter;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/30 9:36 上午
 * Description: 录制视频
 * Version:
 */

@Route(path = AppRouter.PATH_CAMERA_VIDEO)
public class VideoActivity extends BaseActivity<AvtivityVideoBinding> {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private Preview preview;
    private VideoCapture.OutputFileOptions build;
    private VideoCapture mVideoCapture;
    private Timer timer;
    File file = null;
    /**
     * 是否处于录制中
     */
    private boolean isTranscribe = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Window window = getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
    @SuppressLint("RestrictedApi")
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        // 录像时配置
        mVideoCapture = new VideoCapture.Builder()
                .setTargetRotation(binding.previewVideoView.getDisplay().getRotation())
                .setVideoFrameRate(25)
                .setBitRate(2 * 1024 * 1024)
                .setMaxResolution(new Size(1280,720))
                .setAudioRecordSource(MediaRecorder.AudioSource.MIC)
                .build();
        initRevolve(mVideoCapture);
        // 创建 Preview
        preview = new Preview.Builder()
                .build();

        // 指定所需的相机 LensFacing 选项
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        // 将所选相机和任意用例绑定到生命周期
        preview.setSurfaceProvider(binding.previewVideoView.getSurfaceProvider());

        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview,mVideoCapture);
    }

    /**
     * 设置旋转
     * @param mVideoCapture
     */
    private void initRevolve(VideoCapture mVideoCapture) {
        OrientationEventListener orientationEventListener = new OrientationEventListener((Context)this) {
            @SuppressLint("RestrictedApi")
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
                mVideoCapture.setTargetRotation(rotation);
            }
        };
        orientationEventListener.enable();
    }

    @Override
    protected AvtivityVideoBinding getViewBinding() {
        return AvtivityVideoBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.black;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 录制按钮
     * @param view
     */
    @SuppressLint({"RestrictedApi", "UseCompatLoadingForDrawables"})
    public void buttonVideo(View view) {
        if (isTranscribe){
            // 处于录制状态，停止录制视频
            // 设置开始录制图标
            binding.buttonStart.setBackgroundResource(R.mipmap.icon_start);
            isTranscribe = false;
            // 停止计时
            timer.cancel();
            timer = null;
            time = -1000;
            binding.timeTextView.setVisibility(View.GONE);
            mVideoCapture.stopRecording();
        }else {
            // 未处于录制状态，开始录制视频
            // 设置正在录制图标
            binding.buttonStart.setBackgroundResource(R.mipmap.icon_stop);
            isTranscribe = true;

            @SuppressLint("SimpleDateFormat") String imageName = "VID_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
                // 大于Android 9.0 使用新的存储方式。
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, imageName+".mp4");
                contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "DCIM/Camera/");
                build =  new VideoCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
                        .build();
            }else {
                // 小于Android 10使用旧的存储方式。
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera/" + imageName + ".mp4").getAbsolutePath());
                build = new VideoCapture.OutputFileOptions.Builder(file).build();
            }
            // 开始记录时间
            setTimerTask();
            binding.timeTextView.setVisibility(View.VISIBLE);
            mVideoCapture.startRecording(build, CameraXExecutors.mainThreadExecutor(), new VideoCapture.OnVideoSavedCallback() {
                @Override
                public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                    myToast("视频保存成功");
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                        // 通知相册有照片更新
                        String[] paths = new String[]{file.getAbsolutePath()};
                        MediaScannerConnection.scanFile(VideoActivity.this, paths, null, null);
                        file = null;
                    }
                }

                @Override
                public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                    myToast("视频保存失败");
                }
            });
        }
    }

    private long time = -1000;
    private TimerTask timerTask;
    private void setTimerTask() {
        if (timer == null){
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                time = time + 1000;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.timeTextView.setText(stringForTime(time));
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    /**
     * 时间转 00：00：00 格式
     * @param timeMs
     * @return
     */
    public String stringForTime(long timeMs){
        long totalSeconds = timeMs/1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds/60)%60;
        long hours = totalSeconds/3600;
        return new Formatter().format("%02d:%02d",minutes,seconds).toString();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
