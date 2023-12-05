package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import com.google.android.material.chip.Chip;
import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public class FilterActivity extends EventFilter {
    @Override
    public boolean matchesCondition(EventWrapped eventWrapped) {
        return eventWrapped.getEvent().isActivity();
    }

    @Override
    public Chip getUIElement() {
        return FilterManager.getActivityFilterUI();
    }

//    @Override
//    public FilterActivity getFilter() {
//        return FilterManager.getActivityFilter();
//    }

    @Override
    public void updateUIElement() {
        this.getUIElement().setChecked(this.getIsActivated());
    }
}
