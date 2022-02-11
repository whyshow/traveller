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
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import club.ccit.basic.action.ClickAction;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/23 08:16
 * Description: Fragment 基类
 * Version:
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment implements ClickAction {
    protected T binding;
    public boolean isFragmentViewInit = false;

    @Override
    public <V extends View> V findViewById(int id) {
        return getView().findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = getViewBinding();
           initView();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
