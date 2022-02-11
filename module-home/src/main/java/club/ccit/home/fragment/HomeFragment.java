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
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.action.OnClickAction;
import club.ccit.widget.banner.base.RecyclerViewBannerBase;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.DatePickerDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.OnBottomClickListener;
import club.ccit.widget.dialog.WaitDialog;
import club.ccit.widget.dialog.base.BaseDialog;
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
        binding.recyclerViewBanner.initBannerImageView(list, new RecyclerViewBannerBase.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(requireActivity(), "clicked:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 进入相机预览
        binding.cameraActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation());
        // 录制视频
        binding.videoActivity.setOnClickListener(view -> ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation());

        binding.startHomeActivity.setOnClickListener(view ->
                // ARouter.getInstance().build(AppRouter.PATH_HOME_HOME).navigation()
                HomeActivity.launch(requireActivity(), "HomeFragment 启动的")
        );

        binding.startBottomDialog.setOnClickListener(view -> {
            String[] phone = {"你", "好", "呀"};
            BottomDialog.Builder(getActivity(), phone, new OnBottomClickListener() {
                @Override
                public void onClick(String text, int index) {
                    myToast(text);
                }
            }).show();
        });

        binding.startDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.ensureTextView = "OK";
                MessageDialog.cancelTextView = "NO";
                MessageDialog.Builder(requireActivity(), "这是一个弹窗", new OnClickAction() {
                    @Override
                    public void onClickListener(View view, BaseDialog dialog) {
                        dialog.onDialogDismiss();
                    }
                }).show();
            }
        });

        binding.startWaitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitDialog.Builder(getActivity(), "请稍后...").show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        WaitDialog.onDismiss();
                    }
                },2000);
            }
        });

        binding.payActivity.setOnClickListener(view ->
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
                }));
        binding.dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
                mDatePickerDialog.show("2022-02-10");
            }
        });

        binding.timeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.ensureTextView = "OK";
                MessageDialog.cancelTextView = "NO";
                MessageDialog.Builder(requireActivity(), "这是一个弹窗", new OnClickAction() {
                    @Override
                    public void onClickListener(View view, BaseDialog newMessageDialog) {
                        newMessageDialog.onDialogDismiss();
                    }
                }).show();
            }
        });


    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2050-05-01", false);

        // 通过时间戳初始化日期，毫秒级别
        mDatePickerDialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                myToast(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause");
    }
}
