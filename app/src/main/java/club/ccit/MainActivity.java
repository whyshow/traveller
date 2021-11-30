package club.ccit;
import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;

import club.ccit.databinding.ActivityMainBinding;

/**
 * @author: 张帅威
 * Date: 2021/11/18 13:17
 * Description:
 * Version:
 */
@Route(path = AppRouter.PATH_APP_HOME)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 进入相机预览
     * @param view
     */
    public void cameraActivity(View view) {
        ARouter.getInstance().build(AppRouter.PATH_CAMERA_PHOTOGRAPH).navigation();
    }

    /**
     * 录制视频
     * @param view
     */
    public void videoActivity(View view) {
        ARouter.getInstance().build(AppRouter.PATH_CAMERA_VIDEO).navigation();
    }
}