package com.kykarenlin.physiotracker.ui.exercisetracker;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

public class ExerciseProgress {
    private Exercise exercise;

    private boolean selected;

    private boolean sessionCompleted;

    public ExerciseProgress(Exercise exercise, boolean selected, boolean sessionCompleted) {
        this.exercise = exercise;
        this.selected = selected;
        this.sessionCompleted = sessionCompleted;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSessionCompleted(boolean sessionCompleted) {
        this.sessionCompleted = sessionCompleted;
    }

    public boolean getSessionCompleted() {
        return this.sessionCompleted;
    }

    public Exercise getExercise() {
        return this.exercise;
    }
}
