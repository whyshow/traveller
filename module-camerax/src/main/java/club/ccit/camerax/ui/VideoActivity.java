package club.ccit.camerax.ui;

import static club.ccit.camerax.utils.Utils.createVideoThumbnail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Size;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
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
import java.util.concurrent.TimeUnit;

import club.ccit.basic.BaseDataBindingActivity;
import club.ccit.camerax.view.CameraFocusView;
import club.ccit.camerax.R;
import club.ccit.camerax.databinding.AvtivityVideoBinding;
import club.ccit.camerax.permission.PermissionListener;
import club.ccit.camerax.permission.PermissionsUtil;
import club.ccit.common.AppRouter;
import cn.jzvd.Jzvd;

/**
 * @author: 张帅威
 * Date: 2021/11/30 9:36 上午
 * Description: 录制视频
 * Version:
 */

@Route(path = AppRouter.PATH_CAMERA_VIDEO)
public class VideoActivity extends BaseDataBindingActivity<AvtivityVideoBinding> {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private Preview preview;
    private VideoCapture.OutputFileOptions build;
    private VideoCapture mVideoCapture;
    private Timer timer;
    private String playUrl;

    File file = null;
    /**
     * 录像分辨率
     */
    private Size size = new Size(1920, 1080);
    /**
     * 帧率
     */
    private int frameRate = 30;
    /**
     * 码率
     */
    private int bitRate = 2 * 1024 * 1024;
    /**
     * 是否处于录制中
     */
    private boolean isTranscribe = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 申请拍照和录音权限
        PermissionsUtil.requestPermission(getApplicationContext(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                cameraProviderFuture = ProcessCameraProvider.getInstance(VideoActivity.this);
                cameraProviderFuture.addListener(() -> {
                    ProcessCameraProvider cameraProvider = null;
                    try {
                        cameraProvider = cameraProviderFuture.get();
                        bindPreview(cameraProvider);
                        initView();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }, ContextCompat.getMainExecutor(VideoActivity.this));
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                finish();
            }
        }, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);

        setOnClickListener(R.id.back,R.id.buttonCancel);
    }

    /**
     * 绑定预览视图
     *
     * @param cameraProvider
     */
    @SuppressLint("RestrictedApi")
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        // 录像时配置
        mVideoCapture = new VideoCapture.Builder()
                .setTargetRotation(binding.previewVideoView.getDisplay().getRotation())
                .setVideoFrameRate(frameRate)
                .setBitRate(bitRate)
                .setMaxResolution(size)
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

        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, mVideoCapture);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.buttonCancel) {
            binding.buttonCancel.setVisibility(View.GONE);
            binding.buttonOk.setVisibility(View.GONE);
            binding.previewVideoView.setVisibility(View.VISIBLE);
            binding.jzVideo.setVisibility(View.GONE);
            binding.buttonStart.setVisibility(View.VISIBLE);
        }else if (id == R.id.buttonOk){
            Intent intent=new Intent();
            //设置要回传的数据
            intent.putExtra("playUrl",playUrl);
            setResult(403,intent);
            finish();
        }
    }

    /**
     * 设置旋转
     *
     * @param mVideoCapture
     */
    private void initRevolve(VideoCapture mVideoCapture) {
        OrientationEventListener orientationEventListener = new OrientationEventListener((Context) this) {
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

    /**
     * 录制按钮
     *
     * @param view
     */
    @SuppressLint({"RestrictedApi", "UseCompatLoadingForDrawables"})
    public void buttonVideo(View view) {
        binding.buttonStart.setClickable(false);
        if (isTranscribe) {
            stopTranscribe();
        } else {
            // 未处于录制状态，开始录制视频
            // 设置正在录制图标
            binding.buttonStart.setBackgroundResource(R.drawable.icon_stop);
            binding.jzVideo.setVisibility(View.GONE);
            binding.previewVideoView.setVisibility(View.VISIBLE);
            isTranscribe = true;
            @SuppressLint("SimpleDateFormat") String imageName = "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                // 大于Android 9.0 使用新的存储方式。
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, imageName + ".mp4");
                contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "DCIM/Camera/");
                build = new VideoCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
                        .build();
                playUrl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera/" + imageName + ".mp4").getAbsolutePath();
            } else {
                // 小于Android 10使用旧的存储方式。
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera/" + imageName + ".mp4").getAbsolutePath());
                build = new VideoCapture.OutputFileOptions.Builder(file).build();
                playUrl = file.getAbsolutePath();
            }
            // 开始记录时间
            setTimerTask();
            binding.timeTextView.setVisibility(View.VISIBLE);
            binding.buttonStart.setClickable(true);
            mVideoCapture.startRecording(build, CameraXExecutors.mainThreadExecutor(), new VideoCapture.OnVideoSavedCallback() {
                @Override
                public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                    binding.buttonStart.setVisibility(View.GONE);
                    binding.buttonStart.setVisibility(View.GONE);
                    binding.timeTextView.setVisibility(View.GONE);
                    binding.buttonCancel.setVisibility(View.VISIBLE);
                    binding.buttonOk.setVisibility(View.VISIBLE);
                    binding.buttonStart.setBackgroundResource(R.drawable.icon_start);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        // 通知相册有照片更新
                        String[] paths = new String[]{file.getAbsolutePath()};
                        MediaScannerConnection.scanFile(VideoActivity.this, paths, null, null);
                        file = null;
                    }
                    binding.previewVideoView.setVisibility(View.GONE);
                    binding.jzVideo.setVisibility(View.VISIBLE);
                    binding.jzVideo.setUp(playUrl, "");
                    binding.jzVideo.posterImageView.setImageBitmap(createVideoThumbnail(playUrl,2));
                    binding.jzVideo.fullscreenButton.setVisibility(View.VISIBLE);
                    Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
                }

                @Override
                public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                    myToast("视频保存失败");
                }
            });

        }
    }

    /**
     * 停止录制
     */
    @SuppressLint("RestrictedApi")
    private void stopTranscribe() {
        // 处于录制状态，停止录制视频
        // 设置开始录制图标
        isTranscribe = false;
        // 停止计时
        timer.cancel();
        timer = null;
        time = -1000;
        mVideoCapture.stopRecording();
        binding.buttonStart.setClickable(true);
    }

    private long time = -1000;
    private TimerTask timerTask;

    private void setTimerTask() {
        if (timer == null) {
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (time >= 15 * 1000){
                    stopTranscribe();
                }else {
                    time = time + 1000;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.timeTextView.setText(stringForTime(time));
                        }
                    });
                }

            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 按键监听
     */
    protected void initView() {
        binding.previewVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取触摸的坐标
                float x = event.getX();
                float y = event.getY();
                // 将对焦框以触摸坐标为中心显示出来。
                PreviewView previewView = (PreviewView) findViewById(R.id.previewVideoView);
                CameraFocusView cameraFocusView = new CameraFocusView(VideoActivity.this);
                previewView.addView(cameraFocusView);
                cameraFocusView.setTouchFocusRect(x, y);
                // 创建监听
                CameraControl cameraControl = camera.getCameraControl();
                MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(binding.previewVideoView.getWidth(), binding.previewVideoView.getHeight());
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
                }, ContextCompat.getMainExecutor(VideoActivity.this));
                return false;
            }
        });
    }

    /**
     * 时间转 00：00：00 格式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(long timeMs) {
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        return new Formatter().format("%02d:%02d", minutes, seconds).toString();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        Jzvd.releaseAllVideos();
    }

    @Override
    protected AvtivityVideoBinding onSetViewBinding() {
        return AvtivityVideoBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
