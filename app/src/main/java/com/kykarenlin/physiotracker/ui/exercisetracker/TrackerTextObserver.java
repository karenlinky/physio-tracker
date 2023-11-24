package com.kykarenlin.physiotracker.ui.exercisetracker;

import android.widget.Chronometer;
import android.widget.TextView;

import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;

public class TrackerTextObserver extends TrackerObserver {

    private TrackerStatusObject trackerStatusObject;
    private TextView txtTrackerStatus;
    private Chronometer cnmtTracker;
    private TextView txtTrackerExerciseName;
    private ExerciseDetailsFragment exerciseDetailsFragment;
    public TrackerTextObserver(TrackerStatusObject trackerStatusObject, TextView txtTrackerStatus, Chronometer cnmtTracker, TextView txtTrackerExerciseName, ExerciseDetailsFragment exerciseDetailsFragment) {
        this.trackerStatusObject = trackerStatusObject;
        this.txtTrackerStatus = txtTrackerStatus;
        this.cnmtTracker = cnmtTracker;
        this.txtTrackerExerciseName = txtTrackerExerciseName;
        this.exerciseDetailsFragment = exerciseDetailsFragment;
    }
}
