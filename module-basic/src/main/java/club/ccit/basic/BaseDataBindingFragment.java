package club.ccit.basic;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import club.ccit.basic.action.ClickAction;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/23 08:16
 * Description: Fragment 基类
 * Version:
 */
public abstract class BaseDataBindingFragment<T extends ViewDataBinding> extends Fragment implements ClickAction {
    protected T binding;
    public boolean isFragmentViewInit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (setLayoutId() == 0){
            binding = onSetViewBinding();
            initView();
        }else {
            binding = DataBindingUtil.setContentView(requireActivity(), setLayoutId());
            binding.setLifecycleOwner(this);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!isFragmentViewInit) {
            super.onViewCreated(view, savedInstanceState);
            isFragmentViewInit = true;
        }
    }

    /** 寻找点击事件的id **/
    @Override
    public <T extends View> T findViewById(int id) {
        return getView().findViewById(id);
    }

    /** 返回具有dataBinding的布局 **/
    protected int setLayoutId(){
        return 0;
    }
    /** 初始化一些视图、数据等 **/
    protected void initView() {
    }

    /**
     * 视图绑定
     * 如果子类继承没有实现此方法以及没有返回 setLayoutId()
     * 那么将会以反射的形式进行绑定。
     * 性能可能会降低
     * @return ActivityXXXBinding.inflate(getLayoutInflater ());
     */
    protected T onSetViewBinding(){
        return reflectViewBinding();
    }

    /** 反射获取binding **/
    private T reflectViewBinding(){
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) method.invoke(null, getLayoutInflater());
        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            e.printStackTrace();
        }
        return binding;
    }

    /**
     * 自定义Toast
     *
     * @param message
     */
    public void myToast(String message) {
        if (message != null) {
            View view = LayoutInflater.from(requireActivity().getApplication()).inflate(R.layout.layout_toast, null);
            TextView text = (TextView) view.findViewById(R.id.toastTextView);
            text.setText(message);
            Toast toast = new Toast(requireActivity().getApplication());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
        }
    }

    /** 结束回调 **/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
