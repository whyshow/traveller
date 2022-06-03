package club.ccit.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.action.AdapterAction;

/**
 * FileName: BaseAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/17 9:12 上午
 * Description:
 * Version: 1.0 版本 使用viewBinding绑定控件，自动增加footer,显示加载中、没有更多了、重新加载和失败文字提示灯
 */
public abstract class BaseAdapter<T extends ViewBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List list;
    private  static final int TYPE_DATA = 1;
    private static final int TYPE_FOOTER = 2;
    private static int FOOTER_TYPE = 0;
    public static int FOOTER_LOADING = 0;
    public static int FOOTER_NO_DATA = 1;
    public static int FOOTER_RETRY = 2;
    public AdapterAction action;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            return new ViewHolder(getViewBinding(parent));
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_state, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_DATA) {
            onBindingViewData((ViewHolder) holder, position);
        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.noData.setVisibility(View.GONE);
            footerViewHolder.errorMsg.setVisibility(View.GONE);
            footerViewHolder.retryButton.setVisibility(View.GONE);
            switch (FOOTER_TYPE) {
                case 0:
                    footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                    action.onNext();
                    break;
                case 1:
                    footerViewHolder.noData.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    footerViewHolder.retryButton.setVisibility(View.VISIBLE);
                    footerViewHolder.retryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            action.onRefresh();
                            footerViewHolder.retryButton.setVisibility(View.GONE);
                            footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     * @param page
     */
    public void onAddData(List list, int page) {
        if (page > 1) {
            if (this.list.size() % list.size() == 1){
                FOOTER_TYPE = FOOTER_NO_DATA;
            }else {
                FOOTER_TYPE = FOOTER_LOADING;
            }
            for (int i = 0; i < list.size(); i++) {
                this.list.add(this.list.size(), list.get(i));
            }
            notifyItemRangeInserted(this.list.size() - list.size(), list.size());
            notifyDataSetChanged();
        } else {
            onRefresh(list);
        }
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     * @param page
     */
    public void onAddDataset(List list, int page) {
        FOOTER_TYPE = FOOTER_LOADING;
        if (page > 1) {
            this.list = list;
            notifyItemRangeInserted(this.list.size() - list.size(), list.size());
            notifyDataSetChanged();
        } else {
            onRefresh(list);
        }
    }

    /**
     * 重新加载
     */
    public void onRefresh(List list) {
        this.list = list;
        FOOTER_TYPE = FOOTER_LOADING;
        notifyDataSetChanged();
    }

    /**
     * 错误状态
     */
    public void setError(int type) {
        FOOTER_TYPE = type;
        if (list == null) {
            list = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    /**
     * 获取item 数量
     *
     * @return 返回item总数
     */
    @Override
    public int getItemCount() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取item类型
     *
     * @param position item 游标
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (list.size() == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_DATA;
        }
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
     * .inflate(LayoutInflater.from(parent.getContext()))
     *
     * @param parent。
     * @return 实现
     */
    protected abstract T getViewBinding(ViewGroup parent);

    /**
     * 绑定控件
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public T itemBinding;

        public ViewHolder(@NonNull T itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }

    /**
     * 绑定 Footer控件id
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout noData, progressBar;
        TextView errorMsg, retryButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            noData = itemView.findViewById(R.id.noData);
            progressBar = itemView.findViewById(R.id.progressBar);
            errorMsg = itemView.findViewById(R.id.errorMsg);
            retryButton = itemView.findViewById(R.id.retryButton);
        }
    }

}
