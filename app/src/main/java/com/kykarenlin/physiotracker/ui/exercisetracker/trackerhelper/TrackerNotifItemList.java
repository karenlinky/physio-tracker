package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TrackerNotifItemList {
    private ArrayList<TrackerNotifItem> list = new ArrayList<>();

    private boolean notificationOn;

    public TrackerNotifItemList(boolean notificationOn) {
        this.notificationOn = notificationOn;
    }

    public void addItem(int delay, String message) {
        TrackerNotifItem trackerNotifItem = new TrackerNotifItem(delay, message);
        list.add(trackerNotifItem);
    }

    public void addItem(String delay, String message) {
        TrackerNotifItem trackerNotifItem = new TrackerNotifItem(delay, message);
        list.add(trackerNotifItem);
    }

    public ArrayList<TrackerNotifItem> getList() {
        return this.list;
    }

    public ArrayList<TrackerNotifItem> getValidList() {
        return this.list.stream().filter(notifItem -> notifItem.validate()).collect(toCollection(ArrayList::new));
    }

    public boolean getNotificationOn() {
        return this.notificationOn;
    }
}
