package club.ccit.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.common.LogUtils;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.user.databinding.ItemMyBinding;


/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class MyAdapter extends BaseAdapter<ItemMyBinding> {

    /**
     * 实例化
     * @param list
     */
    public MyAdapter(List list) {
        this.list = list;
    }

    /**
     * 绑定数据
     * @param position
     */
    @Override
    protected void onBindingViewData(int position) {
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
    protected ItemMyBinding getViewBinding(ViewGroup parent) {
        return ItemMyBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }

}
