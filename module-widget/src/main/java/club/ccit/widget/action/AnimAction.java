package club.ccit.widget.action;

import club.ccit.widget.R;

/**
 * FileName: AnimAction
 *
 * @author: 张帅威
 * Date: 2022/2/9 4:52 下午
 * Description: 动画样式
 * Version:
 */
public interface AnimAction {
    /** 默认动画效果 */
    int ANIM_DEFAULT = -1;

    /** 没有动画效果 */
    int ANIM_EMPTY = 0;

    /** 缩放动画 */
    int ANIM_SCALE = R.style.ScaleAnimStyle;

    /** 吐司动画 */
    int ANIM_TOAST = android.R.style.Animation_Toast;

    /** 顶部弹出动画 */
    int ANIM_TOP = R.style.TopAnimStyle;

    /** 底部弹出动画 */
    int ANIM_BOTTOM = R.style.BottomAnimStyle;
}
