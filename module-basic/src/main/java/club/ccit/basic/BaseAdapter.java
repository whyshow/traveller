package club.ccit.basic;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * FileName: BaseAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/17 9:12 上午
 * Description:
 * Version:
 */
public abstract class BaseAdapter<T extends ViewBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected T binding;
    public static final int TYPE_FOOTER = -1;
    public static final int TYPE_ERROR_FOOTER = -2;
    public static final int TYPE_LOADING_FOOTER = -3;
    public static final int TYPE_NONE_FOOTER = -4;
    public static int TYPE = -4;
    public List list;
    private static int itemCount = 0;
    private static int remedy = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = getViewBinding(parent);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindingViewData(holder, position);
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onAppointAllData(List list) {
        int location = list.size() - this.list.size();
        this.list = list;
        notifyItemRangeChanged(list.size() - location, location);
        notifyDataSetChanged();
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onAppointData(List list) {
        for (int i = 0; i < list.size(); i++) {
            this.list.add(this.list.size(), list.get(i));
        }
        notifyItemRangeInserted(this.list.size() - list.size(), list.size());
        notifyDataSetChanged();
    }

    /**
     * 重新加载
     *
     * @param list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onReload(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position 绑定
     */
    protected abstract void onBindingViewData(RecyclerView.ViewHolder holder, int position);

    /**
     * 绑定视图
     * ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
     *
     * @param parent。
     * @return 实现
     */
    protected abstract T getViewBinding(ViewGroup parent);

    /**
     * 获取item 数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
       return position;
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

}
