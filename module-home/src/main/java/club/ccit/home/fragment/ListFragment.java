package club.ccit.home.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseFragment;
import club.ccit.basic.action.AdapterAction;
import club.ccit.home.R;
import club.ccit.home.adapter.NewListAdapter;
import club.ccit.home.databinding.FragmentListBinding;
import club.ccit.home.viewModel.ListViewModel;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AbstractApiObserver;
import club.ccit.sdk.net.AndroidObservable;

/**
 * @author: 张帅威
 * Date: 2021/12/3 8:50 上午
 * Description:
 * Version: 1.0 版本
 */
public class ListFragment extends BaseFragment<FragmentListBinding> {
    private NewListAdapter adapter;
    private NewsApi api;
    private int page = 0;
    private ListViewModel viewModel;
    private List<NewsListBean.Result> list = new ArrayList<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        api = new NewsApiProvider().getNewsList();
        binding.draftSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NewListAdapter(new AdapterAction() {
            @Override
            public void onRefresh() {
                requestData();
            }

            @Override
            public void onNext() {
                page = page + 1;
                requestData();
            }
        });
        binding.draftRecyclerView.setAdapter(adapter);
        if (viewModel.getPage().getValue() != null) {
            page = viewModel.getPage().getValue();
            list = viewModel.getData().getValue();
            binding.draftRecyclerView.getLayoutManager().onRestoreInstanceState(viewModel.getState());
        }

        binding.draftSwipeRefresh.setOnRefreshListener(() -> {
            page = 1;
            requestData();
        });

        viewModel.getData().observe(this, new Observer<List<NewsListBean.Result>>() {
            @Override
            public void onChanged(List<NewsListBean.Result> results) {
                binding.draftSwipeRefresh.setRefreshing(false);
                if (results.size() > 0) {
                    adapter.putData(results, page);
                }
            }
        });
    }

    private void requestData() {
        AndroidObservable.create(api.getNewsList2(page)).subscribe(new AbstractApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                // 设置ViewModel数据
                if (newsListBean.getResult().size() > 0) {
                    if (page == 1) {
                        list = newsListBean.getResult();
                    } else {
                        for (int i = 0; i < newsListBean.getResult().size(); i++) {
                            list.add(newsListBean.getResult().get(i));
                        }
                    }
                    viewModel.setValue(list);
                }else {
                    page = page - 1;
                    adapter.setError(1);
                }
            }

            @Override
            protected void error(int code, String message) {
                if (adapter != null) {
                    adapter.setError(2);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // 保存RecyclerView数据状态
        viewModel.setState(binding.draftRecyclerView.getLayoutManager().onSaveInstanceState());
        viewModel.setPage(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null){
            adapter.onDestroy();
        }
    }
}
