package club.ccit.room.viewModel;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import club.ccit.common.Constant;
import club.ccit.room.AbstractRoomData;
import club.ccit.room.api.TestApi;
import club.ccit.room.model.TestDataModel;

/**
 * FileName: RoomViewModel
 *
 * @author: 张帅威
 * Date: 2022/2/24 9:12 上午
 * Description:
 * Version:
 */
public class RoomViewModel extends ViewModel {
    /**
     * 名字
     */
    public ObservableField<String> name = new ObservableField<>();
    /**
     * 年龄
     */
    public ObservableField<String> age = new ObservableField<>();
    public MutableLiveData<Boolean> success = new MutableLiveData<>();

    /**
     * 添加数据
     */
    @SuppressLint("CheckResult")
    public void addData() {
        if (name.get() == null) {
            success.setValue(false);
        } else if (age.get() == null) {
            success.setValue(false);
        } else {
            TestApi testApi = AbstractRoomData.getRoomDataBase(Constant.getApplication()).getTestApi();
            TestDataModel dataModel = new TestDataModel();
            dataModel.setTestAge(age.get());
            dataModel.setTestName(name.get());
            testApi.insertData(dataModel);
            success.setValue(true);
        }
    }
}
