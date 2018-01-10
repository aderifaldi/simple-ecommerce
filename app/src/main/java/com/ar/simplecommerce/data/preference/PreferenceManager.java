package com.ar.simplecommerce.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.ar.simplecommerce.helper.Constant;

/**
 * Created by aderifaldi on 2018-01-09.
 */

public class PreferenceManager {

    private static final String DEFAULT_STRING = "";
    private static final int DEFAULT_INT = 0;
    private static final boolean DEFAULT_BOOLEAN = false;

    private SharedPreferences sharedPreferences;

    private PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.Preference.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }


    public void saveLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }


    public void saveBoolean(String key, Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, DEFAULT_STRING);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, DEFAULT_INT);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, DEFAULT_INT);
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN);
    }

}
