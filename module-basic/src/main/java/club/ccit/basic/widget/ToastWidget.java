package club.ccit.basic.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import club.ccit.basic.R;

/**
 * FileName: ToastWidget
 *
 * @author: 张帅威
 * Date: 2022/9/9 10:38
 * Description:
 * Version:
 */
public interface ToastWidget {

    /**
     * 自定义toast
     *
     * @param message 内容
     */
    default void showToast(String message) {
        if (message != null ) {
            if (getContext() != null){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_toast, null);
                TextView text = view.findViewById(R.id.toastTextView);
                text.setText(message);
                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
            }
        }
    }

    /**
     * 获取上下文
     *
     * @return
     */
    default Context getContext() {
        synchronized (ToastWidget.class) {
            try {
                @SuppressLint("PrivateApi") Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
                Method method = ActivityThread.getMethod("currentActivityThread");
                //获取currentActivityThread 对象
                Object currentActivityThread = method.invoke(ActivityThread);
                Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                //获取 Context对象
                return (Context) method2.invoke(currentActivityThread);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
