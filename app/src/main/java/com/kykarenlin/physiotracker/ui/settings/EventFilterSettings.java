package com.kykarenlin.physiotracker.ui.settings;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.utils.SharedPref;

public class EventFilterSettings {

    private final static String KEY_EVENT_FILTER_MODE = "event_filter_mode";
    private final static String VAL_EVENT_MATCH_ONE = "event_filter_match_one";
    private final static String VAL_EVENT_MATCH_ALL = "event_filter_match_all";
    private Context context;
    private SharedPref sharedPref;
    private static EventFilterSettings instance = null;
    private EventFilterSettings(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.sharedPref = new SharedPref(fragmentActivity.getPreferences(Context.MODE_PRIVATE));
    }

    public synchronized static EventFilterSettings getInstance(Context context, FragmentActivity fragmentActivity) {
        if (instance == null) {
            instance = new EventFilterSettings(context, fragmentActivity);
        }
        return instance;
    }

    private String getEventFilterMode() {
        return this.sharedPref.getData(EventFilterSettings.KEY_EVENT_FILTER_MODE, EventFilterSettings.VAL_EVENT_MATCH_ONE);
    }

    private void setEventFilterMode(String mode) {
        this.sharedPref.setData(EventFilterSettings.KEY_EVENT_FILTER_MODE, mode);
    }

    public void setMatchOne() {
        this.setEventFilterMode(VAL_EVENT_MATCH_ONE);
    }

    public void setMatchAll() {
        this.setEventFilterMode(VAL_EVENT_MATCH_ALL);
    }

    public boolean isMatchOne() {
        return this.getEventFilterMode().equals(EventFilterSettings.VAL_EVENT_MATCH_ONE);
    }

    public boolean isMatchAll() {
        return this.getEventFilterMode().equals(EventFilterSettings.VAL_EVENT_MATCH_ALL);
    }
}
