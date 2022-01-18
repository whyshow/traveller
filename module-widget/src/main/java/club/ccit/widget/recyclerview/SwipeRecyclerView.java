package club.ccit.widget.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import club.ccit.widget.recyclerview.touch.DefaultItemTouchHelper;
import club.ccit.widget.recyclerview.touch.OnItemMoveListener;
import club.ccit.widget.recyclerview.touch.OnItemMovementListener;
import club.ccit.widget.recyclerview.touch.OnItemStateChangedListener;
import club.ccit.widget.recyclerview.widget.DefaultLoadMoreView;

/**
 * FileName: SwipeRecyclerView
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:07 上午
 * Description: 侧滑RecyclerView
 * Version:
 */
public class SwipeRecyclerView extends RecyclerView {

    /**
     * Left menu.
     */
    public static final int LEFT_DIRECTION = 1;
    /**
     * Right menu.
     */
    public static final int RIGHT_DIRECTION = -1;

    @IntDef({LEFT_DIRECTION, RIGHT_DIRECTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionMode {}

    /**
     * Invalid position.
     */
    private static final int INVALID_POSITION = -1;

    protected int mScaleTouchSlop;
    protected SwipeMenuLayout mOldSwipedLayout;
    protected int mOldTouchedPosition = INVALID_POSITION;

    private int mDownX;
    private int mDownY;

    private boolean allowSwipeDelete;

    private DefaultItemTouchHelper mItemTouchHelper;

    private SwipeMenuCreator mSwipeMenuCreator;
    private OnItemMenuClickListener mOnItemMenuClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private AdapterWrapper mAdapterWrapper;

    private boolean mSwipeItemMenuEnable = true;
    private List<Integer> mDisableSwipeItemMenuList = new ArrayList<>();

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void initializeItemTouchHelper() {
        if (mItemTouchHelper == null) {
            mItemTouchHelper = new DefaultItemTouchHelper();
            mItemTouchHelper.attachToRecyclerView(this);
        }
    }

    /**
     * Set OnItemMoveListener.
     *
     * @param listener {@link OnItemMoveListener}.
     */
    public void setOnItemMoveListener(OnItemMoveListener listener) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.setOnItemMoveListener(listener);
    }

    /**
     * Set OnItemMovementListener.
     *
     * @param listener {@link OnItemMovementListener}.
     */
    public void setOnItemMovementListener(OnItemMovementListener listener) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.setOnItemMovementListener(listener);
    }

    /**
     * Set OnItemStateChangedListener.
     *
     * @param listener {@link OnItemStateChangedListener}.
     */
    public void setOnItemStateChangedListener(OnItemStateChangedListener listener) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.setOnItemStateChangedListener(listener);
    }

    /**
     * 设置项目菜单以启用状态。
     *
     * @param enabled true means available, otherwise not available; default is true.
     */
    public void setSwipeItemMenuEnabled(boolean enabled) {
        this.mSwipeItemMenuEnable = enabled;
    }

    /**
     * True意味着可用，否则不可用;默认是正确的。
     */
    public boolean isSwipeItemMenuEnabled() {
        return mSwipeItemMenuEnable;
    }

    /**
     * 将项目菜单设置为启用状态。
     *
     * @param position the position of the item.
     * @param enabled true means available, otherwise not available; default is true.
     */
    public void setSwipeItemMenuEnabled(int position, boolean enabled) {
        if (enabled) {
            if (mDisableSwipeItemMenuList.contains(position)) {
                mDisableSwipeItemMenuList.remove(Integer.valueOf(position));
            }
        } else {
            if (!mDisableSwipeItemMenuList.contains(position)) {
                mDisableSwipeItemMenuList.add(position);
            }
        }
    }

    /**
     * True意味着可用，否则不可用;默认是正确的。
     *
     * @param position the position of the item.
     */
    public boolean isSwipeItemMenuEnabled(int position) {
        return !mDisableSwipeItemMenuList.contains(position);
    }

    /**
     * 设置可长按拖拽
     *
     * @param canDrag drag true, otherwise is can't.
     */
    public void setLongPressDragEnabled(boolean canDrag) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.setLongPressDragEnabled(canDrag);
    }

    /**
     * 获取长按是否可以拖拽
     *
     * @return drag true, otherwise is can't.
     */
    public boolean isLongPressDragEnabled() {
        initializeItemTouchHelper();
        return this.mItemTouchHelper.isLongPressDragEnabled();
    }


    /**
     * 设置itemView 是否可以删除.
     *
     * @param canSwipe swipe true, otherwise is can't.
     */
    public void setItemViewSwipeEnabled(boolean canSwipe) {
        initializeItemTouchHelper();
        allowSwipeDelete = canSwipe; // swipe and menu conflict.
        this.mItemTouchHelper.setItemViewSwipeEnabled(canSwipe);
    }

    /**
     * 获取item是否可以长按
     *
     * @return swipe true, otherwise is can't.
     */
    public boolean isItemViewSwipeEnabled() {
        initializeItemTouchHelper();
        return this.mItemTouchHelper.isItemViewSwipeEnabled();
    }

    /**
     * 启动拖拽
     *
     * @param viewHolder 让ViewHolder开始拖拽。它必须是RecyclerView的直接子节点。
     */
    public void startDrag(ViewHolder viewHolder) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.startDrag(viewHolder);
    }

    /**
     * 启动滑动
     * @param viewHolder 让ViewHolder开始滑动。它必须是RecyclerView的直接子节点
     */
    public void startSwipe(ViewHolder viewHolder) {
        initializeItemTouchHelper();
        this.mItemTouchHelper.startSwipe(viewHolder);
    }

    /**
     * 检查适配器，如果已经存在则抛出异常
     * @param message
     */
    private void checkAdapterExist(String message) {
        if (mAdapterWrapper != null){
            throw new IllegalStateException(message);
        }
    }

    /**
     * 设置项单击监听器
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        if (listener == null){
            return;
        }
        checkAdapterExist("Cannot set item click listener, setAdapter has already been called.");
        this.mOnItemClickListener = new ItemClickListener(this, listener);
    }

    private static class ItemClickListener implements OnItemClickListener {

        private SwipeRecyclerView mRecyclerView;
        private OnItemClickListener mListener;

        public ItemClickListener(SwipeRecyclerView recyclerView, OnItemClickListener listener) {
            this.mRecyclerView = recyclerView;
            this.mListener = listener;
        }

        @Override
        public void onItemClick(View itemView, int position) {
            position -= mRecyclerView.getHeaderCount();
            if (position >= 0){
                mListener.onItemClick(itemView, position);
            }
        }
    }

    /**
     * 设置项目点击监听
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        if (listener == null){
            return;
        }
        checkAdapterExist("Cannot set item long click listener, setAdapter has already been called.");
        this.mOnItemLongClickListener = new ItemLongClickListener(this, listener);
    }

    private static class ItemLongClickListener implements OnItemLongClickListener {

        private SwipeRecyclerView mRecyclerView;
        private OnItemLongClickListener mListener;

        public ItemLongClickListener(SwipeRecyclerView recyclerView, OnItemLongClickListener listener) {
            this.mRecyclerView = recyclerView;
            this.mListener = listener;
        }

        @Override
        public void onItemLongClick(View itemView, int position) {
            position -= mRecyclerView.getHeaderCount();
            if (position >= 0){
                mListener.onItemLongClick(itemView, position);
            }
        }
    }

    /**
     * 设置创建菜单监听器
     * @param menuCreator
     */
    public void setSwipeMenuCreator(SwipeMenuCreator menuCreator) {
        if (menuCreator == null){
            return;
        }
        checkAdapterExist("Cannot set menu creator, setAdapter has already been called.");
        this.mSwipeMenuCreator = menuCreator;
    }

    /**
     * 设置菜单点击事件
     * @param listener
     */
    public void setOnItemMenuClickListener(OnItemMenuClickListener listener) {
        if (listener == null){
            return;
        }
        checkAdapterExist("Cannot set menu item click listener, setAdapter has already been called.");
        this.mOnItemMenuClickListener = new ItemMenuClickListener(this, listener);
    }

    private static class ItemMenuClickListener implements OnItemMenuClickListener {

        private final SwipeRecyclerView mRecyclerView;
        private final OnItemMenuClickListener mListener;

        public ItemMenuClickListener(SwipeRecyclerView recyclerView, OnItemMenuClickListener listener) {
            this.mRecyclerView = recyclerView;
            this.mListener = listener;
        }

        /**
         * item 点击事件
         * @param menuBridge menu bridge.
         * @param position 位置
         */
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            position -= mRecyclerView.getHeaderCount();
            if (position >= 0) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                mListener.onItemClick(menuBridge, position);
            }
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookupHolder = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mAdapterWrapper.isHeader(position) || mAdapterWrapper.isFooter(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookupHolder != null) {
                        return spanSizeLookupHolder.getSpanSize(position - getHeaderCount());
                    }
                    return 1;
                }
            });
        }
        super.setLayoutManager(layoutManager);
    }

    /**
     * 获取原始的适配器
     */
    public Adapter getOriginAdapter() {
        if (mAdapterWrapper == null){
            return null;
        }
        return mAdapterWrapper.getOriginAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapterWrapper != null) {
            mAdapterWrapper.getOriginAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
        }

        if (adapter == null) {
            mAdapterWrapper = null;
        } else {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);

            mAdapterWrapper = new AdapterWrapper(getContext(), adapter);
            mAdapterWrapper.setOnItemClickListener(mOnItemClickListener);
            mAdapterWrapper.setOnItemLongClickListener(mOnItemLongClickListener);
            mAdapterWrapper.setSwipeMenuCreator(mSwipeMenuCreator);
            mAdapterWrapper.setOnItemMenuClickListener(mOnItemMenuClickListener);

            if (mHeaderViewList.size() > 0) {
                for (View view : mHeaderViewList) {
                    mAdapterWrapper.addHeaderView(view);
                }
            }
            if (mFooterViewList.size() > 0) {
                for (View view : mFooterViewList) {
                    mAdapterWrapper.addFooterView(view);
                }
            }
        }
        super.setAdapter(mAdapterWrapper);
    }

    private final AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChanged() {
            mAdapterWrapper.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            positionStart += getHeaderCount();
            mAdapterWrapper.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            positionStart += getHeaderCount();
            mAdapterWrapper.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            positionStart += getHeaderCount();
            mAdapterWrapper.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            positionStart += getHeaderCount();
            mAdapterWrapper.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            fromPosition += getHeaderCount();
            toPosition += getHeaderCount();
            mAdapterWrapper.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private final List<View> mHeaderViewList = new ArrayList<>();
    private final List<View> mFooterViewList = new ArrayList<>();

    /**
     * 添加header视图
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViewList.add(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.addHeaderViewAndNotify(view);
        }
    }

    /**
     * 移除 header 视图
     * @param view
     */
    public void removeHeaderView(View view) {
        mHeaderViewList.remove(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.removeHeaderViewAndNotify(view);
        }
    }

    /**
     * 在 footer 添加视图
     * @param view
     */
    public void addFooterView(View view) {
        mFooterViewList.add(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.addFooterViewAndNotify(view);
        }
    }

    /**
     * 移除 footer 视图
     * @param view
     */
    public void removeFooterView(View view) {
        mFooterViewList.remove(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.removeFooterViewAndNotify(view);
        }
    }

    /**
     * 获取Header的数量
     */
    public int getHeaderCount() {
        if (mAdapterWrapper == null){
            return 0;
        }
        return mAdapterWrapper.getHeaderCount();
    }

    /**
     * 获取footer的数量
     */
    public int getFooterCount() {
        if (mAdapterWrapper == null){
            return 0;
        }
        return mAdapterWrapper.getFooterCount();
    }

    /**
     * 获取项目的视图类型
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        if (mAdapterWrapper == null){
            return 0;
        }
        return mAdapterWrapper.getItemViewType(position);
    }

    /**
     * 打开左边的菜单
     * @param position
     */
    public void smoothOpenLeftMenu(int position) {
        smoothOpenMenu(position, LEFT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    /**
     * 打开左边的菜单
     * @param position position.
     * @param duration time millis.
     */
    public void smoothOpenLeftMenu(int position, int duration) {
        smoothOpenMenu(position, LEFT_DIRECTION, duration);
    }

    /**
     * 打开右边的菜单
     * @param position position.
     */
    public void smoothOpenRightMenu(int position) {
        smoothOpenMenu(position, RIGHT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    /**
     * 打开右边的菜单
     * @param position position.
     * @param duration time millis.
     */
    public void smoothOpenRightMenu(int position, int duration) {
        smoothOpenMenu(position, RIGHT_DIRECTION, duration);
    }

    /**
     * 打开菜单.
     *
     * @param position position.
     * @param direction use {@link #LEFT_DIRECTION}, {@link #RIGHT_DIRECTION}.
     * @param duration time millis.
     */
    public void smoothOpenMenu(int position, @DirectionMode int direction, int duration) {
        if (mOldSwipedLayout != null) {
            if (mOldSwipedLayout.isMenuOpen()) {
                mOldSwipedLayout.smoothCloseMenu();
            }
        }
        position += getHeaderCount();
        ViewHolder vh = findViewHolderForAdapterPosition(position);
        if (vh != null) {
            View itemView = getSwipeMenuView(vh.itemView);
            if (itemView instanceof SwipeMenuLayout) {
                mOldSwipedLayout = (SwipeMenuLayout)itemView;
                if (direction == RIGHT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenRightMenu(duration);
                } else if (direction == LEFT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenLeftMenu(duration);
                }
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void smoothCloseMenu() {
        if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
            mOldSwipedLayout.smoothCloseMenu();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean isIntercepted = super.onInterceptTouchEvent(e);
        if (allowSwipeDelete || mSwipeMenuCreator == null) {
            return isIntercepted;
        } else {
            if (e.getPointerCount() > 1) {
                return true;
            }
            int action = e.getAction();
            int x = (int)e.getX();
            int y = (int)e.getY();

            int touchPosition = getChildAdapterPosition(findChildViewUnder(x, y));
            ViewHolder touchVH = findViewHolderForAdapterPosition(touchPosition);
            SwipeMenuLayout touchView = null;
            if (touchVH != null) {
                View itemView = getSwipeMenuView(touchVH.itemView);
                if (itemView instanceof SwipeMenuLayout) {
                    touchView = (SwipeMenuLayout)itemView;
                }
            }

            boolean touchMenuEnable = mSwipeItemMenuEnable && !mDisableSwipeItemMenuList.contains(touchPosition);
            if (touchView != null) {
                touchView.setSwipeEnable(touchMenuEnable);
            }
            if (!touchMenuEnable){
                return isIntercepted;
            }

            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mDownX = x;
                    mDownY = y;

                    isIntercepted = false;
                    if (touchPosition != mOldTouchedPosition && mOldSwipedLayout != null &&
                            mOldSwipedLayout.isMenuOpen()) {
                        mOldSwipedLayout.smoothCloseMenu();
                        isIntercepted = true;
                    }

                    if (isIntercepted) {
                        mOldSwipedLayout = null;
                        mOldTouchedPosition = INVALID_POSITION;
                    } else if (touchView != null) {
                        mOldSwipedLayout = touchView;
                        mOldTouchedPosition = touchPosition;
                    }
                    break;
                }
                // They are sensitive to retain sliding and inertia.
                case MotionEvent.ACTION_MOVE: {
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    if (mOldSwipedLayout == null) {
                        break;
                    }
                    ViewParent viewParent = getParent();
                    if (viewParent == null) {
                        break;
                    }

                    int disX = mDownX - x;
                    // 向左滑，显示右侧菜单，或者关闭左侧菜单。
                    boolean showRightCloseLeft = disX > 0 &&
                            (mOldSwipedLayout.hasRightMenu() || mOldSwipedLayout.isLeftCompleteOpen());
                    // 向右滑，显示左侧菜单，或者关闭右侧菜单。
                    boolean showLeftCloseRight = disX < 0 &&
                            (mOldSwipedLayout.hasLeftMenu() || mOldSwipedLayout.isRightCompleteOpen());
                    viewParent.requestDisallowInterceptTouchEvent(showRightCloseLeft || showLeftCloseRight);
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    break;
                }
            }
        }
        return isIntercepted;
    }

    private boolean handleUnDown(int x, int y, boolean defaultValue) {
        int disX = mDownX - x;
        int disY = mDownY - y;

        // swipe
        if (Math.abs(disX) > mScaleTouchSlop && Math.abs(disX) > Math.abs(disY)){
            return false;
        }
        // click
        if (Math.abs(disY) < mScaleTouchSlop && Math.abs(disX) < mScaleTouchSlop){
            return false;
        }
        return defaultValue;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                    mOldSwipedLayout.smoothCloseMenu();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(e);
    }

    private View getSwipeMenuView(View itemView) {
        if (itemView instanceof SwipeMenuLayout) {
            return itemView;
        }
        List<View> unvisited = new ArrayList<>();
        unvisited.add(itemView);
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (!(child instanceof ViewGroup)) { // view
                continue;
            }
            if (child instanceof SwipeMenuLayout) {
                return child;
            }
            ViewGroup group = (ViewGroup)child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                unvisited.add(group.getChildAt(i));
            }
        }
        return itemView;
    }

    private int mScrollState = -1;

    private boolean isLoadMore = false;
    private boolean isAutoLoadMore = true;
    private boolean isLoadError = false;

    private boolean mDataEmpty = true;
    private boolean mHasMore = false;

    private LoadMoreView mLoadMoreView;
    private LoadMoreListener mLoadMoreListener;

    @Override
    public void onScrollStateChanged(int state) {
        this.mScrollState = state;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;

            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) {
                return;
            }

            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();

            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == SCROLL_STATE_DRAGGING || mScrollState == SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)layoutManager;

            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0){
                return;
            }

            int[] lastVisiblePositionArray = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
            int lastVisiblePosition = lastVisiblePositionArray[lastVisiblePositionArray.length - 1];

            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == SCROLL_STATE_DRAGGING || mScrollState == SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        }
    }

    private void dispatchLoadMore() {
        if (isLoadError){
            return;
        }

        if (!isAutoLoadMore) {
            if (mLoadMoreView != null){
                mLoadMoreView.onWaitToLoadMore(mLoadMoreListener);
            }
        } else {
            if (isLoadMore || mDataEmpty || !mHasMore) {
                return;
            }

            isLoadMore = true;

            if (mLoadMoreView != null){
                mLoadMoreView.onLoading();
            }

            if (mLoadMoreListener != null){
                mLoadMoreListener.onLoadMore();
            }
        }
    }

    /**
     * 使用默认值加载更多视图.
     */
    public void useDefaultLoadMore() {
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        addFooterView(defaultLoadMoreView);
        setLoadMoreView(defaultLoadMoreView);
    }

    /**
     * 加载更多视图.
     */
    public void setLoadMoreView(LoadMoreView view) {
        mLoadMoreView = view;
    }

    /**
     * 加载更多监听.
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    /**
     * 自动加载更自动
     * <p>
     * 非自动加载模式，您可以单击要加载的项目。
     * </p>
     *
     * @param autoLoadMore 你可以使用false.
     *
     * @see LoadMoreView#onWaitToLoadMore(LoadMoreListener)
     */
    public void setAutoLoadMore(boolean autoLoadMore) {
        isAutoLoadMore = autoLoadMore;
    }

    /**
     * 完成加载更多
     *
     * @param dataEmpty 数据为空 ?
     * @param hasMore 有更多数据 ?
     */
    public final void loadMoreFinish(boolean dataEmpty, boolean hasMore) {
        isLoadMore = false;
        isLoadError = false;

        mDataEmpty = dataEmpty;
        mHasMore = hasMore;

        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadFinish(dataEmpty, hasMore);
        }
    }

    /**
     * 数据加载不正确时调用
     *
     * @param errorCode 错误码，会传递给LoadView，你可以根据它来定制提示信息
     * @param errorMessage Error message.
     */
    public void loadMoreError(int errorCode, String errorMessage) {
        isLoadMore = false;
        isLoadError = true;

        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadError(errorCode, errorMessage);
        }
    }

    public interface LoadMoreView {

        /**
         * 显示进展
         */
        void onLoading();

        /**
         * 加载完成, 返回结果
         * @param dataEmpty
         * @param hasMore
         */
        void onLoadFinish(boolean dataEmpty, boolean hasMore);

        /**
         * 非自动加载模式，您可以单击要加载的项目。
         */
        void onWaitToLoadMore(LoadMoreListener loadMoreListener);

        /**
         * 加载错误.
         */
        void onLoadError(int errorCode, String errorMessage);
    }

    public interface LoadMoreListener {

        /**
         * 加载更多.
         */
        void onLoadMore();
    }

}
