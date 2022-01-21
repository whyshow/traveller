package club.ccit.basic

import androidx.viewbinding.ViewBinding
import android.view.ViewGroup
import club.ccit.basic.KtBaseAdapter
import club.ccit.basic.holder.FooterViewHolder
import android.view.LayoutInflater
import club.ccit.basic.R
import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 * FileName: BaseAdapter
 *
 * @author: 张帅威
 * Date: 2022/1/17 9:12 上午
 * Description:
 * Version:
 */
abstract class KtBaseAdapter<T : ViewBinding?> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var binding: T? = null
    var list: MutableList<*>? = null
    private var itemCount = 0
    private val remedy = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            FooterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
            )
        } else {
            binding = getViewBinding(parent)
            ViewHolder(binding!!)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position + remedy <= itemCount) {
            if (list!![position] != null) {
                onBindingViewData(position)
            }
        } else {
            val noneViewHolder = holder as FooterViewHolder
            when (TYPE) {
                TYPE_ERROR_FOOTER -> {
                    noneViewHolder.noDataTextView.text = "加载错误"
                    return
                }
                TYPE_LOADING_FOOTER -> {
                    noneViewHolder.noDataTextView.text = "加载中"
                    return
                }
                TYPE_NONE_FOOTER -> {
                    noneViewHolder.noDataTextView.text = "没有更多了"
                    return
                }
                else -> {
                }
            }
            noneViewHolder.noDataTextView.setOnClickListener { Log.i("LOG111", "点击了") }
        }
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    fun onAppointAllData(list: MutableList<*>) {
        val location = list.size - this.list!!.size
        this.list = list
        notifyItemRangeChanged(list.size - location, location)
        notifyDataSetChanged()
    }

    /**
     * 添加item数据
     *
     * @param list 已经添加过数据列表
     */
    @SuppressLint("NotifyDataSetChanged")
    fun onAppointData(list: MutableList<*>) {
        for (i in list.indices) {
            this.list?.add(this.list!!.size, list[i] as Nothing)
        }
        notifyItemRangeInserted(this.list!!.size - list.size, list.size)
        notifyDataSetChanged()
    }

    /**
     * 重新加载
     * @param list
     */
    @SuppressLint("NotifyDataSetChanged")
    fun onReload(list: MutableList<*>?) {
        this.list = list
        notifyDataSetChanged()
    }

    /**
     * 设置footer提示信息
     * @param type
     */
    fun setFooterView(type: Int) {
        TYPE = type
        notifyItemChanged(itemCount - 1)
    }

    /**
     * 绑定数据
     *
     * @param position 绑定
     */
    protected abstract fun onBindingViewData(position: Int)

    /**
     * 绑定视图
     * ItemTestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
     *
     * @param parent。
     * @return 实现
     */
    protected abstract fun getViewBinding(parent: ViewGroup?): T

    /**
     * 获取item 数量
     *
     * @return
     */
    override fun getItemCount(): Int {
        itemCount = list!!.size + 1
        return itemCount
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + remedy <= itemCount) {
            position
        } else {
            TYPE_FOOTER
        }
    }

    /**
     * 绑定控件
     */
    inner class ViewHolder(itemView: T) : RecyclerView.ViewHolder(
        itemView!!.root
    ) {
        init {
            binding = itemView
        }
    }

    companion object {
        const val TYPE_FOOTER = -1
        const val TYPE_ERROR_FOOTER = -2
        const val TYPE_LOADING_FOOTER = -3
        const val TYPE_NONE_FOOTER = -4
        var TYPE = -4
    }
}