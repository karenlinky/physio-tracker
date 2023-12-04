package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import com.google.android.material.chip.Chip;
import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public class FilterImportant extends EventFilter {

    @Override
    public boolean matchesCondition(EventWrapped eventWrapped) {
        return eventWrapped.getEvent().isImportant();
    }

    @Override
    public Chip getUIElement() {
        return FilterManager.getImportanceFilterUI();
    }

    @Override
    public boolean getIsActivated() {
        return FilterManager.getImportantFilterActivated();
    }
}
