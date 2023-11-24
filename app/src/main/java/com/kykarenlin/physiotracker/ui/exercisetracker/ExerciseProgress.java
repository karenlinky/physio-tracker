package com.kykarenlin.physiotracker.ui.exercisetracker;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

public class ExerciseProgress {
    private Exercise exercise;

    public ExerciseProgress(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return this.exercise;
    }
}
