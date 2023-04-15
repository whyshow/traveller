package club.ccit.basic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * FileName: BaseViewModel
 *
 * @author: 张帅威
 * Date: 2022/1/12 3:49 下午
 * Description: ViewModel 基类
 * Version:
 */
public class BaseViewModel extends ViewModel {
    public MutableLiveData<String> message = new MutableLiveData<>();
    public MutableLiveData<Boolean> ok = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
