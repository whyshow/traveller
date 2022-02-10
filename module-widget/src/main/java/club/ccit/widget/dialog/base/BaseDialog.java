package club.ccit.widget.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatDialog;

import club.ccit.widget.R;
import club.ccit.widget.action.AnimAction;

/**
 * FileName: BaseDialog
 *
 * @author: 张帅威
 * Date: 2022/2/9 4:44 下午
 * Description: Dialog基类
 * Version:
 */
public abstract class BaseDialog extends AppCompatDialog implements AnimAction{
    public BaseDialog(Context context) {
        super(context,R.style.BaseDialogStyle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setWindowAnimations(setDialogAnimations());
        getWindow().setLayout(setWidth(),setHeight());
        if (setDialogTransparent()){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }else {
            getWindow().setBackgroundDrawableResource(setDialogBackgroundResId());
        }
        getWindow().setGravity(setDialogGravity());
        setCancelable(setDialogCancelable());
    }

    /**
     * 设置是否透明
     * @return
     */
    protected abstract boolean setDialogTransparent();
    /**
     * 出现的位置
     * @return
     */
    protected abstract int setDialogGravity();

    /**
     * 设置是否允许点击其他区域取消
     * @return
     */
    public abstract boolean setDialogCancelable();

    public void onDialogDismiss(){
        dismiss();
    }

    /**
     * 动画资源
     * @return
     */
    protected abstract int setDialogAnimations();

    /**
     * 宽度
     * @return
     */
    protected abstract int setWidth();

    /**
     * 高度
     * @return
     */
    protected abstract int setHeight();

    /**
     * 背景资源文件
     * @return
     */
    protected abstract int setDialogBackgroundResId();

    /**
     * 监听事件
     * @param view
     */
    public abstract void onClick(View view);

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 布局文件
     * @return
     */
    protected abstract @LayoutRes int setLayoutResId();
}
