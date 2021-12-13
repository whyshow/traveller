package club.ccit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * FileName: CustomRecyclerView
 *
 * @author: 张帅威
 * Date: 2021/12/10 2:08 下午
 * Description: RecyclerView 封装
 * Version:
 */
public class CustomRecyclerView extends RecyclerView {
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
        setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
}
