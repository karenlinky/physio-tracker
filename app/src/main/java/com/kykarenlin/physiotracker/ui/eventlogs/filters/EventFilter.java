package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import android.view.View;

import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public abstract class EventFilter {

    public abstract boolean getIsActivated();
    public abstract View getUIElement();
    public abstract boolean matchesCondition(EventWrapped eventWrapped);
}
