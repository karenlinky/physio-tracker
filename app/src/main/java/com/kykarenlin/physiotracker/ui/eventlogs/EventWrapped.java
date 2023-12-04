package com.kykarenlin.physiotracker.ui.eventlogs;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.ui.eventlogs.filters.EventFilter;
import com.kykarenlin.physiotracker.ui.eventlogs.filters.FilterKeyword;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventWrapped {

    private Event event;

    private boolean showDate;

    private boolean showEndOfWeekIndicator;

    private boolean hasPreviousEvents;

    private Set<String> setStrDetails = new HashSet<>();



    public EventWrapped(EventViewModel eventViewModel, LifecycleOwner lifecycleOwner, Event event) {
        this.event = event;
        this.showDate = false;
        this.showEndOfWeekIndicator = false;
        this.hasPreviousEvents = false;

        String[] lstStrDetails = this.event.getEventDetails().toLowerCase().split(FilterKeyword.KEYWORD_DELIMETER);
        for (String strDetails : lstStrDetails) {
            this.setStrDetails.add(strDetails);
        }

        eventViewModel.getPrevEvents(event).observe(lifecycleOwner, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> prevEvents) {
                hasPreviousEvents = prevEvents.size() > 0;
            }
        });
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public void setShowEndOfWeekIndicator(boolean showEndOfWeekIndicator) {
        this.showEndOfWeekIndicator = showEndOfWeekIndicator;
    }

    public Set<String> getSetStrDetails() {
        return this.setStrDetails;
    }

    public Event getEvent() {
        return event;
    }

    public boolean shouldShowDate() {
        return showDate;
    }

    public boolean shouldShowEndOfWeekIndicator() {
        return showEndOfWeekIndicator;
    }

    public boolean getHasPreviousEvents() {
        return this.hasPreviousEvents;
    }
}
