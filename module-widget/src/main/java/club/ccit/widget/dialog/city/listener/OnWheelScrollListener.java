package club.ccit.widget.dialog.city.listener;

import club.ccit.widget.dialog.city.whee.WheelView;

/**
 * FileName: OnWheelScrollListener
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:32 上午
 * Description:
 * Version:
 */
public interface OnWheelScrollListener {
    /**
     * Callback method to be invoked when scrolling started.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingStarted(WheelView wheel);

    /**
     * Callback method to be invoked when scrolling ended.
     * @param wheel the wheel view whose state has changed.
     */
    void onScrollingFinished(WheelView wheel);
}