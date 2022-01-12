package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseFragment;
import club.ccit.common.LogUtils;
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
    private Parcelable state;
    private NewsApi api;
    private List<NewsListBean.Result> list;

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        binding.draftSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.draftSwipeRefresh.setRefreshing(true);
        draftsViewModel = new ViewModelProvider(requireActivity()).get(DraftsViewModel.class);
        api = new DraftApiProvider().getNewsList();
        // 还原page
        if (draftsViewModel.getPage().getValue() != null) {
            page = draftsViewModel.getPage().getValue();
        }else {
            initData(page);
        }
        draftsViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<NewsListBean.Result>>() {
            @Override
            public void onChanged(List<NewsListBean.Result> results) {
                // 如果加载的数据小于6条，则不再上划加载更多。
                if (results.size() / page < 6) {
                    isLoading = false;
                }
                // 获取网络数据并解析完成
                if (adapter == null) {
                    // 创建适配器显示
                    adapter = new DraftsAdapter(results,page);
                    binding.draftRecyclerView.setAdapter(adapter);
                } else {
                    adapter.onAppointReload(results,page);
                }
                binding.draftSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
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
                if (isLoading) {
                    if (RecyclerViewOnScrollListener.onScrollListener(recyclerView, newState)) {
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

        AndroidObservable.create(api.getNewsList(page)).with(this).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                // 设置ViewModel数据
                if (draftsViewModel.getData().getValue() != null) {
                    list = draftsViewModel.getData().getValue();
                } else {
                    list = new ArrayList<>();
                }
                if (page == 1) {
                    list = newsListBean.getResult();
                } else {
                    for (int i = 0; i < newsListBean.getResult().size(); i++) {
                        list.add(newsListBean.getResult().get(i));
                    }
                }
                // 设置 page
                draftsViewModel.setPage(page);
                draftsViewModel.setValue(list);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause()");
        state = binding.draftRecyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("onResume()");
        binding.draftRecyclerView.getLayoutManager().onRestoreInstanceState(state);
    }

    @Override
    protected FragmentDraftsBinding getViewBinding() {
        return FragmentDraftsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i("onViewCreated()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i("onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy()");
    }
}
