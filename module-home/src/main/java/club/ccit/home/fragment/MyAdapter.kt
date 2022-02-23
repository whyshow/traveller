package club.ccit.home.fragment

import club.ccit.basic.KtBaseAdapter
import club.ccit.common.LogUtils
import android.view.ViewGroup
import android.view.LayoutInflater
import club.ccit.home.databinding.ItemMyBinding
import club.ccit.sdk.demo.NewsListBean

/**
 * FileName: TestAdapter
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 1:17 下午
 * Description:
 * Version:
 */
class MyAdapter(list: MutableList<*>?) : KtBaseAdapter<ItemMyBinding?>() {
    /**
     * 绑定数据
     * @param position
     */
    override fun onBindingViewData(position: Int) {
        val bean = list!![position] as NewsListBean.Result
        // 设置文本数据
        binding!!.titleTextView.setText(bean.article_title)
        binding!!.timeTextView.text = bean.article_date
        binding!!.contentTextView.text = bean.article_text
        binding!!.authorTextView.text = bean.article_user + "/文"
        binding!!.itemCardView.setOnClickListener { LogUtils.i("  " + bean.article_title) }
    }

    /**
     * 绑定视图
     * @param parent
     * @return
     */
    override fun getViewBinding(parent: ViewGroup?): ItemMyBinding {
        return ItemMyBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
    }

    /**
     * 实例化
     * @param list
     */
    init {
        this.list = list
    }
}