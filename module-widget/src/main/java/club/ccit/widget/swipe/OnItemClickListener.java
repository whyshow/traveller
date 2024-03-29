package club.ccit.widget.swipe;

import android.view.View;

/**
 * FileName: OnItemClickListener
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:20 上午
 * Description:
 * Version:
 */
public interface OnItemClickListener {
    /**
     * onItemClick
     * @param view target view.
     * @param adapterPosition position of item.
     */
    void onItemClick(View view, int adapterPosition);
}
