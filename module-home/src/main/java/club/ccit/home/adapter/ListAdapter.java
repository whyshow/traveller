package club.ccit.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.ccit.common.LogUtils;
import club.ccit.home.R;
import club.ccit.home.fragment.ListFragment;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.widget.ExpandTextView;

/**
 * FileName: ListAdapter
 *
 * @author: 张帅威
 * Date: 2022/6/2 16:39
 * Description:
 * Version:
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List list;
    public static final int TYPE_DATA = 1;
    public static final int TYPE_FOOTER = 2;
    public static int ERROR_NO_DATA = 0;
    private ListFragment fragment;
    public ListAdapter(ListFragment context) {
        fragment = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_state, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (getItemViewType(position) == TYPE_DATA) {
            ViewHolder viewHolder = (ViewHolder) holder;
            NewsListBean.Result dataBean = (NewsListBean.Result) list.get(position);
            // 设置文本数据
            viewHolder.titleTextView.setText(position+1+"  "+dataBean.getArticle_title());
            viewHolder.timeTextView.setText(dataBean.getArticle_date());
            if (!dataBean.getArticle_text().isEmpty()){
                viewHolder.contentTextView.setContent(dataBean.getArticle_text());
            }else {
                viewHolder.contentTextView.setContent("");
            }
            viewHolder.authorTextView.setText(dataBean.getArticle_user()+"/文");
            viewHolder.itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.i(position+1+"  "+dataBean.getArticle_title());
                }
            });
        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.noData.setVisibility(View.GONE);
            footerViewHolder.errorMsg.setVisibility(View.GONE);
            footerViewHolder.retryButton.setVisibility(View.GONE);
            switch (ERROR_NO_DATA) {
                case 0:
                    footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    footerViewHolder.noData.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    footerViewHolder.retryButton.setVisibility(View.VISIBLE);
                    footerViewHolder.retryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragment.refresh();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    public void onAppointData(List list) {
        for (int i = 0; i < list.size(); i++) {
            this.list.add(this.list.size(), (NewsListBean.Result) list.get(i));
        }
        notifyItemRangeInserted(this.list.size() - list.size(), list.size());
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof NewsListBean.Result) {
            return TYPE_DATA;
        } else {
            return TYPE_FOOTER;
        }

    }

    /**
     * 错误状态
     */
    public void setError(int type) {
        ERROR_NO_DATA = type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView,authorTextView,timeTextView;
        ExpandTextView contentTextView;
        CardView itemCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            itemCardView = itemView.findViewById(R.id.itemCardView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout noData,progressBar;
        TextView errorMsg,retryButton;
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            noData = itemView.findViewById(R.id.noData);
            progressBar = itemView.findViewById(R.id.progressBar);
            errorMsg = itemView.findViewById(R.id.errorMsg);
            retryButton = itemView.findViewById(R.id.retryButton);
        }
    }


   public void onDestroy() {
        fragment = null;
    }

}
