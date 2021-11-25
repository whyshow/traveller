package club.ccit;

import com.alibaba.android.arouter.facade.annotation.Route;

import club.ccit.basic.BaseActivity;
import club.ccit.databinding.ActivityBBinding;

/**
 * @author: 张帅威
 * Date: 2021/11/18 13:17
 * Description:
 * Version:
 */
@Route(path = "/app/BActivity")
public class BActivity extends BaseActivity<ActivityBBinding> {
    @Override
    protected ActivityBBinding getViewBinding() {
        return ActivityBBinding.inflate(getLayoutInflater());
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
