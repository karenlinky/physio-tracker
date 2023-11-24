package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.os.SystemClock;
import android.widget.Chronometer;

import com.kykarenlin.physiotracker.enums.TrackerStatus;

public class TrackerStopwatchObserver extends TrackerObserver{
    private TrackerStatusSubject trackerStatusSubject;
    private Chronometer cnmtTracker;

    public TrackerStopwatchObserver(TrackerStatusSubject trackerStatusSubject, Chronometer cnmtTracker) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.cnmtTracker = cnmtTracker;
    }

    private void updateViews() {
        TrackerStatus status = trackerStatusSubject.getStatus();
        boolean sessionPaused = trackerStatusSubject.getSessionPaused();
        long timeStamp = trackerStatusSubject.getTimestamp();

        switch (status) {
            case SESSION_NOT_STARTED:
            case SESSION_COMPLETED:
                cnmtTracker.stop();
                break;
            case WORKOUT_IN_PROGRESS:
            case BREAK:
                cnmtTracker.setBase(timeStamp);
                cnmtTracker.start();
                break;
        }
    }

    @Override
    public void notifyStateChanged() {
        updateViews();
    }
}
