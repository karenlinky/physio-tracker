package com.kykarenlin.physiotracker.ui.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListManager {

    private ExerciseAdapter exerciseAdapter;
    private RelativeLayout emptyExerciseListContainer;
    private TextView emptyExerciseListMsg;
    private ScrollView exerciseListContainer;
    private List<Exercise> activeExercises = new ArrayList<>();
    private List<Exercise> archivedExercises = new ArrayList<>();

    private static boolean showingActive = true;

//    private ArrayL

    public ExerciseListManager (ExerciseAdapter exerciseAdapter, RelativeLayout emptyExerciseListContainer, TextView emptyExerciseListMsg, ScrollView exerciseListContainer) {
        this.exerciseAdapter = exerciseAdapter;
        this.emptyExerciseListContainer = emptyExerciseListContainer;
        this.emptyExerciseListMsg = emptyExerciseListMsg;
        this.exerciseListContainer = exerciseListContainer;
        this.updateList();
    }

    public static boolean isShowingActive() {
        return showingActive;
    }

    private void updateList() {
        if (this.showingActive) {
            if (this.activeExercises.size() == 0) {
                emptyExerciseListContainer.setVisibility(View.VISIBLE);
                emptyExerciseListMsg.setText(R.string.empty_exercise_list_msg);
                exerciseListContainer.setVisibility(View.GONE);
            } else {
                emptyExerciseListContainer.setVisibility(View.GONE);
                exerciseListContainer.setVisibility(View.VISIBLE);
            }
            this.exerciseAdapter.setExercises(this.activeExercises);
        } else {
            if (this.archivedExercises.size() == 0) {
                emptyExerciseListContainer.setVisibility(View.VISIBLE);
                emptyExerciseListMsg.setText(R.string.empty_archived_exercise_list_msg);
                exerciseListContainer.setVisibility(View.GONE);
            } else {
                emptyExerciseListContainer.setVisibility(View.GONE);
                exerciseListContainer.setVisibility(View.VISIBLE);
            }
            this.exerciseAdapter.setExercises(this.archivedExercises);
        }
    }

    public void setShowingActive(boolean showingActive) {
        if (this.showingActive != showingActive) {
            this.showingActive = showingActive;
            updateList();
        }
    }

    public void setActiveExercises(List<Exercise> activeExercises) {
        this.activeExercises = activeExercises;
        if (this.showingActive) {
            updateList();
        }
    }

    public void setArchivedExercises(List<Exercise> archivedExercises) {
        this.archivedExercises = archivedExercises;
        if (!this.showingActive) {
            updateList();
        }
    }
}
