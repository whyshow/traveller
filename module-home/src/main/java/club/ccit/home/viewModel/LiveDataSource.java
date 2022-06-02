package club.ccit.home.viewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsListBean;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * FileName: LiveDataSource
 *
 * @author: 张帅威
 * Date: 2022/5/5 10:36
 * Description:
 * Version:
 */
public class LiveDataSource extends RxPagingSource<Integer, NewsListBean.Result> {

    @NotNull
    @Override
    public Single<LoadResult<Integer,NewsListBean.Result>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        // 当前页码
        Integer page = loadParams.getKey();
        if (page == null) {
            page = 1;
        }
        Integer finalPage = page;
        NewsApi api = new NewsApiProvider().getNewsList();
        return api.getNewsList(page)
                .subscribeOn(Schedulers.io())
                .map(NewsListBean::getResult)
                .map(data -> toLoadResult(data, finalPage))
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, NewsListBean.Result> toLoadResult(@NonNull List<NewsListBean.Result> passengers , Integer page) {
            return new LoadResult.Page<>(
                    passengers,
                    page.equals(1) ? null: page - 1,
                    passengers.size() == 6 ? page + 1 : null,
                    LoadResult.Page.COUNT_UNDEFINED,
                    LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, NewsListBean.Result> pagingState) {
        return null;
    }
}
