package club.ccit.widget.pay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import club.ccit.widget.R;
import club.ccit.widget.action.AnimAction;

/**
 * FileName: PayDialog
 *
 * @author: 张帅威
 * Date: 2022/2/9 1:50 下午
 * Description: 支付弹窗
 * Version:
 */
public class PayDialog implements AnimAction {
    private static Dialog dialog;
    private static OnDialogListener onDialogListener;

    @SuppressLint({"UseCompatLoadingForDrawables", "ObsoleteSdkInt", "ResourceType"})
    public static void show(Context context, PayDialog.OnDialogListener dialogListener) {
        onDialogListener = dialogListener;
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_pay_dialog, null);
        if (dialog != null) {
            onDismiss();
            Log.i("PayDialog", "关闭支付弹窗,重新加载");
        }

        dialog = new Dialog(context);
        PayPasswordView payPasswordView = view.findViewById(R.id.payPasswordView);
        payPasswordView.setPayClickListener(new PayPasswordView.OnPayClickListener() {
            @Override
            public void onPassFinish(String password) {
                onDialogListener.onPassFinish(password,payPasswordView);
            }

            @Override
            public void onPayClose() {
                onDismiss();
            }

            @Override
            public void onPayForget() {
                onDialogListener.onPayForget();
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        // 创建自定义样式的Dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pay, null));
        } else {
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pay));
        }
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(ANIM_BOTTOM);
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (getScreenWidth(context));
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        dialog.show();
    }

    /**
     * 关闭
     */
    public static void onDismiss() {
        if (dialog != null) {
            onDialogListener = null;
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
            Log.i("PayDialog", "关闭等待框");
        }
    }

    public interface OnDialogListener {
        /**
         * 输入完成
         * @param password 输入的密码
         * @param payPasswordView
         */
        void onPassFinish(String password, PayPasswordView payPasswordView);
        /**
         * 找回密码
         */
        void onPayForget();
    }

    private static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}