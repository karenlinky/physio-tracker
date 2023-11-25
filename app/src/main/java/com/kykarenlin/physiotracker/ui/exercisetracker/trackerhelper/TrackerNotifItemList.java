package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import java.util.ArrayList;

public class TrackerNotifItemList {
    private ArrayList<TrackerNotifItem> list = new ArrayList<>();

    public TrackerNotifItemList() {}

    public void addItem(int minute, String message) {
        TrackerNotifItem trackerNotifItem = new TrackerNotifItem(minute, message);
        if (trackerNotifItem.validate()) {
            list.add(trackerNotifItem);
        }
    }

    public int getNumItems() {
        return list.size();
    }
}
