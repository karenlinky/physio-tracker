package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import java.util.ArrayList;

public class TrackerNotifItemList {
    private ArrayList<TrackerNotifItem> list = new ArrayList<>();

    private boolean notificationOn;

    public TrackerNotifItemList(boolean notificationOn) {
        this.notificationOn = notificationOn;
    }

    public void addItem(int minute, String message) {
        TrackerNotifItem trackerNotifItem = new TrackerNotifItem(minute, message);
        if (trackerNotifItem.validate()) {
            list.add(trackerNotifItem);
        }
    }

    public ArrayList<TrackerNotifItem> getList() {
        return this.list;
    }

    public boolean getNotificationOn() {
        return this.notificationOn;
    }

    public int getNumItems() {
        return list.size();
    }
}
