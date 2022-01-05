package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.drafts.databinding.ItemDraftsBinding;
import club.ccit.sdk.demo.NewsListBean;

/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class DraftsAdapter extends BaseRecyclerViewAdapter<ItemDraftsBinding>{
    private List<NewsListBean.Result> list = new ArrayList<>();

    /**
     * 实例化
     * @param newsListBean
     */
    public DraftsAdapter(NewsListBean newsListBean) {
        this.list = newsListBean.getResult();
    }

    /**
     * 重新加载
     */
    protected void onReload(List<NewsListBean.Result> list) {

    }

    /**
     * 增加数据 刷新
     * @param list
     */
    public void onAppend(List<NewsListBean.Result> list) {
        for (int i = 0; i < list.size(); i++) {
            this.list.add(this.list.size(), list.get(i));
        }
        notifyItemRangeInserted(this.list.size()-list.size(), list.size());
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindingViewData(ViewHolder holder, int position) {
        // 设置文本数据
        binding.titleTextView.setText(list.get(position).getArticle_title());
        binding.timeTextView.setText(list.get(position).getArticle_date());
        binding.contentTextView.setText(list.get(position).getArticle_text());
        binding.authorTextView.setText(list.get(position).getArticle_user()+"/文");
    }

    /**
     * 绑定视图
     * @param parent
     * @return
     */
    @Override
    protected ItemDraftsBinding getViewBinding(ViewGroup parent) {
        return ItemDraftsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }

    /**
     * 设置条目数
     * @return
     */
    @Override
    protected int setItemCount() {
        return list.size();
    }
}
