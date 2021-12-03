package club.ccit.drafts;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import club.ccit.basic.BaseFragment;
import club.ccit.drafts.databinding.FragmentDraftsBinding;
import club.ccit.sdk.draft.DraftApiProvider;
import club.ccit.sdk.draft.NewsApi;
import club.ccit.sdk.draft.NewsListBean;
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

        if (savedInstanceState == null){
            NewsApi api = new DraftApiProvider(requireActivity()).getNewsList();
            AndroidObservable.create(api.getNewsList()).with(this).subscribe(new ApiDefaultObserver<NewsListBean>() {
                @Override
                protected void accept(NewsListBean newsListBean) {
                    if (adapter == null){
                        adapter = new TestAdapter(newsListBean);
                        binding.draftRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        binding.draftRecyclerView.setAdapter(adapter);
                    }

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LOG111","onStart()");
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
