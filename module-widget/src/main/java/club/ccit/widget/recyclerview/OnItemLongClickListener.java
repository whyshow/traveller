package club.ccit.widget.recyclerview;

import android.view.View;

/**
 * FileName: OnItemLongClickListener
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:17 上午
 * Description:
 * Version:
 */
public interface OnItemLongClickListener {
    /**
     * @param view target view.
     * @param adapterPosition position of item.
     */
    void onItemLongClick(View view, int adapterPosition);
}
