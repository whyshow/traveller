package club.ccit.widget.swipe.touch;

import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: OnItemMoveListener
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:14 上午
 * Description:
 * Version:
 */
public interface OnItemMoveListener {

    /**
     * When drag and drop the callback.
     *
     * @param srcHolder src.
     * @param targetHolder target.
     *
     * @return To deal with the returns true, false otherwise.
     */
    boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder);

    /**
     * When items should be removed when the callback.
     *
     * @param srcHolder src.
     */
    void onItemDismiss(RecyclerView.ViewHolder srcHolder);
}
