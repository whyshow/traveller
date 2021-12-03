package club.ccit.drafts;

import club.ccit.basic.BaseFragment;
import club.ccit.drafts.databinding.FragmentDraftsBinding;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:50 上午
 * Description:
 * Version:
 */
public class DraftsFragment extends BaseFragment<FragmentDraftsBinding> {
    @Override
    protected FragmentDraftsBinding getViewBinding() {
        return FragmentDraftsBinding.inflate(getLayoutInflater());
    }
}
