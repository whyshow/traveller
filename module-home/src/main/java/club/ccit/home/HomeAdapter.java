package club.ccit.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.home.databinding.ItemHomeBinding;
import club.ccit.sdk.demo.NewsListBean;

/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<ItemHomeBinding> {
    private List<NewsListBean.Result> list;

    /**
     * 实例化
     * @param newsListBean
     */
    public HomeAdapter(NewsListBean newsListBean) {
        this.list = newsListBean.getResult();
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
    protected ItemHomeBinding getViewBinding(ViewGroup parent) {
        return ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }

    /**
     * 设置条目数
     * @return
     */
    @Override
    protected int setItemCount() {
        return list.size();
    }

    @Override
    protected TextView setTextViewFooter() {
        return null;
    }

    @Override
    protected int setPage() {
        return 0;
    }

    @Override
    protected int setLimit() {
        return 6;
    }
}
