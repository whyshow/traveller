package club.ccit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * FileName: CircleProgressBar
 *
 * @author: mosaic
 * Date: 2023/3/28 14:18
 * Description:
 * Version:
 */
public class CircleProgressBar extends View {

    private static final int DEFAULT_COLOR = Color.BLUE;
    private static final int DEFAULT_PROGRESS = 0;
    private static final int MAX_PROGRESS = 100;

    private Paint mPaint;
    private int mColor;
    private int mProgress;

    public CircleProgressBar(Context context) {
        super(context);
        init();
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
//        try {
//            mColor = a.getColor(R.styleable.CircleProgressBar_progressColor, DEFAULT_COLOR);
//            mProgress = a.getInt(R.styleable.CircleProgressBar_progress, DEFAULT_PROGRESS);
//        } finally {
//            a.recycle();
//        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 20;
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        mPaint.setColor(mColor);
        canvas.drawArc(new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius), -90, 360 * mProgress / MAX_PROGRESS, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }
}