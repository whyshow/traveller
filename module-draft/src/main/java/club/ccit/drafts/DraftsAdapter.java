package club.ccit.drafts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

    /**
     * 实例化
     * @param list
     * @param page
     */
    public DraftsAdapter(List list,int page) {
        this.list = list;
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
        NewsListBean.Result bean = (NewsListBean.Result) list.get(position);
        // 设置文本数据
        binding.titleTextView.setText(position+1+"  "+bean.getArticle_title());
        binding.timeTextView.setText(bean.getArticle_date());
        binding.contentTextView.setText(bean.getArticle_text());
        binding.authorTextView.setText(bean.getArticle_user()+"/文");
        binding.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i(position+1+"  "+bean.getArticle_title());
            }
        });
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
    public void setAppointAllData(List list, int page) {
        super.setAppointAllData(list, page);
    }

    @Override
    protected TextView setTextViewFooter() {
        return binding.noDataTextView;
    }

    @Override
    protected int setLimit() {
        return 6;
    }
}
