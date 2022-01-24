package club.ccit.widget.swipe;

import android.view.View;
import android.widget.OverScroller;

/**
 * FileName: LeftHorizontal
 *
 * @author: 张帅威
 * Date: 2022/1/18 11:26 上午
 * Description:
 * Version:
 */
public class LeftHorizontal extends BaseHorizontal {

    public LeftHorizontal(View menuView) {
        super(SwipeRecyclerView.LEFT_DIRECTION, menuView);
    }

    @Override
    public boolean isMenuOpen(int scrollX) {
        int i = -getMenuView().getWidth() * getDirection();
        return scrollX <= i && i != 0;
    }

    @Override
    public boolean isMenuOpenNotEqual(int scrollX) {
        return scrollX < -getMenuView().getWidth() * getDirection();
    }

    @Override
    public void autoOpenMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(Math.abs(scrollX), 0, getMenuView().getWidth() - Math.abs(scrollX), 0, duration);
    }

    @Override
    public void autoCloseMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(-Math.abs(scrollX), 0, Math.abs(scrollX), 0, duration);
    }

    @Override
    public Checker checkAxle(int x, int y) {
        mChecker.x = x;
        mChecker.y = y;
        mChecker.shouldResetSwipe = false;
        if (mChecker.x == 0) {
            mChecker.shouldResetSwipe = true;
        }
        if (mChecker.x >= 0) {
            mChecker.x = 0;
        }
        if (mChecker.x <= -getMenuView().getWidth()) {
            mChecker.x = -getMenuView().getWidth();
        }
        return mChecker;
    }

    @Override
    public boolean isClickOnContentView(int contentViewWidth, float x) {
        return x > getMenuView().getWidth();
    }
}
