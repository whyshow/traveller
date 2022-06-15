package club.ccit.home.ui;

import club.ccit.basic.BaseActivity;
import club.ccit.home.databinding.ActivityLoginBinding;

/**
 * FileName: LoginActivity
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/24 9:16 上午
 * Description: 登录页面
 * Version:
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected ActivityLoginBinding onSetViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

}
