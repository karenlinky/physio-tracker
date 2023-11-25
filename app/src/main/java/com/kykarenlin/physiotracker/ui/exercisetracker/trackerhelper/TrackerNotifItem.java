package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

public class TrackerNotifItem {

    private int minute;
    private String message;

    public TrackerNotifItem(int minute, String message) {
        this.minute = minute;
        this.message = message.trim();
    }

    public TrackerNotifItem(String minute, String message) {
        if (minute.equals("")) {
            this.minute = 0;
        } else {
            try {
                this.minute = Integer.parseInt(minute);
            } catch (Exception e) {
                this.minute = 0;
            }
        }
        this.message = message.trim();
    }

    public int getMinute() {
        return minute;
    }

    public String getMessage() {
        return message;
    }

    public boolean validate() {
        return (this.minute > 0 && !this.message.equals(""));
    }
}
