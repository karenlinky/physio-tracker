package com.kykarenlin.physiotracker.ui.eventlogs;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.ui.eventlogs.filters.FilterManager;
import com.kykarenlin.physiotracker.utils.DateTimeHelper;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListManager {

    private EventViewModel eventViewModel;
    private LifecycleOwner lifecycleOwner;
    private List<EventWrapped> allEventsWithStartDate = new ArrayList<>();
    private List<EventWrapped> allEventsWithoutStartDate = new ArrayList<>();
    private List<EventWrapped> allArchivedEventsWithStartDate = new ArrayList<>();
    private List<EventWrapped> allArchivedEventsWithoutStartDate = new ArrayList<>();
    private EventAdapter eventListAdapter;
    private RelativeLayout emptyEventListContainer;
    private ScrollView eventListContainer;
    private TextView emptyEventListMsg;
    private FilterManager filterManager;
    private static boolean showingActive = true;
    public EventListManager(EventViewModel eventViewModel, LifecycleOwner lifecycleOwner, EventAdapter eventListAdapter, RelativeLayout emptyEventListContainer, TextView emptyEventListMsg, ScrollView eventListContainer, FilterManager filterManager) {
        this.eventViewModel = eventViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.eventListAdapter = eventListAdapter;
        this.emptyEventListContainer = emptyEventListContainer;
        this.eventListContainer = eventListContainer;
        this.emptyEventListMsg = emptyEventListMsg;
        this.filterManager = filterManager;
    }

    private void updateList() {
        List<EventWrapped> listToDisplay = new ArrayList<>();
        if (showingActive) {
            listToDisplay.addAll(allEventsWithoutStartDate);
            listToDisplay.addAll(allEventsWithStartDate);
        } else {
            listToDisplay.addAll(allArchivedEventsWithoutStartDate);
            listToDisplay.addAll(allArchivedEventsWithStartDate);
        }

        List<EventWrapped> filteredList = this.filterManager.filter(listToDisplay);

        if (listToDisplay.size() == 0) {
            emptyEventListContainer.setVisibility(View.VISIBLE);
            eventListContainer.setVisibility(View.GONE);
            if (showingActive) {
                emptyEventListMsg.setText(R.string.empty_event_list_msg);
            } else {
                emptyEventListMsg.setText(R.string.empty_archived_event_list_msg);
            }
        } else if (filteredList.size() == 0) {
            emptyEventListContainer.setVisibility(View.VISIBLE);
            eventListContainer.setVisibility(View.GONE);
            emptyEventListMsg.setText("We couldn't find any results that match your filter.");
        } else {
            emptyEventListContainer.setVisibility(View.GONE);
            eventListContainer.setVisibility(View.VISIBLE);
        }
        this.eventListAdapter.setEventsWrapped(filteredList);
    }

    public void notifyFilterUpdated() {
        this.updateList();
    }

    private void updateListDueToListChange(boolean fromActive) {
        if (showingActive && fromActive || !showingActive && !fromActive) {
            this.updateList();
        }
    }

    public static boolean isShowingActive() {
        return showingActive;
    }

    public void setShowingActive(boolean showingActive) {
        if (this.showingActive != showingActive) {
            this.showingActive = showingActive;
            this.updateList();
        }
    }

    private List<EventWrapped> generateEventListWrapped(List<Event> events, boolean hasStartDate) {
        List<EventWrapped> newList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            newList.add(
                new EventWrapped(
                    eventViewModel,
                    this.lifecycleOwner,
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
        return newList;
    }

    public void setAllEventsWithStartDate(List<Event> allEventsWithStartDate) {
        this.allEventsWithStartDate = this.generateEventListWrapped(allEventsWithStartDate, true);
        updateListDueToListChange(true);
    }
    public void setAllEventsWithoutStartDate(List<Event> allEventsWithoutStartDate) {
        this.allEventsWithoutStartDate = this.generateEventListWrapped(allEventsWithoutStartDate, false);
        updateListDueToListChange(true);
    }
    public void setAllArchivedEventsWithStartDate(List<Event> allArchivedEventsWithStartDate) {
        this.allArchivedEventsWithStartDate = this.generateEventListWrapped(allArchivedEventsWithStartDate, true);
        updateListDueToListChange(false);
    }
    public void setAllArchivedEventsWithoutStartDate(List<Event> allArchivedEventsWithoutStartDate) {
        this.allArchivedEventsWithoutStartDate = this.generateEventListWrapped(allArchivedEventsWithoutStartDate, false);
        updateListDueToListChange(false);
    }
}
