package club.ccit.home.ui;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import club.ccit.basic.BaseViewDataActivity;
import club.ccit.common.LogUtils;
import club.ccit.home.R;
import club.ccit.home.databinding.ActivityLoginBinding;
import club.ccit.home.viewModel.LoginViewModel;
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
public class LoginActivity extends BaseViewDataActivity<ActivityLoginBinding> {
    private UserPreferencesModel userPreferencesModel;
    private LoginViewModel viewModel;

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setOnClickListener(binding.buttonLogin);
        userPreferencesModel = UserPreferenceManager.getInstance().initPreferences(this);
        if (!userPreferencesModel.getUserPhone().isEmpty()) {
            binding.editTextUsername.setText(userPreferencesModel.getUserPhone());
        }
        if (!userPreferencesModel.getUserPassword().isEmpty()) {
            binding.editTextPassword.setText(userPreferencesModel.getUserPassword());
        }
        setObserve();
        viewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!viewModel.password.getValue().isEmpty() && !s.isEmpty()) {
                    viewModel.buttonVisibility.setValue(true);
                    LogUtils.i("buttonVisibility true");
                } else {
                    viewModel.buttonVisibility.setValue(false);
                    LogUtils.i("buttonVisibility false");
                }
            }
        });

        viewModel.password.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!viewModel.name.getValue().isEmpty() && !s.isEmpty()) {
                    viewModel.buttonVisibility.setValue(true);
                    LogUtils.i("buttonVisibility true");
                } else {
                    viewModel.buttonVisibility.setValue(false);
                    LogUtils.i("buttonVisibility false");
                }
            }
        });
    }

    private void setObserve() {
        viewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });

        viewModel.ok.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    userPreferencesModel.putUserPhone(binding.editTextUsername.getText().toString().trim());
                    userPreferencesModel.putUserPassword(binding.editTextPassword.getText().toString().trim());
                    showToast("登录成功，您的信息已保存在本地!");
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.buttonLogin) {
            viewModel.login();
        }
    }
}
