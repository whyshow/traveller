package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import club.ccit.basic.BaseFragment;
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

    private TestAdapter adapter;
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
        initData();
        binding.draftSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        NewsApi api = new DraftApiProvider(requireActivity()).getNewsList();
        AndroidObservable.create(api.getNewsList()).with(this).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                Log.i("LOG111",newsListBean.toString());
                // 获取网络数据并解析完成
                if (adapter == null){
                    // 创建适配器显示
                    adapter = new TestAdapter(newsListBean);
                    binding.draftRecyclerView.setAdapter(adapter);

                }else {
                    adapter.onReload(newsListBean.getResult());
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
