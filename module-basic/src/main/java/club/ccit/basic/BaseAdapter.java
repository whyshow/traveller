package club.ccit.basic;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import java.util.List;
import club.ccit.basic.holder.FooterViewHolder;

/**
 * FileName: BaseAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/17 9:12 上午
 * Description:
 * Version:
 */
public abstract class BaseAdapter <T extends ViewBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        if (viewType == TYPE_FOOTER){
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }else {
            binding = getViewBinding(parent);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if ((position + remedy) <= itemCount){
            if (list.get(position) != null){
                onBindingViewData(holder,position);
            }
        }else {
            FooterViewHolder noneViewHolder = (FooterViewHolder) holder;
            switch (TYPE){
                case TYPE_ERROR_FOOTER:
                    noneViewHolder.noDataTextView.setText("加载错误,上划重试。");
                    return;
                case TYPE_LOADING_FOOTER:
                    noneViewHolder.noDataTextView.setText("加载中");
                    return;
                case TYPE_NONE_FOOTER:
                    noneViewHolder.noDataTextView.setText("没有更多了");
                    return;
                default:

            }
            noneViewHolder.noDataTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("LOG111","点击了");
                }
            });
        }
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
     * @param list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onReload(List list){
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 设置footer提示信息
     * @param type
     */
    public void setFooterView(int type){
        TYPE = type;
        notifyItemChanged(itemCount-1);
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
        itemCount = list.size() + 1;
        return itemCount;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + remedy) <= itemCount){
            return position;
        }else {
            return TYPE_FOOTER;
        }
    }

    public static boolean isFooterView(int position){
        if ((position + remedy) > itemCount){
            return true;
        }else {
            return false;
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

}
