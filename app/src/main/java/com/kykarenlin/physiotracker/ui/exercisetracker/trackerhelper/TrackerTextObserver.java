package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;

import java.util.List;

public class TrackerTextObserver extends TrackerObserver {

    private TrackerStatusSubject trackerStatusSubject;
    ImageView sessionStatusIndicator;
    private TextView txtTrackerStatus;
    private TextView txtTrackerExerciseName;
    private ExerciseDetailsFragment exerciseDetailsFragment;
    public TrackerTextObserver(TrackerStatusSubject trackerStatusSubject, ImageView sessionStatusIndicator, TextView txtTrackerStatus, TextView txtTrackerExerciseName, ExerciseDetailsFragment exerciseDetailsFragment) {
        this.sessionStatusIndicator = sessionStatusIndicator;
        this.trackerStatusSubject = trackerStatusSubject;
        this.txtTrackerStatus = txtTrackerStatus;
        this.txtTrackerExerciseName = txtTrackerExerciseName;
        this.exerciseDetailsFragment = exerciseDetailsFragment;
    }

    private void updateViews() {
        Context context = trackerStatusSubject.getContext();
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();
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
                sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusNotStarted, theme));
                break;
            case WORKOUT_IN_PROGRESS:
                displayingExercise = activeExercise;

                if (sessionPaused) {
                    sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusSessionPaused, theme));
                    strTrackerStatus = "Session Paused";
                } else {
                    sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusInProgress, theme));
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
                    sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusSessionPaused, theme));
                    strTrackerStatus = "Session Paused";
                } else {
                    sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusBreak, theme));
                    strTrackerStatus = "Break";
                };
                break;
            case SESSION_COMPLETED:
                sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusSessionCompleted, theme));
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
