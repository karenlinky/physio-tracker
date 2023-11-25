package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Intent;

public class StopwatchNotificationObserver extends TrackerObserver {

    private TrackerStatusSubject trackerStatusSubject;

    public StopwatchNotificationObserver (TrackerStatusSubject trackerStatusSubject) {
        this.trackerStatusSubject = trackerStatusSubject;
    }
    @Override
    public void notifyStateChanged() {}

    @Override
    public void notifyStartExercise() {
//        Intent notificationIntent = new Intent(this.trackerStatusSubject.getContext(), NotificationReceiver.class);
    }

    @Override
    public void notifyStartBreak() {

    }

    @Override
    public void notifyContinueSession() {

    }

    @Override
    public void notifySessionPaused() {

    }
}
