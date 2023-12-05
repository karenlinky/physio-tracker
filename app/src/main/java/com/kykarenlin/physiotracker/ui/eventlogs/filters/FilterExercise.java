package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import com.google.android.material.chip.Chip;
import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

public class FilterExercise extends EventFilter {
    @Override
    public boolean matchesCondition(EventWrapped eventWrapped) {
        return eventWrapped.getEvent().isExercise();
    }

    @Override
    public Chip getUIElement() {
        return FilterManager.getExerciseFilterUI();
    }

//    @Override
//    public FilterExercise getFilter() {
//        return FilterManager.getExerciseFilter();
//    }

    @Override
    public void updateUIElement() {
        this.getUIElement().setChecked(this.getIsActivated());
    }
}
