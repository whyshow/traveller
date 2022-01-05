package club.ccit.widget.dialog;

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

/**
 * FileName: InputDialog
 *
 * @author: 瞌睡的牙签
 * Date: 2022/1/4 2:22 下午
 * Description: 输入对话框
 * Version:
 */
public class InputDialog {
    private static Dialog loadingDialog;
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void show(Context context,int resource, OnDialogListener onDialogListener) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                resource, null);

        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            Log.i("MessageDialog","关闭等待框,重新加载");
        }
        loadingDialog = new Dialog(context);
        onDialogListener.onClick(view);
        loadingDialog.setContentView(view);
        loadingDialog.setCancelable(false);
        // 创建自定义样式的Dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.card_white_4, null));
        }else {
            loadingDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.card_white_4));
        }
        //获取当前Activity所在的窗体
        Window dialogWindow = loadingDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (getScreenWidth(context)*0.95);
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        loadingDialog.show();

    }
    /**
     * 关闭
     */
    public static void onDismiss(){
        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            loadingDialog = null;
            Log.i("MessageDialog","关闭等待框");
        }
    }

    public interface OnDialogListener{
        /**
         * 回调视图
         * @param v
         * @return
         */
        void onClick(View v);
    }

    private static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
