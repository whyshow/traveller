package club.ccit.basic.holder;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * FileName: BaseHolder
 *
 * @author: 张帅威
 * Date: 2022/3/29 14:32
 * Description:
 * Version:
 */
public class BaseHolder <V extends ViewBinding> extends RecyclerView.ViewHolder {
    private V binding;

    public BaseHolder(V viewBinding) {
        super(viewBinding.getRoot());
        this.binding = viewBinding;
    }

    public V getViewBinding() {
        return binding;
    }
}
