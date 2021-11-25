package club.ccit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author: 张帅威
 * Date: 2021/11/17 15:53
 * Description: 自定义圆角ImageView
 *              目前支持圆角
 * Version:
 */
public class CustomImageView extends AppCompatImageView {
    /**
     * 圆角值
     */
    private int radius;
    /**
     * 默认的圆角值
     */
    private int defaultRadius = 0;
    /**
     *  3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    private Paint mPaint;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    public CustomImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化数据
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mMatrix = new Matrix();
        mPaint = new Paint();
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomImageView);
        radius = array.getDimensionPixelSize(R.styleable.CustomImageView_radius,defaultRadius);
        setScaleType(ScaleType.CENTER_CROP);
    }

    /**
     * 设置显示比例
     * @param scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);

    }

    /**
     * 绘制
     * @param canvas
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        Bitmap bitmap = drawableToBitmap(getDrawable());
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight()))
        {
            // 如果图片的宽或者高与view的宽高不匹配，
            // 计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；
            // 所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
                    getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mBitmapShader);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),
                radius, radius,mPaint);
    }


    private Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() :
                drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() :
                drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
