package club.ccit.shared.user;

import android.content.Context;

import club.ccit.shared.user.model.UserPreferencesModel;

/**
 * FileName: UserPreferenceManager
 *
 * @author: mosaic
 * Date: 2023/4/6 08:23
 * Description:
 * Version:
 */
public class UserPreferenceManager {
    private static volatile UserPreferenceManager instance;
    private UserPreferencesModel userPreferences;
    /**
     * 单例获取 UserPreferenceManager
     * @return UserPreferenceManager
     */
    public static UserPreferenceManager getInstance() {
        if (instance == null) {
            synchronized (UserPreferenceManager.class) {
                if (instance == null) {
                    instance = new UserPreferenceManager();
                }
            }
        }
        return instance;
    }

    public UserPreferencesModel initPreferences(Context context) {
        return UserPreferenceManager.getInstance().setUserPreferences(new UserPreferencesModel(context, "_user"));
    }

    private UserPreferencesModel setUserPreferences(UserPreferencesModel user) {
        return userPreferences = user;
    }

}
