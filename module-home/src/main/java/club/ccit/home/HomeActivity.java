package club.ccit.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import club.ccit.basic.BaseActivity;
import club.ccit.common.AppRouter;
import club.ccit.home.databinding.ActivityHomeBinding;
import club.ccit.widget.dialog.WaitDialog;

/**
 * @author: 张帅威
 * Date: 2021/11/18 10:32
 * Description:
 * Version:
 */
@Route(path = AppRouter.PATH_HOME_HOME)
public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WaitDialog.show(this,"请稍后...");
    }

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(AppRouter.PATH_APP_HOME).navigation();
            }
        });
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }
}
