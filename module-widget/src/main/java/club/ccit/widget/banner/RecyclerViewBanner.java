package club.ccit.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import club.ccit.widget.banner.adapter.RecyclerAdapter;
import club.ccit.widget.banner.base.RecyclerViewBannerBase;

/**
 * FileName: RVBanner
 *
 * @author: 张帅威
 * Date: 2022/1/24 1:35 下午
 * Description:
 * Version:
 */
public class RecyclerViewBanner extends RecyclerViewBannerBase<LinearLayoutManager, RecyclerAdapter> {

    public RecyclerViewBanner(Context context) {
        this(context, null);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBannerScrolled(RecyclerView recyclerView, int dx, int dy) {
        //解决连续滑动时指示器不更新的问题
        if (bannerSize < 2){
            return;
        }
        int firstReal = mLayoutManager.findFirstVisibleItemPosition();
        View viewFirst = mLayoutManager.findViewByPosition(firstReal);
        float width = getWidth();
        if (width != 0 && viewFirst != null) {
            float right = viewFirst.getRight();
            float ratio = right / width;
            if (ratio > 0.8) {
                if (currentIndex != firstReal) {
                    currentIndex = firstReal;
                    refreshIndicator();
                }
            } else if (ratio < 0.2) {
                if (currentIndex != firstReal + 1) {
                    currentIndex = firstReal + 1;
                    refreshIndicator();
                }
            }
        }
    }

    @Override
    protected void onBannerScrollStateChanged(RecyclerView recyclerView, int newState) {
        int first = mLayoutManager.findFirstVisibleItemPosition();
        int last = mLayoutManager.findLastVisibleItemPosition();
        if (currentIndex != first && first == last) {
            currentIndex = first;
            refreshIndicator();
        }
    }

    @Override
    protected LinearLayoutManager getLayoutManager(Context context, int orientation) {
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected RecyclerAdapter getAdapter(Context context, List<String> list, OnBannerItemClickListener onBannerItemClickListener) {
        return new RecyclerAdapter(context, list,onBannerItemClickListener);
    }
}
