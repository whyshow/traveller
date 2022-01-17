package club.ccit.basic;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:03 下午
 * Description: RecyclerViewAdapter封装基类
 * Version:
 */
public abstract class BaseRecyclerViewAdapter<T extends ViewBinding> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    protected T binding;
    public List list;

    /**
     * 当前页码
     */
    public int page;
    private int nowCount = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG111","viewType: "+viewType);
        binding = getViewBinding(parent);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        onBindingViewData(holder, position);
    }

    /**
     * 绑定控件
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull T itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * 获取item 数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return setItemCount();
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position 绑定
     */
    protected abstract void onBindingViewData(ViewHolder holder, int position);

    /**
     * 绑定视图
     * ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
     *
     * @param parent 。
     * @return 实现
     */
    protected abstract T getViewBinding(ViewGroup parent);

    /**
     * 获取item数量
     *
     * @return 数量
     */
    protected abstract int setItemCount();

    /**
     * 获取item 类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 获取 Footer的TextView 控件
     *
     * @return
     */
    protected abstract TextView setTextViewFooter();

    /**
     * 设置每页数目的最大数
     *
     * @return
     */
    protected abstract int setLimit();

    /**
     * 设置加载全部的Footer
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setLoadingALLFooter() {
        if (setTextViewFooter() != null){
            AdapterType.MESSAGE = AdapterType.COMPLETE;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置错误的Footer
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setErrorFooter() {
        if (setTextViewFooter() != null){
            AdapterType.MESSAGE = AdapterType.ERROR;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置正在加载的Footer
     * super.setLoadingFooter();
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setLoadingFooter() {
        if (setTextViewFooter() != null){
            AdapterType.MESSAGE = AdapterType.LOADING;
            notifyDataSetChanged();
        }
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     * @param page 当前页码
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setAppointData(List list, int page) {
        this.page = page;
        for (int i = 0; i < list.size(); i++) {
            this.list.add(this.list.size(), list.get(i));
        }
        notifyItemRangeInserted(this.list.size() - list.size(), list.size());
        notifyDataSetChanged();
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     * @param page 当前页码
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setAppointAllData(List list, int page) {
        this.page = page;
        this.list = list;
        int location = list.size() - nowCount;
        notifyItemRangeChanged(list.size() - location, location);
        notifyDataSetChanged();
        nowCount = list.size();
    }


}
