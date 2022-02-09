package club.ccit.home.view;

import static club.ccit.common.AppRouter.PATH_HOME_PAY;

import com.alibaba.android.arouter.facade.annotation.Route;
import club.ccit.basic.BaseActivity;
import club.ccit.home.R;
import club.ccit.home.databinding.ActivityPayBinding;

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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected ActivityPayBinding getViewBinding() {
        return ActivityPayBinding.inflate(getLayoutInflater());
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
