package com.kykarenlin.physiotracker.ui.exercisetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.ArrayList;
import java.util.List;

public class TrackerExerciseListAdapter extends RecyclerView.Adapter<TrackerExerciseListAdapter.TrackerExerciseListHolder> {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();
    @NonNull
    @Override
    public TrackerExerciseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracker_exercise_item, parent, false);
        return new TrackerExerciseListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerExerciseListHolder holder, int position) {
        ExerciseProgress currentExercise = exercisesWithProgress.get(position);
        holder.txtTrackerExerciseItemName.setText(currentExercise.getExercise().getName());
    }

    @Override
    public int getItemCount() {
        return exercisesWithProgress.size();
    }

    public void setExercises(List<ExerciseProgress> exercisesWithProgress) {
        this.exercisesWithProgress = exercisesWithProgress;
        notifyDataSetChanged();
    }

    class TrackerExerciseListHolder extends RecyclerView.ViewHolder {
        private TextView txtTrackerExerciseItemName;

        public TrackerExerciseListHolder(@NonNull View itemView) {
            super(itemView);
            txtTrackerExerciseItemName = itemView.findViewById(R.id.txtTrackerExerciseItemName);
        }
    }
}
