package club.ccit.home.viewModel;

import android.os.Parcelable;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import club.ccit.sdk.demo.NewsListBean;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

/**
 * FileName: NewsViewMOdel
 *
 * @author: 张帅威
 * Date: 2022/5/17 15:31
 * Description:
 * Version:
 */
public class NewsViewModel extends ViewModel {
    private Parcelable state;
    private Pager<Integer, NewsListBean.Result> pager;
    private Flowable<PagingData<NewsListBean.Result>> flowable;
    private CoroutineScope viewModelScope;

    public Flowable<PagingData<NewsListBean.Result>> getPassengers(int pageSize) {
        if (pager == null) {
            viewModelScope = ViewModelKt.getViewModelScope(this);
            pager = new Pager<>(new PagingConfig(pageSize, pageSize), 1, LiveDataSource::new);
            flowable = PagingRx.getFlowable(pager);
        }
        return PagingRx.cachedIn(flowable, viewModelScope);
    }

    public Parcelable getState() {
        return state;
    }

    public void setState(Parcelable state) {
        this.state = state;
    }

}
