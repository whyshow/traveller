package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
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
    private List<NewsListBean.Result> list;
    private int page;
    /**
     * 实例化
     * @param results
     * @param page
     */
    public DraftsAdapter(List<NewsListBean.Result> results,int page) {
        this.list = results;
        this.page = page;
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
        binding.titleTextView.setText(position+1+"  "+list.get(position).getArticle_title());
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

    /**
     * 添加数据
     * @param list 已经添加过数据列表
     * @param page 当前页码
     */
    @Override
    public void onAppointReload(List list, int page) {
        super.onAppointReload(list, page);
    }

    @Override
    protected TextView setTextViewFooter() {
        return binding.noDataTextView;
    }

    @Override
    protected int setPage() {
        return page;
    }

    @Override
    protected int setLimit() {
        return 6;
    }
}
