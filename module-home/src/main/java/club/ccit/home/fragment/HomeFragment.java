package club.ccit.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import club.ccit.basic.BaseFragment;
import club.ccit.common.AppRouter;
import club.ccit.common.LogUtils;
import club.ccit.home.R;
import club.ccit.home.databinding.FragmentHomeBinding;
import club.ccit.widget.dialog.BottomDialog;
import club.ccit.widget.dialog.DatePickerDialog;
import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.WaitDialog;
import club.ccit.widget.dialog.city.Provinces;
import club.ccit.widget.pay.PayDialog;
import club.ccit.widget.pay.PayPasswordView;
import club.ccit.widget.title.OnTitleBarListener;
import club.ccit.widget.title.TitleBar;
import club.ccit.widget.utils.DateFormatUtils;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/2 2:22 下午
 * Description: 首页
 * Version:
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private DatePickerDialog mDatePickerDialog;
    private Provinces provinces;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                binding.timeDialog,
                binding.roomFragment,
                binding.cityPicker);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
               myToast("点击了返回");
            }
        });

    }

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
            BottomDialog.Builder(getActivity(), phone, (text, index) -> myToast(text)).show();
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
        // 等待中弹窗
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
        // 支付输入密码弹窗
        if (view.getId() == R.id.payActivity) {
            PayDialog.show(getActivity(), new PayDialog.OnDialogListener() {
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
        // 日期选择弹窗
        if (view.getId() == R.id.dateDialog) {
            initDatePicker();
            mDatePickerDialog.show("2022-02-10");
        }
        // 时间选择弹窗
        if (view.getId() == R.id.timeDialog) {
            MessageDialog.ensureTextView = "OK";
            MessageDialog.cancelTextView = "NO";
            MessageDialog.Builder(requireActivity(), "这是一个弹窗", (view1, newMessageDialog) -> newMessageDialog.onDialogDismiss()).show();
        }
        // 数据库演示
        if (view.getId() == R.id.roomFragment){
            ARouter.getInstance().build(AppRouter.PATH_ROOM_ROOM).navigation();
        }
        // 省市区弹窗
        if (view.getId() == R.id.cityPicker){
            if (provinces == null){
                provinces = new Provinces(requireActivity());
            }
            provinces.showData(new Provinces.OnCityItemClick() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSelected(String name, String cityName, String districtName) {
                    myToast(name + " " + cityName + " " + districtName);
                }

                @Override
                public void onCancel() {

                }
            });
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
    protected FragmentHomeBinding onSetViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
}
