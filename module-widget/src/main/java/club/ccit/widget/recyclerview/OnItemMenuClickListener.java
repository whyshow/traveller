package club.ccit.widget.recyclerview;

/**
 * FileName: OnItemMenuClickListener
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:18 上午
 * Description:
 * Version:
 */
public interface OnItemMenuClickListener {
    /**
     * item点击监听
     * @param menuBridge menu bridge.
     * @param adapterPosition position of item.
     */
    void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition);
}
