package club.ccit.home.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import club.ccit.common.LogUtils;
import club.ccit.home.R;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.widget.ExpandTextView;

/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
public class HomeAdapter extends PagingDataAdapter<NewsListBean.Result,HomeAdapter.ViewHolder> {


    public HomeAdapter(@NonNull DiffUtil.ItemCallback<NewsListBean.Result> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (getItem(position) != null){
            // 设置文本数据
            holder.titleTextView.setText(position+1+"  "+getItem(position).getArticle_title());
            holder.timeTextView.setText(getItem(position).getArticle_date());
            if (!getItem(position).getArticle_text().isEmpty()){
                holder.contentTextView.setContent(getItem(position).getArticle_text());
            }else {
                holder.contentTextView.setContent("");
            }

            holder.authorTextView.setText(getItem(position).getArticle_user()+"/文");
            holder.itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.i(position+1+"  "+getItem(position).getArticle_title());
                }
            });
        }
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
}
