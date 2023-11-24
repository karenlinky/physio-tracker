package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static com.kykarenlin.physiotracker.enums.TrackerStatus.SESSION_NOT_STARTED;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.List;

public class ExerciseControlObserver extends TrackerObserver{

    private TrackerStatusSubject trackerStatusSubject;
    private Button btnStartExercise;
    private LinearLayout exerciseOngoingButtonsContainer;
    private Button btnCancelExercise;
    private Button btnFinishExercise;
    public ExerciseControlObserver(TrackerStatusSubject trackerStatusSubject, Button btnStartExercise, LinearLayout exerciseOngoingButtonsContainer, Button btnCancelExercise, Button btnFinishExercise) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.btnStartExercise = btnStartExercise;
        this.exerciseOngoingButtonsContainer = exerciseOngoingButtonsContainer;
        this.btnCancelExercise = btnCancelExercise;
        this.btnFinishExercise = btnFinishExercise;
    }

    private void updateViews() {
        TrackerStatus status = trackerStatusSubject.getStatus();
        boolean sessionPaused = trackerStatusSubject.getSessionPaused();
        Exercise selectedExercise = trackerStatusSubject.getSelectedExercise();

        switch (status) {
            case SESSION_NOT_STARTED:
            case BREAK:
                this.btnStartExercise.setVisibility(View.VISIBLE);
                this.exerciseOngoingButtonsContainer.setVisibility(View.GONE);
                if (selectedExercise != null) {
                    // exercise selected
                    this.btnStartExercise.setEnabled(true);
                } else {
                    // no exercise selected
                    this.btnStartExercise.setEnabled(false);
                }

                break;
            case WORKOUT_IN_PROGRESS:
                this.btnStartExercise.setVisibility(View.GONE);
                this.exerciseOngoingButtonsContainer.setVisibility(View.VISIBLE);
                break;
            case SESSION_COMPLETED:
                this.btnStartExercise.setVisibility(View.VISIBLE);
                this.exerciseOngoingButtonsContainer.setVisibility(View.GONE);
                this.btnStartExercise.setEnabled(false);
                break;
        }
    }

    public void notifyStateChanged() {
        this.updateViews();
    }
}
