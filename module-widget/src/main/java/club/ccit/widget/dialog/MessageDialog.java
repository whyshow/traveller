package club.ccit.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import club.ccit.widget.R;
import club.ccit.widget.action.OnClickAction;
import club.ccit.widget.dialog.base.BaseDialog;

/**
 * FileName: NewMessageDialog
 *
 * @author: 张帅威
 * Date: 2022/2/10 2:10 下午
 * Description:
 * Version:
 */
public class MessageDialog extends BaseDialog implements View.OnClickListener{
    public static String cancelTextView = "取消";
    public static String ensureTextView = "确定";
    private static String messageTextView = "";
    private static Context context;
    private static OnClickAction action;
    public static MessageDialog Builder;
    public MessageDialog(Context context, OnClickAction a) {
        super(context);
        action = a;
        Builder = this;
    }

    @Override
    protected int setDialogAnimations() {
        return ANIM_SCALE;
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
        return R.drawable.radius_white_8;
    }

    @Override
    protected void initView() {
        TextView dialogCancel = findViewById(R.id.dialogCancel);
        TextView dialogEnsure = findViewById(R.id.dialogEnsure);
        TextView dialogMessage = findViewById(R.id.dialogMessage);
        if (dialogEnsure != null) {
            dialogEnsure.setOnClickListener(this);
            dialogEnsure.setText(ensureTextView);
        }
        if (dialogCancel != null) {
            dialogCancel.setOnClickListener(this);
            dialogCancel.setText(cancelTextView);
        }
        dialogMessage.setText(messageTextView);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.layout_message_dialog;
    }

    @Override
    public void onClick(View view) {
       action.onClickListener(view, MessageDialog.this);
    }

    public static BaseDialog Builder(Context c,String message, OnClickAction a){
        context = c;
        messageTextView = message;
        new MessageDialog(context,a);
        return Builder;
    }

    @Override
    protected boolean setDialogTransparent() {
        return false;
    }

    @Override
    protected int setDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public boolean setDialogCancelable() {
        return true;
    }

    @Override
    public void onDialogDismiss() {
        super.onDialogDismiss();
        action = null;
        Builder = null;
        context = null;
    }
}
