package club.ccit.user;

import android.widget.TextView;

import club.ccit.basic.BaseFragment;
import club.ccit.user.databinding.FragmentMyBinding;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:27 上午
 * Description: 我的
 * Version:
 */
public class MyFragment extends BaseFragment<FragmentMyBinding> {

    @Override
    protected void initListener() {
        super.initListener();
        TextView textView = binding.userTextView;
    }

    @Override
    protected FragmentMyBinding getViewBinding() {
        return FragmentMyBinding.inflate(getLayoutInflater());
    }
}
