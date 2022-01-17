package club.ccit.basic.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import club.ccit.basic.R;

/**
 * FileName: NoneViewHolder
 *
 * @author: 张帅威
 * Date: 2022/1/17 3:47 下午
 * Description: 没有更多了的ViewHolder
 * Version:
 */
public class FooterViewHolder extends RecyclerView.ViewHolder{
    public TextView noDataTextView;
    public FooterViewHolder(@NonNull View itemView) {
        super(itemView);
        noDataTextView = itemView.findViewById(R.id.noDataTextView);
    }
}
