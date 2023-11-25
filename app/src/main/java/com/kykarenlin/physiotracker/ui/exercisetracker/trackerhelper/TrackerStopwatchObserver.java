package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.os.SystemClock;
import android.util.Log;
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
                cnmtTracker.setBase(SystemClock.elapsedRealtime()); // reset timer
                cnmtTracker.stop();
                break;
            case SESSION_COMPLETED:
                cnmtTracker.setBase(SystemClock.elapsedRealtime() - timeStamp);
                cnmtTracker.stop();
                break;
            case WORKOUT_IN_PROGRESS:
            case BREAK:
                if (sessionPaused) {
                    cnmtTracker.setBase(SystemClock.elapsedRealtime() - timeStamp);
                    cnmtTracker.stop();
                } else {
                    cnmtTracker.setBase(timeStamp);
                    cnmtTracker.start();
                }
                break;
        }
    }

    @Override
    public void notifyStateChanged() {
        updateViews();
    }
}
