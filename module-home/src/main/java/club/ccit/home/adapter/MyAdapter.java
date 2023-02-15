package club.ccit.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import club.ccit.home.R;
import club.ccit.widget.grid.NineImageAdapter;
import club.ccit.widget.grid.NineImageLayout;

/**
 * FileName: MyAdapter
 *
 * @author: mosaic
 * Date: 2023/2/15 14:59
 * Description:
 * Version:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> mList;
    private Context mContext;

    public MyAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nine_img_layout_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final NineImageLayout nineImageLayout = holder.nineImageLayout;
        holder.nineImageLayout.setAdapter(new NineImageAdapter() {
            @Override
            protected int getItemCount() {
                return mList.size();
            }

            @Override
            protected View createView(LayoutInflater inflater, ViewGroup parent, int i) {
                return inflater.inflate(R.layout.item_img_layout, parent, false);
            }

            @Override
            protected void bindView(View view, final int i) {
                final ImageView imageView = view.findViewById(R.id.iv_img);
                Glide.with(mContext).load(mList.get(i)).into(imageView);
                if (mList.size() == 1) {
                    Glide.with(mContext)
                            .asBitmap()
                            .load(mList.get(0))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                    final int width = bitmap.getWidth();
                                    final int height = bitmap.getHeight();
                                    nineImageLayout.setSingleImage(width, height,imageView);
                                }
                            });
                    Glide.with(mContext).load(mList.get(0)).into(imageView);
                } else {
                    Glide.with(mContext).load(mList.get(i)).into(imageView);
                }
            }

            @Override
            public void OnItemClick(int i, View view) {
                super.OnItemClick(position, view);
                Toast.makeText(mContext, "position:" + mList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        NineImageLayout nineImageLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nineImageLayout = itemView.findViewById(R.id.nine_image_layout);
        }
    }
}
