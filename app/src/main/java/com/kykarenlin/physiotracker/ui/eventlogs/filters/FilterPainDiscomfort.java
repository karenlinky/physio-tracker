package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import com.google.android.material.chip.Chip;
import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public class FilterPainDiscomfort extends EventFilter {
    @Override
    public boolean matchesCondition(EventWrapped eventWrapped) {
        return eventWrapped.getEvent().isPainOrDiscomfort();
    }

    @Override
    public Chip getUIElement() {
        return FilterManager.getPainDiscomfortFilterUI();
    }

//    @Override
//    public FilterPainDiscomfort getFilter() {
//        return FilterManager.getPainDiscomfortFilter();
//    }

    @Override
    public void updateUIElement() {
        this.getUIElement().setChecked(this.getIsActivated());
    }
}
