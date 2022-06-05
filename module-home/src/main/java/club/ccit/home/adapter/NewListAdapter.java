package club.ccit.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import club.ccit.basic.BaseAdapter;
import club.ccit.basic.action.AdapterAction;
import club.ccit.common.LogUtils;
import club.ccit.home.databinding.ItemHomeBinding;
import club.ccit.sdk.demo.NewsListBean;

/**
 * FileName: NewListAdapter
 *
 * @author: 张帅威
 * Date: 2022/6/3 14:15
 * Description:
 * Version:
 */
public class NewListAdapter extends BaseAdapter<ItemHomeBinding> {

    public NewListAdapter(AdapterAction action) {
        this.action = action;
    }

    @Override
    protected void onBindingViewData(BaseAdapter<ItemHomeBinding>.ViewHolder holder, int position) {
        NewsListBean.Result dataBean = (NewsListBean.Result) list.get(position);
        // 设置文本数据
        LogUtils.i((position + 1 + "  " + dataBean.getArticle_title()));
        LogUtils.i(dataBean.getArticle_text());
        holder.itemBinding.titleTextView.setText(position + 1 + "  " + dataBean.getArticle_title());
        holder.itemBinding.timeTextView.setText(dataBean.getArticle_date());
        if (!dataBean.getArticle_text().isEmpty()) {
            holder.itemBinding.contentTextView.setVisibility(View.VISIBLE);
            holder.itemBinding.contentTextView.setContent(dataBean.getArticle_text());
        } else {
            holder.itemBinding.contentTextView.setVisibility(View.GONE);
            holder.itemBinding.contentTextView.setContent("");
        }
        holder.itemBinding.authorTextView.setText(dataBean.getArticle_user() + "/文");
        holder.itemBinding.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i(position + 1 + "  " + dataBean.getArticle_title());
            }
        });
    }

    @Override
    protected ItemHomeBinding getViewBinding(ViewGroup parent) {
        return ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()));
    }

    public void onDestroy() {
        action = null;
    }
}
