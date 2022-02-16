package club.ccit.home.view;

import static club.ccit.common.AppRouter.PATH_HOME_PAY;

import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import club.ccit.basic.BaseActivity;
import club.ccit.home.R;
import club.ccit.home.databinding.ActivityPayBinding;
import club.ccit.widget.dialog.DatePickerDialog;
import club.ccit.widget.utils.DateFormatUtils;

/**
 * FileName: PayActivity
 *
 * @author: 张帅威
 * Date: 2022/2/9 8:34 上午
 * Description:
 * Version:
 */
@Route(path = PATH_HOME_PAY)
public class PayActivity extends BaseActivity<ActivityPayBinding> {
    private DatePickerDialog mDatePickerDialog;
    @Override
    protected void onStart() {
        super.onStart();
        initDatePicker();
        mDatePickerDialog.show("2022-02-10");
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = DateFormatUtils.str2Long("2050-05-01", false);

        // 通过时间戳初始化日期，毫秒级别
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                Log.i("LOG111",DateFormatUtils.long2Str(timestamp, false));
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
    protected ActivityPayBinding getViewBinding() {
        return ActivityPayBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePickerDialog.onDestroy();
    }
}
