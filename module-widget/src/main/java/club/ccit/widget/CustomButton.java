package club.ccit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * FileName: CustomEditText
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/17 3:59 下午
 * Description: 自定义 EditText
 * Version:
 */
public class CustomButton extends AppCompatButton {
    private int resourceId = 0;
    private Bitmap bitmap;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }



    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomButton);
        bitmap = BitmapFactory.decodeResource(getResources(),array.getResourceId(R.styleable.CustomButton_b_icon,resourceId));
    }

    @Override
    public void onDraw(Canvas canvas) {
        //图片居中偏左显示
        int x = (int) this.getTextSize();
        int y = (this.getMeasuredHeight()-bitmap.getHeight())/2;
        canvas.drawBitmap(bitmap, x, y, null);
        super.onDraw(canvas);

    }
}
