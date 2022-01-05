package club.ccit.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import club.ccit.widget.R;

/**
 * FileName: MessageDialog
 *
 * @author: 瞌睡的牙签
 * Date: 2021/11/24 15:36
 * Description: 确认/取消对话框
 * Version:
 */
public class MessageDialog {
    private static Dialog loadingDialog;
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void show(Context context, String title,String message,String ok,String cancel,OnDialogButtonClickListener onDialogButtonClickListener) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_message_dialog, null);
        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            Log.i("MessageDialog","关闭等待框,重新加载");
        }
        Button dialogCancel = view.findViewById(R.id.dialogCancel);
        Button dialogEnsure = view.findViewById(R.id.dialogEnsure);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        TextView dialogContent = view.findViewById(R.id.dialogContent);
        dialogTitle.setText(title);
        dialogContent.setText(message);
        dialogEnsure.setText(ok);
        dialogCancel.setText(cancel);
        // 取消
        dialogCancel.setOnClickListener(v -> {
            if (loadingDialog != null){
                onDismiss();
            }
        });
        // 确认
        dialogEnsure.setOnClickListener(v -> {
            if (loadingDialog != null){
                onDismiss();
                onDialogButtonClickListener.onClick(loadingDialog,view);
            }
        });
        loadingDialog = new Dialog(context);
        loadingDialog.setOnCancelListener(dialog -> onDismiss());
        // 创建自定义样式的Dialog
        loadingDialog.addContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        loadingDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_8, null));
        loadingDialog.show();
    }

    public static void show(Context context, String title,String message,OnDialogButtonClickListener onDialogButtonClickListener) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_message_dialog, null);
        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            Log.i("MessageDialog","关闭等待框,重新加载");
        }
        Button dialogCancel = view.findViewById(R.id.dialogCancel);
        Button dialogEnsure = view.findViewById(R.id.dialogEnsure);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        TextView dialogContent = view.findViewById(R.id.dialogContent);
        dialogTitle.setText(title);
        dialogContent.setText(message);
        dialogEnsure.setText("确定");
        dialogCancel.setText("取消");
        // 取消
        dialogCancel.setOnClickListener(v -> {
            if (loadingDialog != null){
                onDismiss();
            }
        });
        // 确认
        dialogEnsure.setOnClickListener(v -> {
            if (loadingDialog != null){
                onDismiss();
                onDialogButtonClickListener.onClick(loadingDialog,view);
            }
        });
        loadingDialog = new Dialog(context);
        loadingDialog.setOnCancelListener(dialog -> onDismiss());
        // 创建自定义样式的Dialog
        loadingDialog.addContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        loadingDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_8, null));
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

    public interface OnDialogButtonClickListener{
        /**
         * OK 键点击事件
         * @param baseDialog
         * @param v
         * @return
         */
        boolean onClick(Dialog baseDialog, View v);
    }
}
