package club.ccit;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseActivity;
import club.ccit.databinding.ActivityLoginBinding;
import club.ccit.home.fragment.WebBannerAdapter;
import club.ccit.widget.banner.BannerLayout;

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
