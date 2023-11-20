package com.kykarenlin.physiotracker.ui.home;

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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exercises = new ArrayList<>();

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);

        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        holder.txtExerciseName.setText(currentExercise.getName());
        holder.txtNumSets.setText(currentExercise.getNumSets());
        holder.txtNumReps.setText(String.valueOf(currentExercise.getNumReps()));
        String strDuration = "--";
        int intDuration = currentExercise.getRepDuration();
        if (intDuration != 0) {
            strDuration = intDuration + currentExercise.getRepDurationUnit();
        }
        holder.txtDuration.setText(strDuration);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView txtExerciseName;
        private TextView txtNumSets;
        private TextView txtNumReps;
        private TextView txtDuration;

        public ExerciseHolder (View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);
            txtNumSets = itemView.findViewById(R.id.txtNumSets);
            txtNumReps = itemView.findViewById(R.id.txtNumReps);
            txtDuration = itemView.findViewById(R.id.txtDuration);
        }
    }
}
