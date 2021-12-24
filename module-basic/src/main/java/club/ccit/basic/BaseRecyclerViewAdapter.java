package club.ccit.basic;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * @author: 张帅威
 * Date: 2021/12/3 1:03 下午
 * Description: RecyclerViewAdapter封装基类
 * Version:
 */
public abstract class BaseRecyclerViewAdapter <T extends ViewBinding> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    protected T binding;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = getViewBinding(parent);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        onBindingViewData(holder,position);
    }

    @Override
    public int getItemCount() {
        return setItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull T itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
