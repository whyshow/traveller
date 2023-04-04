package club.ccit.shared.user.model;

import android.content.Context;

import club.ccit.shared.base.BasePreference;
import club.ccit.shared.user.impi.IUserPreferences;

/**
 * FileName: UserModel
 *
 * @author: mosaic
 * Date: 2023/4/4 15:53
 * Description:
 * Version:
 */
public class UserPreferencesModel extends BasePreference implements IUserPreferences {

    /**
     * 构造方法，实例化 BasePreference
     *
     * @param context 上下文
     * @param name    存储文件的名字
     */
    public UserPreferencesModel(Context context, String name) {
        super(context, name);
    }

    @Override
    public String getUserPhone() {
        return getString(UserPhone,null);
    }

    @Override
    public void putUserPhone(String value) {
        putString(UserPhone,value).apply();
    }

    @Override
    public String getUserPassword() {
        return getString(UserPassword,null);
    }

    @Override
    public void putUserPassword(String value) {
        putString(UserPassword,value).apply();
    }
}
