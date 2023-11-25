package com.kykarenlin.physiotracker.ui.exercisetracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;

import java.util.ArrayList;
import java.util.List;

public class TrackerExerciseListAdapter extends RecyclerView.Adapter<TrackerExerciseListAdapter.TrackerExerciseListHolder> {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();
    private OnItemClickListener listener;
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
        if (currentExercise.getExercise().getSessionStatus().equals(ExerciseSessionStatus.COMPLETED.toString())) {
            // exercise completed
            holder.trackerItemCard.setBackgroundColor(Color.parseColor("#FFEDEDED"));
            holder.trackerItemIconStatusContainer.setVisibility(View.VISIBLE);
        } else if (currentExercise.getButtonDisabled()) {
            // session completed or session in progress
            holder.trackerItemCard.setBackgroundColor(Color.parseColor("#FFEDEDED"));
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
        } else if (currentExercise.getSelected()) {
            // exercise gets selected
            holder.trackerItemCard.setBackgroundColor(Color.parseColor("#FFebf4ff"));
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
        } else {
            // exercise not completed
            holder.trackerItemCard.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
        }
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
        private CardView trackerItemCard;

        private FrameLayout trackerItemIconStatusContainer;

        private ImageView imgTrackerItemCheckmark;

        public TrackerExerciseListHolder(@NonNull View itemView) {
            super(itemView);
            txtTrackerExerciseItemName = itemView.findViewById(R.id.txtTrackerExerciseItemName);
            trackerItemCard = itemView.findViewById(R.id.trackerItemCard);
            trackerItemIconStatusContainer = itemView.findViewById(R.id.trackerItemIconStatusContainer);
            imgTrackerItemCheckmark = itemView.findViewById(R.id.imgTrackerItemCheckmark);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(exercisesWithProgress.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ExerciseProgress exerciseProgress);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
