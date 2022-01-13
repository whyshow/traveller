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
public abstract class BaseRecyclerViewAdapter <T extends ViewBinding> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    protected T binding;
    /**
     * 当前页码
     */
    private int page;
    private int nowCount = 0;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = getViewBinding(parent);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        onBindingViewData(holder,position);
        if (setTextViewFooter() != null || setItemCount() != 0){
            if (setItemCount() == position + 1){
                setTextViewFooter().setVisibility(View.VISIBLE);
            }else {
                setTextViewFooter().setVisibility(View.GONE);
            }
            if (page == 0){
                if (setItemCount() / setPage() < setLimit()){
                    setTextViewFooter().setText(AdapterType.COMPLETE);
                }else {
                    setTextViewFooter().setText(AdapterType.MESSAGE);
                }
            }else {
                if (setItemCount() / page < setLimit()){
                    setTextViewFooter().setText(AdapterType.COMPLETE);
                }else {
                    setTextViewFooter().setText(AdapterType.MESSAGE);
                }
            }
        }
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

    /**
     * 获取item 数量
     * @return
     */
    @Override
    public int getItemCount() {
        return setItemCount();
    }

    /**
     * 绑定数据
     * @param holder
     * @param position 绑定
     */
    protected abstract void onBindingViewData(ViewHolder holder, int position);

    /**
     * 绑定视图
     * ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
     * @return 实现
     * @param parent 。
     */
    protected abstract T getViewBinding(ViewGroup parent);

    /**
     * 获取item数量
     * @return 数量
     */
    protected abstract int setItemCount();

    /**
     * 获取item 类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 获取 Footer的TextView 控件
     * @return
     */
    protected abstract TextView setTextViewFooter();

    /**
     * 获取当前页码
     * @return
     */
    protected abstract int setPage();
    /**
     * 设置每页数目的最大数
     * @return
     */
    protected abstract int setLimit();

    /**
     * 设置错误的Footer
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setErrorFooter() {
        AdapterType.MESSAGE = AdapterType.ERROR;
        notifyDataSetChanged();
    }

    /**
     * 设置正在加载的Footer
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setLoadingFooter() {
        AdapterType.MESSAGE = AdapterType.LOADING;
        notifyDataSetChanged();
    }

    /**
     * 添加item数据
     * @param list 已经添加过数据列表
     * @param page 当前页码
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onAppointReload(List list, int page){
        Log.i("LOG111","onAppointReload list.size() = " +list.size());
        this.page = page;
        int location = list.size() - nowCount;
        notifyItemRangeChanged(list.size() - location,location);
        notifyDataSetChanged();
        nowCount = list.size();
    }

}
