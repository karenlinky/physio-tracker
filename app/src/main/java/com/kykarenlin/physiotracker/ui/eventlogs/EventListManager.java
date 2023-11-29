package com.kykarenlin.physiotracker.ui.eventlogs;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class EventListManager {
    private List<EventWrapped> allEventsWithStartDate = new ArrayList<>();
    private List<EventWrapped> allEventsWithoutStartDate = new ArrayList<>();
    private List<EventWrapped> allArchivedEventsWithStartDate = new ArrayList<>();
    private List<EventWrapped> allArchivedEventsWithoutStartDate = new ArrayList<>();
    private EventAdapter eventListAdapter;

    private boolean showArchived;
    public EventListManager(EventAdapter eventListAdapter) {
        this.eventListAdapter = eventListAdapter;
        this.showArchived = false;
    }

    private void updateListDueToListChange(boolean fromArchived) {
        if (showArchived && fromArchived || !showArchived && !fromArchived) {
            List<EventWrapped> listToDisplay = new ArrayList<>();
            if (showArchived) {
                listToDisplay.addAll(allArchivedEventsWithoutStartDate);
                listToDisplay.addAll(allArchivedEventsWithStartDate);
            } else {
                listToDisplay.addAll(allEventsWithoutStartDate);
                listToDisplay.addAll(allEventsWithStartDate);
            }
            Log.e("TAG", "updateListDueToListChange: size: " + listToDisplay.size());
            this.eventListAdapter.setEventsWrapped(listToDisplay);
        }
    }

    private List<EventWrapped> generateEventListWrapped(List<Event> events, boolean hasStartDate) {
        List<EventWrapped> newList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            newList.add(
                new EventWrapped(
                    event,
                    hasStartDate &&
                        (i == 0 ||                                       // first item of list
                        !DateTimeHelper.isWithinSameDay(                // not the same day as the previous item
                            event.getEventStartTime(),
                            events.get(i - 1).getEventStartTime())),
                    hasStartDate &&
                        i < events.size() - 1 &&                        // not the last item
                        !DateTimeHelper.isWithinSameWeek(               // not the same week as the next item
                            event.getEventStartTime(),
                            events.get(i + 1).getEventStartTime()
                        )));
        }
        if (!hasStartDate) {
            if (newList.size() > 0) {
                newList.get(newList.size() - 1).setShowEndOfWeekIndicator();
            }
        }
        Log.e("TAG", "generateEventListWrapped: newList.size(): " + newList.size());
        return newList;
    }

    public void setAllEventsWithStartDate(List<Event> allEventsWithStartDate) {
        this.allEventsWithStartDate = this.generateEventListWrapped(allEventsWithStartDate, true);
        updateListDueToListChange(false);
    }
    public void setAllEventsWithoutStartDate(List<Event> allEventsWithoutStartDate) {
        this.allEventsWithoutStartDate = this.generateEventListWrapped(allEventsWithoutStartDate, false);
        updateListDueToListChange(false);
    }
    public void setAllArchivedEventsWithStartDate(List<Event> allArchivedEventsWithStartDate) {
        this.allArchivedEventsWithStartDate = this.generateEventListWrapped(allArchivedEventsWithStartDate, true);
        updateListDueToListChange(true);
    }
    public void setAllArchivedEventsWithoutStartDate(List<Event> allArchivedEventsWithoutStartDate) {
        this.allArchivedEventsWithoutStartDate = this.generateEventListWrapped(allArchivedEventsWithoutStartDate, false);
        updateListDueToListChange(true);
    }
}
