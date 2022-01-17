package club.ccit.drafts;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import club.ccit.sdk.demo.NewsListBean;

/**
 * FileName: DraftsViewModel
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/21 2:28 下午
 * Description:
 * Version:
 */
public class DraftsViewModel extends ViewModel {
    private final MutableLiveData<List<NewsListBean.Result>> data;
    private MutableLiveData<Integer> page;
    private Parcelable state;

    public DraftsViewModel() {
        data = new MutableLiveData<>();
        page = new MutableLiveData<>();
    }

    public Parcelable getState() {
        return state;
    }

    public void setState(Parcelable state) {
        this.state = state;
    }

    /**
     * 获取页面
     * @return
     */
    public LiveData<Integer> getPage() {
        return page;
    }

    /**
     * 设置页面
     * @param p
     */
    public void setPage(Integer p) {
        page.setValue(p);
    }

    /**
     * 获取数据
     * @return
     */
    public LiveData<List<NewsListBean.Result>> getData() {
        return data;
    }

    /**
     * 设置数据
     * @param value
     */
    public void setValue(List<NewsListBean.Result> value){
        data.setValue(value);
    }
}
