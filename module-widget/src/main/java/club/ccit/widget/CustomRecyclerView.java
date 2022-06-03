package club.ccit.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * FileName: CustomRecyclerView
 *
 * @author: 瞌睡的牙签
 * Date: 2022年01月18日10:46:23
 * Description: RecyclerView 封装
 * Version:
 */
public class CustomRecyclerView extends RecyclerView {
    private static final String SAVED_SUPER_STATE = "super-state";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    private Parcelable mLayoutManagerSavedState;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 取消 反弹效果
        setWillNotDraw(true);
    }

    /**
     * 保存数据状态
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_SUPER_STATE, super.onSaveInstanceState());
        bundle.putParcelable(SAVED_LAYOUT_MANAGER, Objects.requireNonNull(this.getLayoutManager()).onSaveInstanceState());
        return bundle;
    }

    /**
     * 恢复数据状态
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mLayoutManagerSavedState = bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            state = bundle.getParcelable(SAVED_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * 配置更改后恢复滚动位置
     * 必须在设置适配器后调用
     */
    private void restorePosition() {
        if (mLayoutManagerSavedState != null) {
            Objects.requireNonNull(this.getLayoutManager()).onRestoreInstanceState(mLayoutManagerSavedState);
            mLayoutManagerSavedState = null;
        }
    }

    /**
     * 设置适配器数据
     *
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        restorePosition();
    }
}
