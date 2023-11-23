package com.kykarenlin.physiotracker.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;

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

        String numSets = currentExercise.getNumSets();
        int numReps = currentExercise.getNumReps();
        int duration = currentExercise.getRepDuration();
        String durationUnit = currentExercise.getRepDurationUnit();

//        holder.exerciseDetailsFragment.updateValues(numSets, numReps, duration, durationUnit);
        holder.txtNumSets.setText(numSets);
        holder.txtNumReps.setText(String.valueOf(numReps));
        String strDuration = ExerciseDetailsFragment.getDurationTxt(duration, durationUnit);
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
//        private ExerciseDetailsFragment exerciseDetailsFragment;

        public ExerciseHolder (View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);

            txtNumSets = itemView.findViewById(R.id.txtNumSets);
            txtNumReps = itemView.findViewById(R.id.txtNumReps);
            txtDuration = itemView.findViewById(R.id.txtDuration);

//            FrameLayout exerciseItemDetailsPlaceholder = itemView.findViewById(R.id.exerciseItemDetailsPlaceholder);
//            int newId = View.generateViewId();
//
//            exerciseItemDetailsPlaceholder.setId(newId);
//
//            exerciseDetailsFragment = ExerciseDetailsFragment.newInstance();
//
//            fragmentActivity.getSupportFragmentManager().beginTransaction()
//                    .replace(newId, exerciseDetailsFragment)
//                    .commit();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
