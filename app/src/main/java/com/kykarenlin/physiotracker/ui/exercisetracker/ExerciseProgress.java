package com.kykarenlin.physiotracker.ui.exercisetracker;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

public class ExerciseProgress {
    private Exercise exercise;

    private boolean selected;

    public ExerciseProgress(Exercise exercise, boolean selected) {
        this.exercise = exercise;
        this.selected = selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public Exercise getExercise() {
        return this.exercise;
    }
}
