package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import club.ccit.basic.BaseFragment;
import club.ccit.common.RecyclerViewOnScrollListener;
import club.ccit.drafts.databinding.FragmentDraftsBinding;
import club.ccit.sdk.demo.DraftApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.DefaultApiObserver;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:50 上午
 * Description:
 * Version:
 */
public class DraftsFragment extends BaseFragment<FragmentDraftsBinding> {

    private DraftsAdapter adapter;
    private DraftsViewModel draftsViewModel;
    private int page = 1;
    private boolean isLoading = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        binding.draftSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.draftSwipeRefresh.setRefreshing(true);
        if (adapter != null){
            adapter = null;
        }
        initData(page);
        binding.draftSwipeRefresh.setOnRefreshListener(() -> {
            // 下拉刷新， 重新设置请求数据页码、是否上划刷新
            adapter = null;
            page = 1;
            isLoading = true;
            initData(page);
        });

        binding.draftRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoading){
                    if (RecyclerViewOnScrollListener.onScrollListener(recyclerView,newState)){
                        page = page + 1;
                        initData(page);
                    }
                }
            }
        });

    }

    /**
     * 请求网络数据
     */
    private void initData(int page) {
        NewsApi api = new DraftApiProvider().getNewsList();
        AndroidObservable.create(api.getNewsList(page)).with(this).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                Log.i("LOG111",newsListBean.toString());
                // 如果加载的数据小于6条，则不再上划加载更多。
                if (newsListBean.getResult().size() < 6){
                    isLoading = false;
                }
                // 获取网络数据并解析完成
                if (adapter == null){
                    // 创建适配器显示
                    adapter = new DraftsAdapter(newsListBean);
                    binding.draftRecyclerView.setAdapter(adapter);
                }else {
                    adapter.onAppend(newsListBean.getResult());
                }
                binding.draftSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected FragmentDraftsBinding getViewBinding() {
        return FragmentDraftsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
