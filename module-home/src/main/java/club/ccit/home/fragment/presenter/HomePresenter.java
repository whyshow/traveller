package club.ccit.home.fragment.presenter;

import club.ccit.home.HomeActivity;
import club.ccit.home.fragment.contract.HomeContract;
import club.ccit.home.fragment.model.HomeModel;

/**
 * FileName: HomePresenter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/20 8:40 上午
 * Description:
 * Version:
 */
public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View view;
    private HomeModel homeModel;

    public HomePresenter(HomeActivity activity) {
        this.view = activity;
        homeModel = new HomeModel(activity,this);
    }

    @Override
    public void requestData(Object object) {
        homeModel.requestData(object);
    }

    @Override
    public void requestSuccess(Object object) {
        view.requestSuccess(object);
    }

    @Override
    public void requestFailure(String msg) {
        view.requestFailure(msg);
    }

    @Override
    public void onDestroy() {
        homeModel.onDestroy();
        view = null;
        homeModel = null;
    }
}
