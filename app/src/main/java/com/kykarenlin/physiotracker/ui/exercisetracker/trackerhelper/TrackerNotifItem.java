package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

public class TrackerNotifItem {

    private int delay;
    private String message;

    public TrackerNotifItem(int delay, String message) {
        this.delay = delay;
        this.message = message.trim();
    }

    public TrackerNotifItem(String delay, String message) {
        if (delay.equals("")) {
            this.delay = 0;
        } else {
            try {
                this.delay = Integer.parseInt(delay);
            } catch (Exception e) {
                this.delay = 0;
            }
        }
        this.message = message.trim();
    }

    public int getDelay() {
        return delay;
    }

    public String getMessage() {
        return message;
    }

    public boolean validate() {
        return (this.delay > 0 && !this.message.equals(""));
    }
}
