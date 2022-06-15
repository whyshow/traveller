package club.ccit.home.ui;

import com.alibaba.android.arouter.launcher.ARouter;

import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;
import club.ccit.common.LogUtils;
import club.ccit.home.databinding.ActivityLaunchBinding;

/**
 * FileName: LaunchActivity
 * @author: 张帅威
 * Date: 2022/1/12 12:20 下午
 * Description: 启动页面
 * Version:
 */
public class LaunchActivity extends BaseActivity<ActivityLaunchBinding> {

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i("LaunchActivity onStart");
        ARouter.getInstance().build(AppRouter.PATH_APP_HOME).navigation();
        finish();
    }

    @Override
    protected ActivityLaunchBinding onSetViewBinding() {
        return ActivityLaunchBinding.inflate(getLayoutInflater());
    }
}
