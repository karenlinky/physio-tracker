package com.kykarenlin.physiotracker.ui.exercisetracker;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseProgressObserver extends TrackerObserver {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();

    private TrackerExerciseListAdapter adapter;

    public ExerciseProgressObserver(TrackerExerciseListAdapter adapter) {this.adapter = adapter;}

    public void updateExercises(List<Exercise> exercises) {
        for (Exercise exercise : exercises) {
            exercisesWithProgress.add(new ExerciseProgress(exercise));
        }
        adapter.setExercises(exercisesWithProgress);
    }
}
