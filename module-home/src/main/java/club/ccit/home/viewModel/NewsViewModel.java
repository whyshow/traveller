package club.ccit.home.viewModel;

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
   public Flowable<PagingData<NewsListBean.Result>> getPassengers(int pageSize) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
       LiveDataSource liveDataSource = new LiveDataSource();
        Pager<Integer, NewsListBean.Result> pager = new Pager<>(new PagingConfig(pageSize,pageSize ), 1,LiveDataSource :: new );

        Flowable<PagingData<NewsListBean.Result>> flowable = PagingRx.getFlowable(pager);
        return PagingRx.cachedIn(flowable, viewModelScope);
    }
}
