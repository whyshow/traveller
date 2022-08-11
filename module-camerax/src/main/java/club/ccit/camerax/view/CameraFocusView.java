package club.ccit.camerax.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author: 张帅威
 * Date: 2021/11/29 1:33 下午
 * Description:
 * Version:
 */
public class CameraFocusView extends AppCompatImageView {
    /**
     * 焦点附近设置矩形区域作为对焦区域
     */
    private Rect touchFocusRect;
    /**
     * 新建画笔
     */
    private Paint touchFocusPaint;

    public CameraFocusView(Context context) {
        this(context, null, 0);
        init(context);
    }

    public CameraFocusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public CameraFocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //画笔设置
        touchFocusPaint = new Paint();
        touchFocusPaint.setColor(Color.GREEN);
        touchFocusPaint.setStyle(Paint.Style.STROKE);
        touchFocusPaint.setStrokeWidth(3);
    }

    /**
     * 对焦并绘制对焦矩形框
     *
     * @param x
     * @param y
     */
    public void setTouchFocusRect(float x, float y) {
        if (touchFocusRect != null) {
            disDrawTouchFocusRect();
        }
        //以焦点为中心，宽度为200的矩形框
        touchFocusRect = new Rect((int) (x - 100), (int) (y - 100), (int) (x + 100), (int) (y + 100));
        //刷新界面，调用onDraw(Canvas canvas)函数绘制矩形框
        postInvalidate();
    }

    /**
     * 对焦完成后，清除对焦矩形框
     */
    public void disDrawTouchFocusRect() {
        //将对焦区域设置为null，刷新界面后对焦框消失
        touchFocusRect = null;
        //刷新界面，调用onDraw(Canvas canvas)函数
        postInvalidate();
    }

    /**
     * 在画布上绘图，postInvalidate()后自动调用
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        drawTouchFocusRect(canvas);
        super.onDraw(canvas);
    }

    private void drawTouchFocusRect(Canvas canvas) {
        if (null != touchFocusRect) {
            //根据对焦区域targetFocusRect，绘制自己想要的对焦框样式，本文在矩形四个角取L形状
            //左下角
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.bottom, touchFocusRect.left + 20, touchFocusRect.bottom + 2, touchFocusPaint);
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.bottom - 20, touchFocusRect.left, touchFocusRect.bottom, touchFocusPaint);
            //左上角
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.top - 2, touchFocusRect.left + 20, touchFocusRect.top, touchFocusPaint);
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.top, touchFocusRect.left, touchFocusRect.top + 20, touchFocusPaint);
            //右上角
            canvas.drawRect(touchFocusRect.right - 20, touchFocusRect.top - 2, touchFocusRect.right + 2, touchFocusRect.top, touchFocusPaint);
            canvas.drawRect(touchFocusRect.right, touchFocusRect.top, touchFocusRect.right + 2, touchFocusRect.top + 20, touchFocusPaint);
            //右下角
            canvas.drawRect(touchFocusRect.right - 20, touchFocusRect.bottom, touchFocusRect.right + 2, touchFocusRect.bottom + 2, touchFocusPaint);
            canvas.drawRect(touchFocusRect.right, touchFocusRect.bottom - 20, touchFocusRect.right + 2, touchFocusRect.bottom, touchFocusPaint);
        }
    }
}
