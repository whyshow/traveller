package club.ccit.home.fragment;

import android.Manifest;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.home.databinding.FragmentHomeBinding;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    protected void initListener() {
        super.initListener();
        // 进入相机预览
        binding.cameraActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 申请拍照权限
                PermissionsUtil.requestPermission(requireActivity().getApplicationContext(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, Manifest.permission.CAMERA);

            }
        });
        // 录制视频
        binding.videoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 申请拍照和录音权限
                PermissionsUtil.requestPermission(requireActivity().getApplicationContext(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO);

            }
        });

    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
