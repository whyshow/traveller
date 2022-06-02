package club.ccit.home;

import static club.ccit.common.AppRouter.PATH_HOME_HOME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import club.ccit.basic.BaseActivity;
import club.ccit.home.adapter.FooterLoadStateAdapter;
import club.ccit.home.adapter.HomeAdapter;
import club.ccit.home.databinding.ActivityHomeBinding;
import club.ccit.home.viewModel.NewsViewModel;
import club.ccit.sdk.demo.NewsListBean;
import io.reactivex.rxjava3.subscribers.DefaultSubscriber;

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
    private FooterLoadStateAdapter footerLoadStateAdapter;

    private final int pageSize = 6;
    private NewsViewModel viewModel;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.homeSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        binding.homeSwipeRefresh.setRefreshing(true);
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        adapter = new HomeAdapter(new DiffUtil.ItemCallback<NewsListBean.Result>() {
            @Override
            public boolean areItemsTheSame(@NonNull NewsListBean.Result oldItem, @NonNull NewsListBean.Result newItem) {
                return oldItem.getArticle_id().equals(newItem.getArticle_id());
            }

            @Override
            public boolean areContentsTheSame(@NonNull NewsListBean.Result oldItem, @NonNull NewsListBean.Result newItem) {
                return oldItem.getArticle_title().equals(newItem.getArticle_title());
            }
        });

        footerLoadStateAdapter = new FooterLoadStateAdapter(v -> adapter.retry());

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.homeRecyclerView.setAdapter(adapter.withLoadStateFooter(footerLoadStateAdapter));

        viewModel.getPassengers(pageSize).subscribe(new DefaultSubscriber<PagingData<NewsListBean.Result>>() {
            @Override
            public void onNext(PagingData<NewsListBean.Result> resultPagingData) {
                binding.homeSwipeRefresh.setRefreshing(false);
                adapter.submitData(getLifecycle(), resultPagingData);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        binding.homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });
    }

    public static void launch(Activity activity, String toJson) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra("data", toJson);
        activity.startActivity(intent);
    }
}
