package com.kykarenlin.physiotracker.ui.eventlogs;

import com.kykarenlin.physiotracker.model.event.Event;

public class EventWrapped {

    private Event event;

    private boolean showDate;

    private boolean showEndOfWeekIndicator;

    public EventWrapped(Event event, boolean showDate, boolean showEndOfWeekIndicator) {
        this.event = event;
        this.showDate = showDate;
        this.showEndOfWeekIndicator = showEndOfWeekIndicator;
    }

    public Event getEvent() {
        return event;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public boolean isShowEndOfWeekIndicator() {
        return showEndOfWeekIndicator;
    }

    public void setShowEndOfWeekIndicator() {
        this.showEndOfWeekIndicator = true;
    }
}
