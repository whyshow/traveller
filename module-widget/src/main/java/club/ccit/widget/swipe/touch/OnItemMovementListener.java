package club.ccit.widget.swipe.touch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: OnItemMovementListener
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:13 上午
 * Description:
 * Version:
 */
public interface OnItemMovementListener {
    int INVALID = 0;

    int LEFT = ItemTouchHelper.LEFT;

    int UP = ItemTouchHelper.UP;

    int RIGHT = ItemTouchHelper.RIGHT;

    int DOWN = ItemTouchHelper.DOWN;

    /**
     * Can drag and drop the ViewHolder?
     *
     * @param recyclerView {@link RecyclerView}.
     * @param targetViewHolder target ViewHolder.
     *
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onDragFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder);

    /**
     * Can swipe and drop the ViewHolder?
     *
     * @param recyclerView {@link RecyclerView}.
     * @param targetViewHolder target ViewHolder.
     *
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onSwipeFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder);
}
