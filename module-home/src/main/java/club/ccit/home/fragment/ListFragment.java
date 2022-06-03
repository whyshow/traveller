package club.ccit.home.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseFragment;
import club.ccit.home.R;
import club.ccit.home.adapter.ListAdapter;
import club.ccit.home.databinding.FragmentListBinding;
import club.ccit.home.viewModel.NewsViewModel;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AbstractApiObserver;
import club.ccit.sdk.net.AndroidObservable;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:50 上午
 * Description:
 * Version:
 */
public class ListFragment extends BaseFragment<FragmentListBinding> {
    private ListAdapter adapter;
    private NewsApi api;
    private int page = 1;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate() {
        super.onCreate();
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        api = new NewsApiProvider().getNewsList();
        List list = new ArrayList();
        list.add("");
        adapter = new ListAdapter(ListFragment.this, list);
        binding.draftSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.draftRecyclerView.setAdapter(adapter);
        requestData();
        binding.draftSwipeRefresh.setOnRefreshListener(() -> refresh());
    }

    public void refresh() {
        page = 1;
        requestData();
    }

    public void onNext() {
        page = page + 1;
        requestData();
    }

    private void requestData() {
        AndroidObservable.create(api.getNewsList2(page)).subscribe(new AbstractApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                binding.draftSwipeRefresh.setRefreshing(false);
                if (newsListBean.getResult().size() > 0) {
                    adapter.onAppointData(newsListBean.getResult(), page);
                } else {
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
    }

    @Override
    protected FragmentListBinding onSetViewBinding() {
        return FragmentListBinding.inflate(getLayoutInflater());
    }
}
