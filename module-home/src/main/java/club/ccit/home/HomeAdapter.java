package club.ccit.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import club.ccit.basic.BaseRecyclerViewAdapter;
import club.ccit.basic.holder.BaseHolder;
import club.ccit.common.LogUtils;
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

    /**
     * 实例化
     * @param list
     */
    public HomeAdapter(List list) {
        this.data = list;
    }

    @Override
    protected void onBindingData(BaseHolder<ItemHomeBinding> holder, Object data, int position) {
        NewsListBean.Result bean = (NewsListBean.Result) data;
        LogUtils.i(position+1+"  "+bean.getArticle_title());
        LogUtils.i("正文："+bean.getArticle_text());
        LogUtils.i("控件："+holder.getViewBinding().titleTextView);
        // 设置文本数据
        holder.getViewBinding().titleTextView.setText(position+1+"  "+bean.getArticle_title());
        holder.getViewBinding().timeTextView.setText(bean.getArticle_date());
        if (!bean.getArticle_text().isEmpty()){
            holder.getViewBinding().contentTextView.setContent(bean.getArticle_text());
            LogUtils.i("不是空");
        }else {
            holder.getViewBinding().contentTextView.setContent("");
        }

        LogUtils.i("内容："+holder.getViewBinding().contentTextView.getText());
        holder.getViewBinding().authorTextView.setText(bean.getArticle_user()+"/文");
        holder.getViewBinding().itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.i(position+1+"  "+bean.getArticle_title());
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 绑定视图
     * @param viewGroup
     * @return
     */
    @Override
    protected ItemHomeBinding onBindingView(ViewGroup viewGroup) {
       return ItemHomeBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
    }
}
