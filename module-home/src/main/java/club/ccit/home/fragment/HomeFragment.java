package club.ccit.home.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

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
                ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation();
            }
        });
        // 录制视频
        binding.videoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation();


            }
        });
        binding.startHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation();
            }
        });

    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
