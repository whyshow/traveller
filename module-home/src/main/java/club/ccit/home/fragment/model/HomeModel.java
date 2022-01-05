package club.ccit.home.fragment.model;

import android.util.Log;

import club.ccit.home.HomeActivity;
import club.ccit.home.fragment.contract.HomeContract;
import club.ccit.sdk.demo.DraftApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.DefaultApiObserver;

/**
 * FileName: HomeModel
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/20 8:40 上午
 * Description:
 * Version:
 */
public class HomeModel implements HomeContract.Model {
    private HomeContract.Presenter presenter;
    private HomeActivity activity;
    public HomeModel(HomeActivity activity, HomeContract.Presenter presenter) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    public void requestData(Object object) {
        int page = (int) object;
        NewsApi api = new DraftApiProvider().getNewsList();
        AndroidObservable.create(api.getNewsList(page)).with(activity).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                Log.i("LOG111",newsListBean.toString());
                presenter.requestSuccess(newsListBean);
            }
        });
    }

    @Override
    public void onDestroy() {
        activity = null;
        presenter = null;
    }
}
