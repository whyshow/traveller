package club.ccit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * FileName: SharedPreferencesUtil
 *
 * @author: mosaic
 * Date: 2023/4/3 14:46
 * Description:
 * Version:
 */
public class SharedPreferencesUtil {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferencesUtil(Context context, String fileName) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void setValue(String key, Object value) {
        if (value instanceof String) {
            mEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value);
        } else {
            throw new IllegalArgumentException("This type of data is not supported by SharedPreferences");
        }
        mEditor.apply();
    }

    public <T> T getValue(String key, T defaultValue) {
        if (defaultValue instanceof String) {
            return (T) mSharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, (Long) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, (Float) defaultValue));
        } else {
            throw new IllegalArgumentException("This type of data is not supported by SharedPreferences");
        }
    }

}
