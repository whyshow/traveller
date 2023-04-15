package club.ccit.home.viewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import club.ccit.basic.BaseViewModel;

/**
 * FileName: LoginViewModel
 *
 * @author: mosaic
 * Date: 2023/4/10 10:24
 * Description:
 * Version:
 */
public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> buttonVisibility = new MutableLiveData<>();

    /**
     * 名字
     */
    public MutableLiveData<String> name = new MutableLiveData<>();
    /**
     * 密码
     */
    public MutableLiveData<String> password = new MutableLiveData<>();

    public LoginViewModel() {

    }

    public void login(){
        if (Objects.requireNonNull(name.getValue()).isEmpty()) {
            ok.setValue(false);
            message.setValue("请输入手机号");
            return;
        }
        if (Objects.requireNonNull(password.getValue()).isEmpty()) {
            ok.setValue(false);
            message.setValue("请输密码");
            return;
        }
        ok.setValue(true);
    }


}
