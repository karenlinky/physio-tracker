package com.kykarenlin.physiotracker.ui.settings;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.utils.SharedPref;

public class NightModeSettings {

    private final static String KEY_NIGHT_MODE = "night_mode";
    private Context context;
    private SharedPref sharedPref;
    private static NightModeSettings instance = null;
    private NightModeSettings(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.sharedPref = new SharedPref(fragmentActivity.getPreferences(Context.MODE_PRIVATE));
    }

    public synchronized static NightModeSettings getInstance(Context context, FragmentActivity fragmentActivity) {
        if (instance == null) {
            instance = new NightModeSettings(context, fragmentActivity);
        }
        return instance;
    }

    public boolean getNightModeEnabled() {
        return this.sharedPref.getData(KEY_NIGHT_MODE, true);
    }

    public void setNightModeEnabled(boolean enabled) {
        this.sharedPref.setData(KEY_NIGHT_MODE, enabled);

        if (enabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
