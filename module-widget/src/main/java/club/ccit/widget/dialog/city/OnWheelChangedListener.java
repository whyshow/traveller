package club.ccit.widget.dialog.city;

import club.ccit.widget.dialog.city.whee.WheelView;

/**
 * FileName: OnWheelChangedListener
 *
 * @author: 张帅威
 * Date: 2022/3/1 8:18 上午
 * Description:
 * Version:
 */
public interface OnWheelChangedListener {
    /**
     * Callback method to be invoked when current item changed
     * @param wheel the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
