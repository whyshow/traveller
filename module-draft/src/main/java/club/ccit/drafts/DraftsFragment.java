package club.ccit.drafts;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.basic.BaseFragment;
import club.ccit.common.RecyclerViewOnScrollListener;
import club.ccit.drafts.databinding.FragmentDraftsBinding;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.AbstractApiObserver;

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
    private int pageSize = 6;
    private boolean isLoading = true;
    private NewsApi api;
    private List<NewsListBean.Result> list;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        binding.draftSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        draftsViewModel = new ViewModelProvider(requireActivity()).get(DraftsViewModel.class);
        // 实例化api 请求
        api = new NewsApiProvider().getNewsList();
        // 如果ViewModel中有数据有page 则还原page 数据,否则请求默认的page数据
        if (draftsViewModel.getPage().getValue() != null) {
            page = draftsViewModel.getPage().getValue();
            binding.draftRecyclerView.getLayoutManager().onRestoreInstanceState(draftsViewModel.getState());
        }else {
            binding.draftSwipeRefresh.setRefreshing(true);
            initData(page);
        }
        // 获取到数据并加载显示
        draftsViewModel.getData().observe(getViewLifecycleOwner(), results -> {
            if (results.size() / page != pageSize){
                isLoading = false;
            }
            // 获取网络数据并解析完成
            if (adapter == null) {
                // 使用加数据和下拉加载数据则重新实例化适配器
                adapter = new DraftsAdapter(results);
                binding.draftRecyclerView.setAdapter(adapter);
            } else {
                // 加载请求的分页数据
                adapter.onAppointAllData(results);
            }
        });
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        super.initListener();
        // 下拉刷新， 重新设置请求数据页码、是否上划刷新
        binding.draftSwipeRefresh.setOnRefreshListener(() -> {
            adapter = null;
            page = 1;
            isLoading = true;
            initData(page);
        });

        // 上划加载更多
        binding.draftRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoading) {
                    if (RecyclerViewOnScrollListener.onScrollListener(recyclerView, newState)) {
                        if (adapter != null){
                            page = page + 1;
                            initData(page);
                            adapter.setFooterView(BaseAdapter.TYPE_LOADING_FOOTER);
                        }
                    }
                }
            }
        });
    }

    /**
     * 请求网络数据
     */
    private void initData(int p) {
        AndroidObservable.create(api.getNewsList(p)).with(this).subscribe(new AbstractApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                // 如果加载的数据小于6条，则不再上划加载更多。
                if (newsListBean.getResult().size() < pageSize) {
                    isLoading = false;
                    if (adapter != null){
                        adapter.setFooterView(BaseAdapter.TYPE_NONE_FOOTER);
                    }
                }
                // 设置ViewModel数据
                if (draftsViewModel.getData().getValue() != null) {
                    list = draftsViewModel.getData().getValue();
                } else {
                    list = new ArrayList<>();
                }
                if (p == 1) {
                    list = newsListBean.getResult();
                } else {
                    for (int i = 0; i < newsListBean.getResult().size(); i++) {
                        list.add(newsListBean.getResult().get(i));
                    }
                }
                // 设置 page
                draftsViewModel.setPage(p);
                draftsViewModel.setValue(list);
                binding.draftSwipeRefresh.setRefreshing(false);
            }

            @Override
            protected void error(int code, String message) {
                binding.draftSwipeRefresh.setRefreshing(false);
                // 请求出现错误
                page = page - 1;
                if (adapter != null){
                    adapter.setFooterView(BaseAdapter.TYPE_ERROR_FOOTER);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // 保存RecyclerView数据状态
        draftsViewModel.setState(binding.draftRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected FragmentDraftsBinding getViewBinding() {
        return FragmentDraftsBinding.inflate(getLayoutInflater());
    }
}
