package club.ccit.user;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import club.ccit.basic.BaseAdapter;
import club.ccit.basic.BaseFragment;
import club.ccit.common.LogUtils;
import club.ccit.sdk.demo.NewsApi;
import club.ccit.sdk.demo.NewsApiProvider;
import club.ccit.sdk.demo.NewsListBean;
import club.ccit.sdk.net.AndroidObservable;
import club.ccit.sdk.net.DefaultApiObserver;
import club.ccit.user.databinding.FragmentMyBinding;
import club.ccit.widget.recyclerview.OnItemMenuClickListener;
import club.ccit.widget.recyclerview.SwipeMenu;
import club.ccit.widget.recyclerview.SwipeMenuBridge;
import club.ccit.widget.recyclerview.SwipeMenuCreator;
import club.ccit.widget.recyclerview.SwipeMenuItem;

/**
 * @author: 瞌睡的牙签
 * Date: 2021/12/3 8:27 上午
 * Description: 我的
 * Version:
 */
public class MyFragment extends BaseFragment<FragmentMyBinding> {
    private NewsApi api;
    private MyAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        binding.swipeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        api = new NewsApiProvider().getNewsList();
        initData(1);
    }

    private void initData(int p) {
        AndroidObservable.create(api.getNewsList(p)).with(this).subscribe(new DefaultApiObserver<NewsListBean>() {
            @Override
            protected void succeed(NewsListBean newsListBean) {
                if (adapter == null) {
                    adapter = new MyAdapter(newsListBean.getResult());
                    binding.swipeRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            protected void error(int code, String message) {

            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 设置监听器。
        binding.swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        binding.swipeRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
    }

    /**
     * 创建菜单
     */
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(requireContext()); // 各种文字和图标属性设置。
            deleteItem.setText("删除");
            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。

            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };

    /**
     * 设置监听
     */
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {

            LogUtils.i("位置："+position);
            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };

    @Override
    protected FragmentMyBinding getViewBinding() {
        return FragmentMyBinding.inflate(getLayoutInflater());
    }
}
