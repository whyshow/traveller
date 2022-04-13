package club.ccit.widget.dialog.city.listener;

import club.ccit.widget.dialog.city.whee.WheelView;

/**
 * FileName: OnWheelClickedListener
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:33 上午
 * Description:
 * Version:
 */
public interface OnWheelClickedListener {
    /**
     * Callback method to be invoked when current item clicked
     *
     * @param wheel     the wheel view
     * @param itemIndex the index of clicked item
     */
    void onItemClicked(WheelView wheel, int itemIndex);
}