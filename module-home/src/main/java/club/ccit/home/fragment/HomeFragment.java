package club.ccit.home.fragment;

import android.app.Dialog;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.OnBottomClickListener;
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

        binding.startBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] phone = {"你","好","呀"};
                int[] pic = {club.ccit.widget.R.mipmap.img_wait,club.ccit.widget.R.mipmap.img_wait,club.ccit.widget.R.mipmap.img_wait};
                BottomDialog.show(getActivity(), phone, new OnBottomClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        myToast(text);
                    }
                });
            }
        });

        binding.startDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.show(getActivity(), "提示", "你点击了对话框", new MessageDialog.OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(Dialog baseDialog, View v) {
                        MessageDialog.onDismiss();
                        return false;
                    }
                });
            }
        });

        binding.startWaitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitDialog.show(getActivity(),"请稍后...");
            }
        });


    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
