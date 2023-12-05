package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import com.kykarenlin.physiotracker.enums.TrackerStatus;

import java.util.Calendar;

public class TrackerStopwatchObserver extends TrackerObserver{
    private TrackerStatusSubject trackerStatusSubject;
    private Chronometer cnmtTracker;

    private final static int MAX_NUM_HOURS = 24;
    private final static int MAX_NUM_MILLISECONDS = MAX_NUM_HOURS * 60 * 60 * 1000;

    public TrackerStopwatchObserver(TrackerStatusSubject trackerStatusSubject, Chronometer cnmtTracker) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.cnmtTracker = cnmtTracker;

        this.cnmtTracker.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                TrackerStatus status = trackerStatusSubject.getStatus();
                boolean sessionPaused = trackerStatusSubject.getSessionPaused();
                long timeStamp = trackerStatusSubject.getTimestamp();


                switch (status) {
                    case SESSION_NOT_STARTED:
                    case SESSION_COMPLETED:
                        break;
                    case WORKOUT_IN_PROGRESS:
                    case BREAK:
                        if (sessionPaused) {
                            if (timeStamp >= MAX_NUM_MILLISECONDS) {
                                endSessionDueToTimeout();
                            }
                        } else {
                            if (Calendar.getInstance().getTimeInMillis() - timeStamp >= MAX_NUM_MILLISECONDS) {
                                endSessionDueToTimeout();
                            }
                        }
                        break;
                }
            }
        });
    }

    private void endSessionDueToTimeout() {
        trackerStatusSubject.finishSession();
        Toast.makeText(trackerStatusSubject.getContext(), "Last session ended due to timeout", Toast.LENGTH_SHORT).show();
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
                cnmtTracker.setBase(SystemClock.elapsedRealtime());
//                cnmtTracker.setBase(SystemClock.elapsedRealtime() - timeStamp);
                cnmtTracker.stop();
                break;
            case WORKOUT_IN_PROGRESS:
            case BREAK:
                if (sessionPaused) {
                    cnmtTracker.setBase(SystemClock.elapsedRealtime() - timeStamp);
                    cnmtTracker.stop();
                } else {
                    cnmtTracker.setBase(SystemClock.elapsedRealtime() - (Calendar.getInstance().getTimeInMillis() - timeStamp));
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
