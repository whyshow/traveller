package club.ccit.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.home.databinding.ItemTestBinding;
import club.ccit.sdk.demo.NewsListBean;

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

    /**
     * 实例化
     * @param newsListBean
     */
    public TestAdapter(NewsListBean newsListBean) {
        this.list = newsListBean.getResult();
    }

    /**
     * 重新加载
     */
    public void onReload(List<NewsListBean.Result> list) {

    }

    /**
     * 绑定数据
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindingViewData(int position) {
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
    protected ItemTestBinding getViewBinding(ViewGroup parent) {
        return ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
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
