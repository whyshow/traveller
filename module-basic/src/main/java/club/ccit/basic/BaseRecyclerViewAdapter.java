package club.ccit.basic;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;
import club.ccit.basic.holder.BaseHolder;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:03 下午
 * Description: RecyclerViewAdapter封装基类
 * Version:
 */
public abstract class BaseRecyclerViewAdapter<V extends ViewBinding> extends RecyclerView.Adapter<BaseHolder> {
    public List data;

    @NonNull
    @Override
    public BaseHolder<V> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseHolder(onBindingView(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        onBindingData(holder,data.get(position),position);
    }

    /**
     * 添加item数据
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onAppointData(List list) {
        for (int i = 0; i < list.size(); i++) {
            this.data.add(this.data.size(),list.get(i));
        }
        notifyItemRangeInserted(this.data.size() - list.size(), list.size());
        notifyDataSetChanged();
    }

    /**
     * 重新加载
     * @param list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onReload(List list){
        this.data = list;
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     * @param holder
     * @param data
     * @param position
     */
    protected abstract void onBindingData(BaseHolder<V> holder, Object data, int position);

    /**
     * 绑定view
     * @param viewGroup
     * @return
     */
    protected abstract V onBindingView(ViewGroup viewGroup);

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

}
