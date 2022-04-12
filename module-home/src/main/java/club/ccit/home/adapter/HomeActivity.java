package club.ccit.home.adapter;

import static club.ccit.basic.BaseAdapter.TYPE_ERROR_FOOTER;
import static club.ccit.basic.BaseAdapter.TYPE_LOADING_FOOTER;
import static club.ccit.basic.BaseAdapter.TYPE_NONE_FOOTER;
import static club.ccit.common.AppRouter.PATH_HOME_HOME;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;

import club.ccit.basic.BaseActivity;
import club.ccit.common.LogUtils;
import club.ccit.common.RecyclerViewOnScrollListener;
import club.ccit.home.HomeAdapter;
import club.ccit.home.R;
import club.ccit.home.databinding.ActivityHomeBinding;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.AbstractApiObserver;

/**
 * FileName: HomeActivity
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/13 8:23 上午
 * Description:
 * Version:
 */
@Route(path = PATH_HOME_HOME)
public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    private HomeAdapter adapter;
    private int page = 1;
    private final int pageSize = 6;
    private boolean isLoading = true;
    private boolean isReload = false;
    private NewsApi api;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        binding.homeSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.homeSwipeRefresh.setRefreshing(true);
        adapter = new HomeAdapter(new ArrayList());
        binding.homeRecyclerView.setAdapter(adapter);
        api = new NewsApiProvider().getNewsList();
        initData(page);
        LogUtils.i("接收到："+getIntent().getStringExtra("data"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public static void launch(Activity activity, String toJson) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra("data", toJson);
        activity.startActivity(intent);
    }

    /**
     * 请求网络数据
     */
    private void initData(int p) {
        AndroidObservable.create(api.getNewsList(p)).subscribe(new AbstractApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                if (newsListBean.getResult().size() < pageSize){
                    isLoading = false;
                   // adapter.setFooterView(TYPE_NONE_FOOTER);
                }
                if (isReload){
                    isReload = false;
                    adapter.onReload(newsListBean.getResult());
                }else {
                    adapter.onAppointData(newsListBean.getResult());
                }

                binding.homeSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void error(int code, String message) {
                page = page - 1;
                binding.homeSwipeRefresh.setRefreshing(false);
                Log.i("LOG111",message);
               // adapter.setFooterView(TYPE_ERROR_FOOTER);
            }
        });
    }


    protected void initView() {
        binding.homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isReload = true;
                isLoading = true;
                initData(page);
            }
        });

        binding.homeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoading){
                    if (RecyclerViewOnScrollListener.onScrollListener(recyclerView,newState)){
                      //  adapter.setFooterView(TYPE_LOADING_FOOTER);
                        page = page + 1;
                        initData(page);
                    }
                }

            }
        });
    }

}
