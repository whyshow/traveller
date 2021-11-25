package club.ccit.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import club.ccit.widget.R;

/**
 * @author: 张帅威
 * Date: 2021/11/24 10:19
 * Description: 等待弹窗
 * Version:
 */
public class WaitDialog{
    private static Dialog loadingDialog;
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void show(Context context,String message) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_wait_dialog, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.waitDialogView);
        // 页面中显示文本
        TextView tipText = (TextView) view.findViewById(R.id.dialogMessage);
        tipText.setText(message);
        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            Log.i("WaitDialog","关闭等待框,重新加载");
        }
        loadingDialog = new Dialog(context);
        // 创建自定义样式的Dialog
        loadingDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_8, null));
        // 设置返回键无效
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        loadingDialog.show();
    }

    public static void onDismiss(){
        if (loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog.dismiss();
            loadingDialog = null;
            Log.i("WaitDialog","关闭等待框");
        }
    }
}
