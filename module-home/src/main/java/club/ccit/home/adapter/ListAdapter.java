package club.ccit.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.common.LogUtils;
import club.ccit.home.databinding.ItemDraftsBinding;
import club.ccit.sdk.demo.NewsListBean;

/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class ListAdapter extends BaseAdapter<ItemDraftsBinding> {

    /**
     * 实例化
     * @param list
     */
    public ListAdapter(List list) {
        this.list = list;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    protected void onBindingViewData(RecyclerView.ViewHolder holder, int position) {
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

}
