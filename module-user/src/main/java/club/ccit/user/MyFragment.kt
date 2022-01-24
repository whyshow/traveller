package club.ccit.user

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import club.ccit.basic.KtBaseFragment
import club.ccit.sdk.demo.NewsApi
import club.ccit.sdk.demo.NewsApiProvider
import club.ccit.sdk.net.AndroidObservable
import club.ccit.sdk.demo.NewsListBean
import club.ccit.sdk.net.AbstractApiObserver
import club.ccit.widget.swipe.SwipeMenuCreator
import club.ccit.widget.swipe.SwipeMenuItem
import club.ccit.widget.swipe.OnItemMenuClickListener
import club.ccit.common.LogUtils
import club.ccit.user.databinding.FragmentMyBinding

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:27 上午
 * Description: 我的
 * Version:
 */
class MyFragment : KtBaseFragment<FragmentMyBinding?>() {
    private var api: NewsApi? = null
    private var adapter: MyAdapter? = null
    override fun onStart() {
        super.onStart()
        binding!!.swipeRecyclerView.layoutManager =
            StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
        api = NewsApiProvider().newsList
        initData(1)
    }

    private fun initData(p: Int) {
        AndroidObservable.create(api!!.getNewsList(p)).with(this)
            .subscribe(object : AbstractApiObserver<NewsListBean?>() {
                protected override fun succeed(t: NewsListBean?) {
                    if (adapter == null) {
                        if (t != null) {
                            adapter = MyAdapter(t.result)
                        }
                        binding!!.swipeRecyclerView.adapter = adapter
                    }
                }

                override fun error(code: Int, message: String) {

                }
            })
    }

    override fun initListener() {
        super.initListener()
        // 设置监听器。
        binding!!.swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator)

        // 菜单点击监听。
        binding!!.swipeRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener)
    }

    /**
     * 创建菜单
     */
    private var mSwipeMenuCreator = SwipeMenuCreator { leftMenu, rightMenu, position ->
        val deleteItem = SwipeMenuItem(requireContext()) // 各种文字和图标属性设置。
        deleteItem.text = "删除"
        rightMenu.addMenuItem(deleteItem) // 在Item右侧添加一个菜单。

        // 注意：哪边不想要菜单，那么不要添加即可。
    }

    /**
     * 设置监听
     */
    private var mItemMenuClickListener = OnItemMenuClickListener { menuBridge, position ->
        LogUtils.i("位置：$position")
        // 左侧还是右侧菜单：
        val direction = menuBridge.direction
        // 菜单在Item中的Position：
        val menuPosition = menuBridge.position
    }
    override val viewBinding: FragmentMyBinding?
        get() = FragmentMyBinding.inflate(layoutInflater)
}