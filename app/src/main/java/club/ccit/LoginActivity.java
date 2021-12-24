package club.ccit;

import club.ccit.basic.BaseActivity;
import club.ccit.databinding.ActivityLoginBinding;

/**
 * FileName: LoginActivity
 *
 * @author: 张帅威
 * Date: 2021/12/24 9:16 上午
 * Description: 登录页面
 * Version:
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
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
