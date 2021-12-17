package club.ccit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: CustomEditText
 *
 * @author: 张帅威
 * Date: 2021/12/17 3:59 下午
 * Description: 自定义 EditText
 * Version:
 */
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText implements TextWatcher {
    private boolean isStick = false;
    public CustomEditText(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化数据
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomEditText);
        isStick = array.getBoolean(R.styleable.CustomEditText_stick,isStick);
    }

    /**
     * 输入内容改变之前
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * 输入内容改变中
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * 输入内容改变之后
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 是否允许粘贴
     * @param id
     * @return true 不允许粘贴、false 允许粘贴（默认允许粘贴）
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        if (isStick){
            return true;
        }else {
            return super.onTextContextMenuItem(id);
        }

    }
}
