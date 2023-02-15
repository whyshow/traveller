package club.ccit.basic;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import club.ccit.basic.action.ClickAction;
import club.ccit.basic.widget.ToastWidget;

/**
 * FileName: BaseViewDataActivity
 *
 * @author: 张帅威
 * Date: 2023/1/10 09:52
 * Description:
 * Version:
 */
public abstract class BaseViewDataActivity<T extends ViewDataBinding> extends AppCompatActivity implements ClickAction, ToastWidget {
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 视图
        if (setLayoutId() == 0) {
            binding = onSetViewBinding();
            setContentView(binding.getRoot());
        } else {
            binding = DataBindingUtil.setContentView(this, setLayoutId());
            binding.setLifecycleOwner(this);
        }
        // 禁止屏幕翻转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onCreate();
    }

    protected void onCreate() {
    }

    /**
     * 寻找点击事件的id
     **/
    @Override
    public <T extends View> T findViewById(int id) {
        return binding.getRoot().findViewById(id);
    }

    /**
     * 返回具有dataBinding的布局
     **/
    protected int setLayoutId() {
        return 0;
    }

    /**
     * 视图绑定
     * 如果子类继承没有实现此方法以及没有返回 setLayoutId()
     * 那么将会以反射的形式进行绑定。
     * 性能可能会降低
     *
     * @return ActivityXXXBinding.inflate(getLayoutInflater ());
     */
    protected T onSetViewBinding() {
        return reflectViewBinding();
    }

    /**
     * 反射获取binding
     **/
    private T reflectViewBinding() {
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) method.invoke(null, getLayoutInflater());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return binding;
    }

    /**
     * 结束回调
     **/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}