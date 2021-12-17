package club.ccit.home.fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;

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
        binding.cameraActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation());
        // 录制视频
        binding.videoActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation());

        binding.startHomeActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation());

        binding.startBottomDialog.setOnClickListener(view -> {
            String[] phone = {"你","好","呀"};
            int[] pic = {club.ccit.widget.R.mipmap.img_wait,club.ccit.widget.R.mipmap.img_wait,club.ccit.widget.R.mipmap.img_wait};
            BottomDialog.show(getActivity(), phone, (text, index) -> myToast(text));
        });

        binding.startDialog.setOnClickListener(view -> MessageDialog.show(getActivity(), "提示", "你点击了对话框", (baseDialog, v) -> {
            MessageDialog.onDismiss();
            return false;
        }));

        binding.startWaitDialog.setOnClickListener(view -> WaitDialog.show(getActivity(),"请稍后..."));
    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
