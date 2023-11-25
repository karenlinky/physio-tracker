package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.util.Log;

import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.exercisetracker.ExerciseProgress;
import com.kykarenlin.physiotracker.ui.exercisetracker.TrackerExerciseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseProgressObserver extends TrackerObserver {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();

    private HashMap<Integer, ExerciseProgress> exerciseMap = new HashMap<Integer, ExerciseProgress>();

    private int selectedId = -1;

    private TrackerExerciseListAdapter adapter;

    private TrackerStatusSubject trackerStatusSubject;

    public ExerciseProgressObserver(TrackerStatusSubject trackerStatusSubject, TrackerExerciseListAdapter adapter) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.adapter = adapter;
    }

    @Override
    public void notifyExercisesChanged(List<Exercise> exercises) {
        Exercise selectedExercise = this.trackerStatusSubject.getSelectedExercise();
        int selectedId = -1;
        if (selectedExercise != null) {
            selectedId = selectedExercise.getId();
        }
        exercisesWithProgress.clear();
        exerciseMap.clear();
        for (Exercise exercise : exercises) {
            ExerciseProgress exerciseProgress = new ExerciseProgress(exercise, selectedId == exercise.getId());
            exercisesWithProgress.add(exerciseProgress);
            exerciseMap.put(exercise.getId(), exerciseProgress);
        }
        adapter.setExercises(exercisesWithProgress);
    }

    public void notifyStateChanged() {
        Exercise selectedExercise = this.trackerStatusSubject.getSelectedExercise();
        int selectedId = -1;
        if (selectedExercise != null) {
            selectedId = selectedExercise.getId();
        }

        if (selectedId == this.selectedId) {
            return;
        }

        if (exerciseMap.containsKey(this.selectedId)) {
            exerciseMap.get(this.selectedId).setSelected(false);
        }


        this.selectedId = selectedId;
        if (exerciseMap.containsKey(this.selectedId)) {
            exerciseMap.get(this.selectedId).setSelected(true);
        }

        adapter.setExercises(exercisesWithProgress);
    }
}
