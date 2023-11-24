package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.util.Log;

import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.exercisetracker.ExerciseProgress;
import com.kykarenlin.physiotracker.ui.exercisetracker.TrackerExerciseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseProgressObserver extends TrackerObserver {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();

    private TrackerExerciseListAdapter adapter;

    public ExerciseProgressObserver(TrackerExerciseListAdapter adapter) {this.adapter = adapter;}

    @Override
    public void notifyExercisesChanged(List<Exercise> exercises) {
        exercisesWithProgress.clear();
        for (Exercise exercise : exercises) {
            exercisesWithProgress.add(new ExerciseProgress(exercise));
        }
        adapter.setExercises(exercisesWithProgress);
    }

    public void notifyStateChanged() {}
}
