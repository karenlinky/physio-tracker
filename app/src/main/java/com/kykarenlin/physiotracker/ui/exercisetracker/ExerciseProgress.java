package com.kykarenlin.physiotracker.ui.exercisetracker;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

public class ExerciseProgress {
    private Exercise exercise;

    private boolean selected;

    private boolean buttonDisabled;

    public ExerciseProgress(Exercise exercise, boolean selected, boolean buttonDisabled) {
        this.exercise = exercise;
        this.selected = selected;
        this.buttonDisabled = buttonDisabled;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
    }

    public boolean getButtonDisabled() {
        return this.buttonDisabled;
    }

    public Exercise getExercise() {
        return this.exercise;
    }
}
