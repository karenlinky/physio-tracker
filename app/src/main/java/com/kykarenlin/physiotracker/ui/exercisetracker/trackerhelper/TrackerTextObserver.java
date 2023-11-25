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

    private TextView selectExerciseHint;
    public TrackerTextObserver(TrackerStatusSubject trackerStatusSubject, ImageView sessionStatusIndicator, TextView txtTrackerStatus, TextView txtTrackerExerciseName, ExerciseDetailsFragment exerciseDetailsFragment, TextView selectExerciseHint) {
        this.sessionStatusIndicator = sessionStatusIndicator;
        this.trackerStatusSubject = trackerStatusSubject;
        this.txtTrackerStatus = txtTrackerStatus;
        this.txtTrackerExerciseName = txtTrackerExerciseName;
        this.exerciseDetailsFragment = exerciseDetailsFragment;
        this.selectExerciseHint = selectExerciseHint;
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
        String strExerciseHint = "";

        switch (status) {
            case SESSION_NOT_STARTED:
                strExerciseHint = "Select your first exercise from below and click \"Start\".";
                strTrackerStatus = "Session Not Started";
                displayingExercise = selectedExercise;
                sessionStatusIndicator.setColorFilter(resources.getColor(R.color.statusNotStarted, theme));
                break;
            case WORKOUT_IN_PROGRESS:
                strExerciseHint = "Complete your current exercise to move on to the next one.";
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
                strExerciseHint = "Select your next exercise from below and click \"Start\".";
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
                strExerciseHint = "Click the reset button below to start another session.";
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

        if (trackerStatusSubject.getNumExercise() == 0) {
            strExerciseHint = "Get started by adding exercises at the home page.";
        }
        this.selectExerciseHint.setText(strExerciseHint);
    }

    public void notifyStateChanged() {
        this.updateViews();
    }
}
