package club.ccit.basic

import android.annotation.SuppressLint
import androidx.viewbinding.ViewBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment

/**
 * @author: 瞌睡的牙签
 * Date: 2021/11/23 08:16
 * Description: Fragment 基类
 * Version:
 */
abstract class KtBaseFragment<T : ViewBinding?> : Fragment() {
    var binding: T? = null
    private var isFragmentViewInit = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = viewBinding
            initView()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentViewInit) {
            super.onViewCreated(view, savedInstanceState)
            isFragmentViewInit = true
        }
    }

    /**
     * 设置点击事件
     */
    protected open fun initView() {}

    /**
     * 视图绑定
     *
     * @return ActivityXXXBinding.inflate(getLayoutInflater ());
     */
    protected abstract val viewBinding: T

    /**
     * 自定义Toast
     *
     * @param message
     */
    @SuppressLint("InflateParams")
    fun myToast(message: String?) {
        if (message != null) {
            val view = LayoutInflater.from(requireActivity().application)
                .inflate(R.layout.layout_toast, null)
            val text = view.findViewById<View>(R.id.toastTextView) as TextView
            text.text = message
            val toast = Toast(requireActivity().application)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = view
            toast.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}