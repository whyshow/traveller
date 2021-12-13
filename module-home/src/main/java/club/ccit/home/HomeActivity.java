package club.ccit.home;

import static club.ccit.common.AppRouter.PATH_HOME_HOME;
import android.annotation.SuppressLint;
import android.util.Log;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.android.arouter.facade.annotation.Route;

import club.ccit.basic.BaseActivity;
import club.ccit.home.databinding.ActivityHomeBinding;
import club.ccit.sdk.demo.AggregatePageBean;
import club.ccit.sdk.demo.CategoryApi;
import club.ccit.sdk.demo.CategoryApiProvider;
import club.ccit.sdk.demo.DraftApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.DefaultApiObserver;

/**
 * FileName: HomeActivity
 *
 * @author: 张帅威
 * Date: 2021/12/13 8:23 上午
 * Description:
 * Version:
 */
@Route(path = PATH_HOME_HOME)
public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    private TestAdapter adapter;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        binding.homeSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.homeSwipeRefresh.setRefreshing(true);
        CategoryApi api = new CategoryApiProvider(this).getAggregatePage();
        AndroidObservable.create(api.getAggregatePage()).with(this).subscribe(new DefaultApiObserver<AggregatePageBean>() {
            @Override
            protected void succeed(AggregatePageBean aggregatePageBean) {
                Log.i("LOG111",aggregatePageBean.toString());
            }
        });

        initData();
        binding.homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    /**
     * 请求网络数据
     */
    private void initData() {
        NewsApi api = new DraftApiProvider(this).getNewsList();
        AndroidObservable.create(api.getNewsList()).with(this).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                Log.i("LOG111",newsListBean.toString());

                // 获取网络数据并解析完成
                if (adapter == null){
                    // 创建适配器显示
                    adapter = new TestAdapter(newsListBean);
                    binding.homeRecyclerView.setAdapter(adapter);
                }else {
                    adapter.onReload(newsListBean.getResult());
                }
                binding.homeSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int setImmersionBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return true;
    }
}
