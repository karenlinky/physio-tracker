package com.kykarenlin.physiotracker.ui.exercisetracker;

import android.widget.Button;
import android.widget.LinearLayout;

public class ExerciseControlObserver extends TrackerObserver{

    private TrackerStatusObject trackerStatusObject;
    private Button btnStartExercise;
    private LinearLayout exerciseOngoingButtonsContainer;
    private Button btnCancelExercise;
    private Button btnFinishExercise;
    public ExerciseControlObserver(TrackerStatusObject trackerStatusObject, Button btnStartExercise, LinearLayout exerciseOngoingButtonsContainer, Button btnCancelExercise, Button btnFinishExercise) {
        this.trackerStatusObject = trackerStatusObject;
        this.btnStartExercise = btnStartExercise;
        this.exerciseOngoingButtonsContainer = exerciseOngoingButtonsContainer;
        this.btnCancelExercise = btnCancelExercise;
        this.btnFinishExercise = btnFinishExercise;
    }
}
