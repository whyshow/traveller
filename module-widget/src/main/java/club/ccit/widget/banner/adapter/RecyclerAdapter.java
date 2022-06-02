package club.ccit.widget.banner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import club.ccit.widget.banner.base.RecyclerViewBannerBase;

/**
 * FileName: RecyclerAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/24 1:37 下午
 * Description:
 * Version:
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BannerHolder> {

    private RecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener;
    private Context context;
    private List<String> urlList;

    public RecyclerAdapter(Context context, List<String> urlList, RecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener) {
        this.context = context;
        this.urlList = urlList;
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BannerHolder(new ImageView(context));
    }

    @Override
    public void onBindViewHolder(BannerHolder holder, @SuppressLint("RecyclerView") int position) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        String url = urlList.get(position % urlList.size());
        ImageView img = (ImageView) holder.itemView;
        Glide.with(context).load(url).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(position % urlList.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        ImageView bannerItem;

        BannerHolder(View itemView) {
            super(itemView);
            bannerItem = (ImageView) itemView;
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0,0,0,0);
            bannerItem.setLayoutParams(params);
            bannerItem.setScaleType(ImageView.ScaleType.FIT_XY);

        }
    }

}
