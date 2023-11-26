package com.kykarenlin.physiotracker.utils;

import android.content.SharedPreferences;
import android.util.Log;

public class SharedPref {

    private SharedPreferences sharedPref;
    public SharedPref(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    public String getData(String key, String defaultVal) {
        return sharedPref.getString(key, defaultVal);
    }

    public int getData(String key, int defaultVal) {
        return sharedPref.getInt(key, defaultVal);
    }

    public long getData(String key, long defaultVal) {
        return sharedPref.getLong(key, defaultVal);
    }

    public boolean getData(String key, boolean defaultVal) {
        return sharedPref.getBoolean(key, defaultVal);
    }

    public void setData(String key, String defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, defaultVal);
        editor.apply();

    }

    public void setData(String key, int defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, defaultVal);
        editor.apply();
    }

    public void setData(String key, long defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, defaultVal);
        editor.apply();
    }

    public void setData(String key, boolean defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, defaultVal);
        editor.apply();
    }
}
