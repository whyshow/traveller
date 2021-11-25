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

/**
 * @author: 张帅威
 * Date: 2021/11/23 08:16
 * Description: Fragment 基类
 * Version:
 */
public abstract class BaseFragment <T extends ViewBinding> extends Fragment {
    protected T binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding();
        return binding.getRoot();
    }

    /**
     * 设置点击事件
     */
    protected void initListener() {
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
            View view = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_toast, null);
            TextView text = (TextView) view.findViewById(R.id.toastTextView);
            text.setText(message);
            Toast toast = new Toast(requireActivity());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
