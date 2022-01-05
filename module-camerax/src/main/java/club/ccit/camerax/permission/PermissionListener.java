package club.ccit.camerax.permission;

import androidx.annotation.NonNull;

/**
 * FileName: PermissionListener
 *
 * @author: 瞌睡的牙签
 * Date: 2021/12/8 8:36 上午
 * Description:
 * Version:
 */
public interface PermissionListener {
    /**
     * 通过授权
     * @param permission
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 拒绝授权
     * @param permission
     */
    void permissionDenied(@NonNull String[] permission);
}
