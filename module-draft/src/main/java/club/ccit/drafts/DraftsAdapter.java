package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.common.LogUtils;
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
    private int page;
    /**
     * 实例化
     * @param results
     * @param results
     */
    public DraftsAdapter(List<NewsListBean.Result> results,int page) {
        this.list = results;
        this.page = page;
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
    public void onAppend(List<NewsListBean.Result> list,int page) {
        this.page = page;
        for (int i = 0; i < list.size(); i++) {
            this.list.add(this.list.size(), list.get(i));
        }
        notifyItemRangeInserted(this.list.size()-list.size(), list.size());
    }

    /**
     * 指定重新加载
     */
    protected void onAppointReload(List<NewsListBean.Result> list,int page) {
        this.page = page;
        int now = this.list.size();
        this.list = list;
        int location = this.list.size() - now;
        notifyItemRangeChanged(this.list.size() - location,location);
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindingViewData(ViewHolder holder, int position) {
        if (list.size() == position + 1){
            binding.noDataTextView.setVisibility(View.VISIBLE);
        }else {
            binding.noDataTextView.setVisibility(View.GONE);
        }
        if (list.size() / page < 6){
            binding.noDataTextView.setText("没有更多了");
        }
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
