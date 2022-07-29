package club.ccit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author: 张帅威
 * Date: 2021/11/22 08:58
 * Description: 自定义 LinearLayout
 * Version:
 */
public class CustomLinearLayout extends LinearLayout {
    private Matrix mMatrix;
    private Paint mPaint;
    /**
     * 圆角值
     */
    private int radius;
    /**
     * 默认的圆角值
     */
    private int defaultRadius = 0;
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mMatrix = new Matrix();
        mPaint = new Paint();
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomImageView);
        radius = array.getDimensionPixelSize(R.styleable.CustomImageView_radius,defaultRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
