package club.ccit.drafts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.drafts.databinding.ItemTestBinding;
import club.ccit.sdk.draft.NewsListBean;


/**
 * FileName: TestAdapter
 *
 * @author: 张帅威
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class TestAdapter extends BaseRecyclerViewAdapter<ItemTestBinding> {
    private List<NewsListBean.Result> list;
    public TestAdapter(NewsListBean newsListBean) {
        this.list = newsListBean.getResult();
    }

    /**
     * 绑定数据
     * @param position
     */
    @Override
    protected void onBindingViewData(int position) {
        binding.titleTextView.setText(list.get(position).getArticle_title());
        binding.timeTextView.setText(list.get(position).getArticle_date());
        binding.contentTextView.setText(list.get(position).getArticle_text());
        binding.authorTextView.setText(list.get(position).getArticle_user()+"/文");
    }

    @Override
    protected ItemTestBinding getViewBinding(ViewGroup parent) {
        return ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }

    @Override
    protected int setItemCount() {
        return list.size();
    }


}
