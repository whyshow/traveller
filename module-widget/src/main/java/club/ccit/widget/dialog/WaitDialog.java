package club.ccit.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import club.ccit.widget.R;
import club.ccit.widget.dialog.base.BaseDialog;

/**
 * @author: 张帅威
 * Date: 2021/11/24 10:19
 * Description: 等待弹窗
 * Version:
 */
public class WaitDialog extends BaseDialog {
    private static String message;
    public static WaitDialog Builder;
    public static Timer timer;

    public WaitDialog(Context context) {
        super(context);
        Builder = this;
    }

    public static WaitDialog Builder(Context context, String m) {
        if (Builder == null) {
            message = m;
            new WaitDialog(context);
            setTiming();
        }
        return Builder;
    }

    public static void onDismiss() {
        if (Builder != null) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            Builder.onDialogDismiss();
        }
    }

    @Override
    public void onDialogDismiss() {
        super.onDialogDismiss();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        Builder = null;
    }

    @Override
    public void showDialog() {
        super.showDialog();
        show();
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
        return R.drawable.radius_gray_8;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void initView() {
        TextView tipText = findViewById(R.id.dialogMessage);
        tipText.setText(message);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.layout_wait_dialog;
    }

    private static void setTiming() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Builder.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                onDismiss();
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                }
            }, 3000);
        }
    }

}
