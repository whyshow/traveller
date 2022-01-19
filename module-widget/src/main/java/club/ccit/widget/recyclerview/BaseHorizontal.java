package club.ccit.widget.recyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * FileName: Horizontal
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:24 上午
 * Description:
 * Version:
 */
 public abstract class BaseHorizontal {
    private int direction;
    private View menuView;
    protected Checker mChecker;

    public BaseHorizontal(int direction, View menuView) {
        this.direction = direction;
        this.menuView = menuView;
        mChecker = new Checker();
    }

    public boolean canSwipe() {
        if (menuView instanceof ViewGroup) {
            return ((ViewGroup)menuView).getChildCount() > 0;
        }
        return false;
    }

    public boolean isCompleteClose(int scrollX) {
        int i = -getMenuView().getWidth() * getDirection();
        return scrollX == 0 && i != 0;
    }

    /**
     * isMenuOpen
     * @param scrollX
     * @return
     */
    public abstract boolean isMenuOpen(int scrollX);

    /**
     * isMenuOpenNotEqual
     * @param scrollX
     * @return
     */
    public abstract boolean isMenuOpenNotEqual(int scrollX);

    /**
     * autoOpenMenu
     * @param scroller
     * @param scrollX
     * @param duration
     */
    public abstract void autoOpenMenu(OverScroller scroller, int scrollX, int duration);

    /**
     * autoCloseMenu
     * @param scroller
     * @param scrollX
     * @param duration
     */
    public abstract void autoCloseMenu(OverScroller scroller, int scrollX, int duration);

    /**
     * checkXY
     * @param x
     * @param y
     * @return
     */
    public abstract Checker checkAxle(int x, int y);

    /**
     * isClickOnContentView
     * @param contentViewWidth
     * @param x
     * @return
     */
    public abstract boolean isClickOnContentView(int contentViewWidth, float x);

    public int getDirection() {
        return direction;
    }

    public View getMenuView() {
        return menuView;
    }

    public int getMenuWidth() {
        return menuView.getWidth();
    }

    public static final class Checker {

        public int x;
        public int y;
        public boolean shouldResetSwipe;
    }
}
