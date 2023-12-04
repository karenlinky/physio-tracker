package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import android.view.View;

import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public abstract class EventFilter {

    public boolean getIsActivated() {
        return FilterManager.getFilterActivated(this.getFilter());
    }
    public abstract boolean matchesCondition(EventWrapped eventWrapped);
    public abstract View getUIElement();
    public abstract EventFilter getFilter();
    public abstract void updateUIElement();
}
