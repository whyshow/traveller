package club.ccit.widget.swipe;

/**
 * FileName: SwipeMenuBridge
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:19 上午
 * Description:
 * Version:
 */
public class SwipeMenuBridge {
    private final Controller mController;
    private final int mDirection;
    private final int mPosition;

    public SwipeMenuBridge(Controller controller, int direction, int position) {
        this.mController = controller;
        this.mDirection = direction;
        this.mPosition = position;
    }

    @SwipeRecyclerView.DirectionMode
    public int getDirection() {
        return mDirection;
    }

    /**
     * 获取菜单中按钮的位置
     */
    public int getPosition() {
        return mPosition;
    }

    public void closeMenu() {
        mController.smoothCloseMenu();
    }
}
