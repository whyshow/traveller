package club.ccit.widget.pay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

import club.ccit.widget.R;

/**
 * FileName: PasswordView
 *
 * @author: 张帅威
 * Date: 2022/2/9 8:27 上午
 * Description: 支付视图
 * Version: 1.0
 */
public class PayPassword extends View {
    /**
     * 密码长度
     */
    private int passwordCount;
    /**
     * 实心的画笔
     */
    private Paint mCirclePaint;
    /**
     * 输入框的画笔
     */
    private Paint mPaint;
    /**
     * 实心的圆的半径
     */
    private float mRadius;
    /**
     * 密码文本
     */
    private StringBuffer mText;
    private float inputBoxStroke;
    private int symbolColor;
    /**
     * 边框颜色
     */
    private int strokeColor;
    public PayPassword(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public PayPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.inputBox);
        //支持某些属性设置,比如密码位数,边框颜色、宽度,"●"的颜色、大小
        passwordCount = ta.getInteger(R.styleable.inputBox_passwordCount, 6);
        strokeColor = ta.getColor(R.styleable.inputBox_stokeColor, getResources().getColor(R.color.gray,null));
        symbolColor = ta.getColor(R.styleable.inputBox_symbolColor, Color.BLACK);
        mRadius = ta.getDimension(R.styleable.inputBox_symbolRadius, 24);
        inputBoxStroke = ta.getDimension(R.styleable.inputBox_inputBoxStroke, 4f);
        //设置输入框圆角边框
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setStroke((int) inputBoxStroke, strokeColor);
        gd.setCornerRadius(8);
        setBackground(gd);
        ta.recycle();
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(strokeColor);
            mPaint.setStrokeWidth(inputBoxStroke);
        }
        if (mCirclePaint == null) {
            mCirclePaint = new Paint();
            mCirclePaint.setColor(symbolColor);
            mCirclePaint.setStyle(Paint.Style.FILL);
        }
    }

    public PayPassword(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int singleWidth = getMeasuredWidth() / passwordCount;
        int height = getMeasuredHeight();
        //绘制每个"●"之间的分割线
        for (int i = 1; i < passwordCount; i++) {
            canvas.drawLine(singleWidth * i, 0, singleWidth * i, height, mPaint);
        }
        if (mText != null) {
            //绘制"●"
            int textSize = Math.min(mText.length(), passwordCount);
            for (int i = 1; i <= textSize; i++) {
                canvas.drawCircle(singleWidth * i - singleWidth / 2, height / 2, mRadius, mCirclePaint);
            }
        }
    }

    public int getPasswordCount() {
        return passwordCount;
    }

    /**
     * 支持密码位数设置
     * @param passwordCount 密码数量
     */
    public void setPasswordCount(int passwordCount) {
        this.passwordCount = passwordCount;
    }

    /**
     * 密码改变,重新绘制
     * @param text 文本
     */
    public void setPassword(CharSequence text) {
        mText = (StringBuffer) text;
        if (text.length() > passwordCount) {
            mText.delete(mText.length() - 1, mText.length());
            return;
        }
        postInvalidate();
    }

    public void setPaint(int strokeColor){
        //设置输入框圆角边框
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setStroke((int) inputBoxStroke, strokeColor);
        gd.setCornerRadius(8);
        setBackground(gd);
        mPaint = new Paint();
        mPaint.setColor(strokeColor);
        mPaint.setStrokeWidth(inputBoxStroke);
    }

    /**
     * 清除密码
     */
    public void clearPassword() {
        if (mText != null) {
            mText.delete(0, mText.length());
        }
    }

    /**
     * 设置错误提醒
     */
    public void setErrorStyle() {
        strokeColor = getResources().getColor(R.color.red);
        setPaint(strokeColor);
        postInvalidate();
    }

    /**
     * 设置错误提醒
     */
    public void setInputStyle() {
        strokeColor = getResources().getColor(R.color.gray);
        setPaint(strokeColor);
        postInvalidate();
    }



    /**
     * 获取密码
     * @return
     */
    public CharSequence getPassword() {
        return mText;
    }
}
