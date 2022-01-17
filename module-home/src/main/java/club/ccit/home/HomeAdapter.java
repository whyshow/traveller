package club.ccit.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import club.ccit.basic.BaseAdapter;
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
public class HomeAdapter extends BaseAdapter<ItemHomeBinding> {

    /**
     * 实例化
     * @param list
     */
    public HomeAdapter(List list) {
        this.list = list;
    }


    @Override
    protected void onBindingViewData(int position) {
        NewsListBean.Result bean = (NewsListBean.Result) list.get(position);
        Log.i("LOG111","position: "+(position+1)+"  title:  "+bean.getArticle_title());
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

    @Override
    public void onAppointData(List list, int page) {
        super.onAppointData(list, page);
    }

    @Override
    public void setFooterView(int type) {
        super.setFooterView(type);
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
}
