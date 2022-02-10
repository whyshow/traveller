package club.ccit.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import club.ccit.widget.dialog.base.BaseDialog;

/**
 * FileName: InputDialog
 *
 * @author: 瞌睡的牙签
 * Date: 2022/1/4 2:22 下午
 * Description: 输入对话框
 * Version:
 */
public class DefaultDialog extends BaseDialog {

    public DefaultDialog(Context context) {
        super(context);
    }

    @Override
    protected boolean setDialogTransparent() {
        return false;
    }

    @Override
    protected int setDialogGravity() {
        return 0;
    }

    @Override
    public boolean setDialogCancelable() {
        return false;
    }

    @Override
    protected int setDialogAnimations() {
        return 0;
    }

    @Override
    protected int setWidth() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int setHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int setDialogBackgroundResId() {
        return 0;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutResId() {
        return 0;
    }

    @Override
    public void onDialogDismiss() {
        super.onDialogDismiss();

    }

    private static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
