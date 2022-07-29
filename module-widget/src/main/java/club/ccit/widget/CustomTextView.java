package club.ccit.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: 张帅威
 * Date: 2021/11/22 08:38
 * Description: 自定义TextView
 * Version:
 */
public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    public CustomTextView(@NonNull Context context) {
        super(context);
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }
}
