package club.ccit.widget.action;

import android.view.View;

import club.ccit.widget.dialog.MessageDialog;
import club.ccit.widget.dialog.base.BaseDialog;


/**
 * FileName: ClickAction
 *
 * @author: 张帅威
 * Date: 2022/2/9 4:50 下午
 * Description: 点击事件意图
 * Version:
 */
public interface OnClickAction {

    /**
     * 事件
     * @param view
     * @param dialog
     */
    void onClickListener(View view, BaseDialog dialog);
}