package club.ccit.widget.recyclerview;

/**
 * FileName: SwipeMenuCreator
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:20 上午
 * Description:
 * Version:
 */
public interface SwipeMenuCreator {
    /**
     * 为recyclerVie项目创建菜单。
     *
     * @param leftMenu the menu on the left.
     * @param rightMenu the menu on the right.
     * @param position the position of item.
     */
    void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position);
}
