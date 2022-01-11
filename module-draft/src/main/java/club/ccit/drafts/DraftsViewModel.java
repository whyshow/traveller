package club.ccit.drafts;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import club.ccit.sdk.demo.DraftApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.DefaultApiObserver;

/**
 * FileName: DraftsViewModel
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/21 2:28 下午
 * Description:
 * Version:
 */
public class DraftsViewModel extends ViewModel {
    private MutableLiveData<NewsListBean> newsListBeanMutableLiveData;

    public DraftsViewModel() {
        newsListBeanMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<NewsListBean> getNewsListBeanMutableLiveData() {
        return newsListBeanMutableLiveData;
    }

    public void getNewsListData(Activity activity, int p){
        if (newsListBeanMutableLiveData.getValue() == null){
            NewsApi api = new DraftApiProvider().getNewsList();
            AndroidObservable.create(api.getNewsList(p)).with((LifecycleOwner) activity).subscribe(new DefaultApiObserver<NewsListBean>() {
                @Override
                protected void succeed(NewsListBean newsListBean) {
                    newsListBeanMutableLiveData.setValue(newsListBean);
                }
            });
        }
    }
}
