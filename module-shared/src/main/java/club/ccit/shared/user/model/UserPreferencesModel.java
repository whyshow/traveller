package club.ccit.shared.user.model;

import android.content.Context;

import club.ccit.shared.base.BasePreference;
import club.ccit.shared.user.impl.IUserPreferences;

/**
 * FileName: UserPreferencesModel
 *
 * @author: mosaic
 * Date: 2023/4/6 08:25
 * Description:
 * Version:
 */
public class UserPreferencesModel extends BasePreference implements IUserPreferences {
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
