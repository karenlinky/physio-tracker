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
    private TextView txtTrackerExerciseName;
    private ExerciseDetailsFragment exerciseDetailsFragment;
    public TrackerTextObserver(TrackerStatusSubject trackerStatusSubject, TextView txtTrackerStatus, TextView txtTrackerExerciseName, ExerciseDetailsFragment exerciseDetailsFragment) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.txtTrackerStatus = txtTrackerStatus;
        this.txtTrackerExerciseName = txtTrackerExerciseName;
        this.exerciseDetailsFragment = exerciseDetailsFragment;
    }

    private void updateViews() {
        TrackerStatus status = trackerStatusSubject.getStatus();
        boolean sessionPaused = trackerStatusSubject.getSessionPaused();
        Exercise activeExercise = trackerStatusSubject.getActiveExercise();
        Exercise selectedExercise = trackerStatusSubject.getSelectedExercise();
        Exercise displayingExercise = null;

        String strTrackerStatus = "";
        String strTrackerExerciseName = "";

        switch (status) {
            case SESSION_NOT_STARTED:
                strTrackerStatus = "Session Not Started";
                displayingExercise = selectedExercise;

                break;
            case WORKOUT_IN_PROGRESS:
                displayingExercise = activeExercise;

                if (sessionPaused) {
                    strTrackerStatus = "Session Paused";
                } else {
                    strTrackerStatus = "Workout In Progress";
                };
                break;
            case BREAK:
                if (selectedExercise == null) {
                    displayingExercise = activeExercise;
                } else {
                    displayingExercise = selectedExercise;
                }

                if (sessionPaused) {
                    strTrackerStatus = "Session Paused";
                } else {
                    strTrackerStatus = "Break";
                };
                break;
            case SESSION_COMPLETED:
                strTrackerStatus = "Session Completed";


                displayingExercise = activeExercise;
                break;
        }

        if (displayingExercise != null) {
            strTrackerExerciseName = displayingExercise.getName();
        } else {
            strTrackerExerciseName = "";
        }
        this.exerciseDetailsFragment.updateValues(displayingExercise);

        this.txtTrackerStatus.setText(strTrackerStatus);
        this.txtTrackerExerciseName.setText(strTrackerExerciseName);
    }

    public void notifyStateChanged() {
        this.updateViews();
    }
}
