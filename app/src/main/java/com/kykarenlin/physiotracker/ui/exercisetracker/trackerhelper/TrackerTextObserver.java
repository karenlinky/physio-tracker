package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;

import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;

import java.util.List;

public class TrackerTextObserver extends TrackerObserver {

    private TrackerStatusSubject trackerStatusSubject;
    private TextView txtTrackerStatus;
    private Chronometer cnmtTracker;
    private TextView txtTrackerExerciseName;
    private ExerciseDetailsFragment exerciseDetailsFragment;
    public TrackerTextObserver(TrackerStatusSubject trackerStatusSubject, TextView txtTrackerStatus, Chronometer cnmtTracker, TextView txtTrackerExerciseName, ExerciseDetailsFragment exerciseDetailsFragment) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.txtTrackerStatus = txtTrackerStatus;
        this.cnmtTracker = cnmtTracker;
        this.txtTrackerExerciseName = txtTrackerExerciseName;
        this.exerciseDetailsFragment = exerciseDetailsFragment;
    }

    private void updateViews() {
        TrackerStatus status = trackerStatusSubject.getStatus();
        boolean sessionPaused = trackerStatusSubject.getSessionPaused();
        Exercise activeExercise = trackerStatusSubject.getActiveExercise();
        Exercise selectedExercise = trackerStatusSubject.getSelectedExercise();

        String strTrackerStatus = "";
        String strTrackerExerciseName = "";

        Log.e("TAG", "updateViews: here");

        switch (status) {
            case SESSION_NOT_STARTED:
                strTrackerStatus = "Session Not Started";
                if (selectedExercise != null) {
                    strTrackerExerciseName = selectedExercise.getName();
                } else {
                    strTrackerExerciseName = "";
                }
                this.exerciseDetailsFragment.updateValues(selectedExercise);

                break;
            case WORKOUT_IN_PROGRESS:
                if (activeExercise != null) {
                    strTrackerExerciseName = activeExercise.getName();
                } else {
                    strTrackerExerciseName = "";
                }
                this.exerciseDetailsFragment.updateValues(activeExercise);

                if (sessionPaused) {
                    strTrackerStatus = "Session Paused";
                } else {
                    strTrackerStatus = "Workout In Progress";
                };
                break;
            case BREAK:
                // TODO: make sure that when on break, id is not cancelled
                // TODO: think about what happens if exercise gets deleted
                if (selectedExercise != null) {
                    strTrackerExerciseName = selectedExercise.getName();
                } else {
                    strTrackerExerciseName = "";
                }

                if (sessionPaused) {
                    strTrackerStatus = "Session Paused";
                } else {
                    strTrackerStatus = "Break";
                };
                this.exerciseDetailsFragment.updateValues(selectedExercise);
                break;
            case SESSION_COMPLETED:
                strTrackerStatus = "Session Completed";

                if (activeExercise != null) {
                    strTrackerExerciseName = activeExercise.getName();
                } else {
                    strTrackerExerciseName = "";
                }
                this.exerciseDetailsFragment.updateValues(activeExercise);
                break;
        }

        this.txtTrackerStatus.setText(strTrackerStatus);
        this.txtTrackerExerciseName.setText(strTrackerExerciseName);
    }

    public void notifyStateChanged() {
        this.updateViews();
    }
}
