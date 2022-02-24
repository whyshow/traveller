package club.ccit.basic;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import club.ccit.basic.action.ClickAction;

/**
 * @author: 张帅威
 * Date: 2021/11/18 10:33
 * Description: Activity 基类
 * Version:
 */
public abstract class BaseBindingActivity <T extends ViewDataBinding> extends AppCompatActivity implements ClickAction {
    protected T binding;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 视图
        if (setLayoutId() == 0){
            binding = getViewBinding();
            setContentView(binding.getRoot());
            initView();
        }else {
            binding = DataBindingUtil.setContentView(this, setLayoutId());
            binding.setLifecycleOwner(this);
        }
        // 禁止屏幕翻转
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return binding.getRoot().findViewById(id);
    }

    protected int setLayoutId(){
        return 0;
    }
    /**
     * 设置点击事件
     */
    protected void initView() {
    }

    /**
     * 视图绑定
     *
     * @return ActivityXXXBinding.inflate(getLayoutInflater ());
     */
    protected abstract T getViewBinding();

    /**
     * 状态栏颜色
     *
     * @return
     */
    protected int setBarColor(){
        return 0;
    }

    /**
     * 是否开启状态栏图标深色
     *
     * @return
     */
    protected  boolean setStatusBarDarkFont(){
        return false;
    }

    /**
     * 设置状态栏图标深色
     *
     * @param setDark
     */
    public void changStatusIconColor(boolean setDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (setDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 自定义Toast
     *
     * @param message
     */
    public void myToast(String message) {
        if (message != null) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.layout_toast, null);
            TextView text = view.findViewById(R.id.toastTextView);
            text.setText(message);
            Toast toast = new Toast(getApplication());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
