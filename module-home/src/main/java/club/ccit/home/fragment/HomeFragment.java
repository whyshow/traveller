package club.ccit.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.common.LogUtils;
import club.ccit.home.HomeActivity;
import club.ccit.home.R;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.DatePickerDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;
import club.ccit.widget.pay.PayDialog;
import club.ccit.widget.pay.PayPasswordView;
import club.ccit.widget.utils.DateFormatUtils;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private DatePickerDialog mDatePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        List<String> list = new ArrayList<>();
        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=3184221534,2238244948&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1794621527,1964098559&fm=27&gp=0.jpg");
        binding.recyclerViewBanner.initBannerImageView(list, position -> Toast.makeText(requireActivity(), "clicked:" + position, Toast.LENGTH_SHORT).show());
        setOnClickListener(
                binding.cameraActivity,
                binding.videoActivity,
                binding.startHomeActivity,
                binding.startBottomDialog,
                binding.startDialog,
                binding.startWaitDialog,
                binding.payActivity,
                binding.dateDialog,
                binding.timeDialog);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.cameraActivity) {
            ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation();
        }
        if (view.getId() == R.id.videoActivity) {
            ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation();
        }
        if (view.getId() == R.id.startBottomDialog) {
            String[] phone = {"你", "好", "呀"};
            BottomDialog.Builder(getActivity(), phone, (text, index) -> myToast(text)).show();
        }
        if (view.getId() == R.id.startDialog) {
            MessageDialog.ensureTextView = "OK";
            MessageDialog.cancelTextView = "NO";
            MessageDialog.Builder(requireActivity(), "这是一个弹窗", (view12, dialog) -> dialog.onDialogDismiss()).show();
        }
        if (view.getId() == R.id.startHomeActivity) {
          ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation();
        }
        if (view.getId() == R.id.startWaitDialog) {
            WaitDialog.Builder(getActivity(), "请稍后...").show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    WaitDialog.onDismiss();
                }
            }, 2000);
        }

        if (view.getId() == R.id.payActivity) {
            PayDialog.show(requireActivity(), new PayDialog.OnDialogListener() {
                @Override
                public void onPassFinish(String password, PayPasswordView payPasswordView) {
                    if ("123456".equals(password)) {
                        myToast("密码正确");
                        PayDialog.onDismiss();
                    } else {
                        myToast("密码不正确");
                        payPasswordView.setErrorStyle();
                    }
                }

                @Override
                public void onPayForget() {

                }
            });
        }
        if (view.getId() == R.id.dateDialog) {
            initDatePicker();
            mDatePickerDialog.show("2022-02-10");
        }
        if (view.getId() == R.id.timeDialog) {
            MessageDialog.ensureTextView = "OK";
            MessageDialog.cancelTextView = "NO";
            MessageDialog.Builder(requireActivity(), "这是一个弹窗", (view1, newMessageDialog) -> newMessageDialog.onDialogDismiss()).show();
        }
    }


    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2050-05-01", false);

        // 通过时间戳初始化日期，毫秒级别
        mDatePickerDialog = new DatePickerDialog(requireActivity(), timestamp -> myToast(DateFormatUtils.long2Str(timestamp, false)), beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePickerDialog.setCancelable(false);
        // 不显示时和分
        mDatePickerDialog.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePickerDialog.setScrollLoop(false);
        // 不允许滚动动画
        mDatePickerDialog.setCanShowAnim(false);
    }

    @Override
    protected FragmentHomeBinding getViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
