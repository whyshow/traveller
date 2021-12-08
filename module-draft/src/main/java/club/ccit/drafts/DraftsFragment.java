package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import club.ccit.basic.BaseFragment;
import club.ccit.drafts.databinding.FragmentDraftsBinding;
import club.ccit.sdk.demo.DraftApiProvider;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.ApiDefaultObserver;

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
        binding.draftSwipeRefresh.setRefreshing(true);
        // 请求网络数据
        NewsApi api = new DraftApiProvider(requireActivity()).getNewsList();
        AndroidObservable.create(api.getNewsList()).with(this).subscribe(new ApiDefaultObserver<NewsListBean>() {
            @Override
            protected void accept(NewsListBean newsListBean) {
                // 获取网络数据并解析完成
                if (adapter == null){
                    // 创建适配器显示
                    adapter = new TestAdapter(newsListBean);
                    binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    binding.draftRecyclerView.setAdapter(adapter);
                    binding.draftSwipeRefresh.setRefreshing(false);
                }

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
        Log.i("LOG111","onDestroy()");
    }
}
