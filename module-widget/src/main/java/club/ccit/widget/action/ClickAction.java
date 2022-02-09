package club.ccit.widget.action;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

/**
 * FileName: ClickAction
 *
 * @author: 张帅威
 * Date: 2022/2/9 4:50 下午
 * Description: 点击事件意图
 * Version:
 */
public interface ClickAction extends View.OnClickListener {
    /**
     *
     * @param id
     * @param <V>
     * @return
     */
    <V extends View> V findViewById(@IdRes int id);

    /**
     *
     * @param ids
     */
    default void setOnClickListener(@IdRes int... ids) {
        setOnClickListener(this, ids);
    }

    /**
     *
     * @param listener
     * @param ids
     */
    default void setOnClickListener(@Nullable View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     *
     * @param views
     */
    default void setOnClickListener(View... views) {
        setOnClickListener(this, views);
    }

    /**
     *
     * @param listener
     * @param views
     */
    default void setOnClickListener(@Nullable View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    @Override
    default void onClick(View view) {
        // 默认不实现，让子类实现
    }
}