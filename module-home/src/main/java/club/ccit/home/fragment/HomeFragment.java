package club.ccit.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Timer;
import java.util.TimerTask;

import club.ccit.aop.click.SingleClick;
import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.home.R;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.home.ui.GridViewActivity;
import club.ccit.home.ui.LoginActivity;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.DatePickerDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;
import club.ccit.widget.dialog.city.Provinces;
import club.ccit.widget.dialog.fragment.MeFragmentDialog;
import club.ccit.widget.pay.PayDialog;
import club.ccit.widget.pay.PayPasswordView;
import club.ccit.widget.title.OnTitleBarListener;
import club.ccit.widget.title.TitleBar;
import club.ccit.widget.utils.DateFormatUtils;

/**
 * @author: 张帅威
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private DatePickerDialog mDatePickerDialog;
    private Provinces provinces;

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        setOnClickListener(
                binding.cameraActivity,
                binding.videoActivity,
                binding.startHomeActivity,
                binding.startBottomDialog,
                binding.startDialog,
                binding.startWaitDialog,
                binding.payActivity,
                binding.dateDialog,
                binding.timeDialog,
                binding.roomFragment,
                binding.cityPicker,
                binding.gridViewButton,
                binding.fragmentDialogButton,
                binding.loginActivity,
                binding.startFlutter,
                binding.singleClick);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                showToast("点击了返回");
            }
        });

    }

    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        super.onClick(view);
        // 拍照页面
        if (view.getId() == R.id.cameraActivity) {
            ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation();
        }
        // 录像页面
        if (view.getId() == R.id.videoActivity) {
            ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation();
        }
        // 底部选择弹窗
        if (view.getId() == R.id.startBottomDialog) {
            String[] phone = {"你", "好", "呀"};
            BottomDialog.Builder(getActivity(), phone, (text, index) -> showToast(text)).show();
        }
        // 对话弹窗
        if (view.getId() == R.id.startDialog) {
            MessageDialog.ensureTextView = "OK";
            MessageDialog.cancelTextView = "NO";
            MessageDialog.Builder(requireActivity(), "这是一个弹窗", (view12, dialog) -> dialog.onDialogDismiss()).show();
        }
        // 首页
        if (view.getId() == R.id.startHomeActivity) {
            ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation();
        }
        if (view.getId() == R.id.gridViewButton) {
            startActivity(new Intent(requireContext(), GridViewActivity.class));
        }
        // 等待中弹窗
        if (view.getId() == R.id.startWaitDialog) {
            WaitDialog.Builder(getActivity(), "请稍后...").showDialog();
            WaitDialog.Builder(getActivity(), "请稍后...").showDialog();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    WaitDialog.onDismiss();
                }
            }, 2000);
        }
        // 支付输入密码弹窗
        if (view.getId() == R.id.payActivity) {
            PayDialog.show(getActivity(), new PayDialog.OnDialogListener() {
                @Override
                public void onPassFinish(String password, PayPasswordView payPasswordView) {
                    if ("123456".equals(password)) {
                        showToast("密码正确");
                        PayDialog.onDismiss();
                    } else {
                        showToast("密码不正确");
                        payPasswordView.setErrorStyle();
                    }
                }

                @Override
                public void onPayForget() {

                }
            });
        }
        // 日期选择弹窗
        if (view.getId() == R.id.dateDialog) {
            initDatePicker();
            mDatePickerDialog.show("2022-02-10");
        }
        // 时间选择弹窗
        if (view.getId() == R.id.timeDialog) {
            ARouter.getInstance().build(AppRouter.PATH_FACIAL_FEATURE).navigation();
        }
        // 数据库演示
        if (view.getId() == R.id.roomFragment) {
            ARouter.getInstance().build(AppRouter.PATH_ROOM_ROOM).navigation();
        }
        // 省市区弹窗
        if (view.getId() == R.id.cityPicker) {
            if (provinces == null) {
                provinces = new Provinces(requireActivity());
            }
            provinces.showData(new Provinces.OnCityItemClick() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSelected(String name, String cityName, String districtName) {
                    showToast(name + " " + cityName + " " + districtName);
                }

                @Override
                public void onCancel() {

                }
            });
        }


        if (R.id.fragmentDialogButton == view.getId()) {
            MeFragmentDialog dialog = new MeFragmentDialog();
            dialog.show(requireActivity().getSupportFragmentManager(), "MyDialogFragment");
        }
        if (R.id.loginActivity == view.getId()) {
            startActivity(new Intent(requireContext(), LoginActivity.class));
        }

        if (R.id.singleClick == view.getId()) {
            showToast("点击了一次");
            Log.i("LOG111", "点击了一次");
        }
    }


    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2050-05-01", false);

        // 通过时间戳初始化日期，毫秒级别
        mDatePickerDialog = new DatePickerDialog(requireActivity(), timestamp -> showToast(DateFormatUtils.long2Str(timestamp, false)), beginTimestamp, endTimestamp);
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
    protected FragmentHomeBinding onSetViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
