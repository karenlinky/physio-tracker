package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.util.Log;

import com.kykarenlin.physiotracker.enums.TrackerStatus;
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

    private TrackerStatus trackerStatus;

    public ExerciseProgressObserver(TrackerStatusSubject trackerStatusSubject, TrackerExerciseListAdapter adapter) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.adapter = adapter;
    }

    @Override
    public void notifyExercisesChanged(List<Exercise> exercises) {
        TrackerStatus newTrackerStatus = this.trackerStatusSubject.getStatus();
        boolean sessionCompleted = newTrackerStatus == TrackerStatus.SESSION_COMPLETED;
        Exercise selectedExercise = this.trackerStatusSubject.getSelectedExercise();
        int selectedId = -1;
        if (selectedExercise != null) {
            selectedId = selectedExercise.getId();
        }
        exercisesWithProgress.clear();
        exerciseMap.clear();
        for (Exercise exercise : exercises) {
            ExerciseProgress exerciseProgress = new ExerciseProgress(exercise, selectedId == exercise.getId(), sessionCompleted);
            exercisesWithProgress.add(exerciseProgress);
            exerciseMap.put(exercise.getId(), exerciseProgress);
        }
        adapter.setExercises(exercisesWithProgress);
    }

    public void notifyStateChanged() {
        TrackerStatus newTrackerStatus = this.trackerStatusSubject.getStatus();
        Exercise selectedExercise = this.trackerStatusSubject.getSelectedExercise();
        int selectedId = -1;
        if (selectedExercise != null) {
            selectedId = selectedExercise.getId();
        }

        if (selectedId == this.selectedId && newTrackerStatus == this.trackerStatus) {
            return;
        }

        boolean updateNeeded = false;

        if (selectedId != this.selectedId) {
            // user selected a different item
            if (exerciseMap.containsKey(this.selectedId)) {
                exerciseMap.get(this.selectedId).setSelected(false);
            }


            this.selectedId = selectedId;
            if (exerciseMap.containsKey(this.selectedId)) {
                exerciseMap.get(this.selectedId).setSelected(true);
            }

            updateNeeded = true;
        }

        if (newTrackerStatus != this.trackerStatus) {
            if (this.trackerStatus == TrackerStatus.SESSION_COMPLETED || newTrackerStatus == TrackerStatus.SESSION_COMPLETED) {
                // user mark session as completed or session not started
                for (ExerciseProgress exercise : exercisesWithProgress) {
                    exercise.setSessionCompleted(newTrackerStatus == TrackerStatus.SESSION_COMPLETED);
                    updateNeeded = true;
                }
                this.trackerStatus = newTrackerStatus;
            }
        }

        if (updateNeeded) {
            adapter.setExercises(exercisesWithProgress);
        }
    }
}
