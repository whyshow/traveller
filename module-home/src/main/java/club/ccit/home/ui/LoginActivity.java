package club.ccit.home.ui;

import android.view.View;

import club.ccit.basic.BaseActivity;
import club.ccit.home.R;
import club.ccit.home.databinding.ActivityLoginBinding;
import club.ccit.shared.user.UserPreferenceManager;
import club.ccit.shared.user.model.UserPreferencesModel;

/**
 * FileName: LoginActivity
 *
 * @author: 张帅威
 * Date: 2021/12/24 9:16 上午
 * Description: 登录页面
 * Version:
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private UserPreferencesModel userPreferencesModel;

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickListener(binding.loginButton);
    }


    @Override
    protected ActivityLoginBinding onSetViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.loginButton) {
            if (binding.phoneEditText.getText().toString().trim().isEmpty()) {
                showToast("请输入手机号");
                return;
            }
            if (binding.passwordEditText.getText().toString().trim().isEmpty()) {
                showToast("请输入密码");
                return;
            }

            userPreferencesModel = UserPreferenceManager.getInstance().initPreferences(this);
            userPreferencesModel.putUserPhone(binding.phoneEditText.getText().toString().trim());
            userPreferencesModel.putUserPassword(binding.passwordEditText.getText().toString().trim());
            showToast("登录成功，您的信息已保存在本地!");
            finish();
        }
    }
}
