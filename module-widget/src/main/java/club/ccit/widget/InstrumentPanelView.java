package club.ccit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * FileName: InstrumentPanelView
 *
 * @author: mosaic
 * Date: 2023/3/16 10:46
 * Description:
 * Version:
 */
public class InstrumentPanelView extends ViewGroup {
    public InstrumentPanelView(Context context) {
        super(context);
    }

    public InstrumentPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InstrumentPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InstrumentPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //父控件ViewGroupMode和Size
        int widthMode = MeasureSpec. getMode(widthMeasureSpec);
        int heightMode = MeasureSpec. getMode(heightMeasureSpec);
        int widthSize = MeasureSpec. getSize(widthMeasureSpec);
        int heightSize = MeasureSpec. getSize(heightMeasureSpec);
        Log.i("LOG111","ViewGroup可用宽度为："+ widthSize);
        Log.i("LOG111","ViewGroup可用高度为："+ heightSize);

        int layoutWidth = 0;
        int layoutHeight = 0;
        // 测量并保存父控件ViewGroup的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childView = getChildAt(0);
        int left = getLeft();                                           //设置子控件左坐标
        int top = getTop();                                             //设置子控件上坐标
        int right = getLeft() + childView.getMeasuredWidth();           //设置子控件右坐标
        int bottom = getPaddingTop() + childView.getMeasuredHeight();   //设置子控件下坐标
        Log.i("LOG111","子控件左坐标为："+ left);
        Log.i("LOG111","子控件上坐标为："+ top);
        Log.i("LOG111","子控件右坐标为："+ right);
        Log.i("LOG111","子控件下坐标为："+ bottom);
    }
}
