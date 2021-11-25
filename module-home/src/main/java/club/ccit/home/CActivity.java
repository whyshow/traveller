package club.ccit.home;

import com.alibaba.android.arouter.facade.annotation.Route;

import club.ccit.basic.BaseActivity;
import club.ccit.home.databinding.ActivityCBinding;

/**
 * @author: 张帅威
 * Date: 2021/11/18 13:51
 * Description:
 * Version:
 */
@Route(path = "/home/CActivity")
public class CActivity extends BaseActivity<ActivityCBinding> {
    @Override
    protected ActivityCBinding getViewBinding() {
        return ActivityCBinding.inflate(getLayoutInflater());
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
