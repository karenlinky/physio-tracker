package com.kykarenlin.physiotracker.ui.exercisetracker;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;

import java.util.ArrayList;
import java.util.List;

public class TrackerExerciseListAdapter extends RecyclerView.Adapter<TrackerExerciseListAdapter.TrackerExerciseListHolder> {
    private List<ExerciseProgress> exercisesWithProgress = new ArrayList<>();
    private OnItemClickListener clickListener;

    private OnItemLongClickListener longClickListener;

    private Context context;
    @NonNull
    @Override
    public TrackerExerciseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.tracker_exercise_item, parent, false);
        return new TrackerExerciseListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerExerciseListHolder holder, int position) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;

        int enabledColor = ContextCompat.getColor(context, R.color.trackerListEnabled);
        int selectedColor = ContextCompat.getColor(context, R.color.trackerListSelected);
        int disabledColor = ContextCompat.getColor(context, R.color.trackerListDisabled);
        int enabledTextColor = ContextCompat.getColor(context, R.color.trackerListTextEnabled);
        int disabledTextColor = ContextCompat.getColor(context, R.color.trackerListTextDisabled);

        if (isNightMode) {
            enabledColor =  ContextCompat.getColor(context, R.color.trackerListEnabledDark);
            selectedColor =  ContextCompat.getColor(context, R.color.trackerListSelectedDark);
            disabledColor =  ContextCompat.getColor(context, R.color.trackerListDisabledDark);
            enabledTextColor = ContextCompat.getColor(context, R.color.trackerListTextEnabledDark);
            disabledTextColor = ContextCompat.getColor(context, R.color.trackerListTextDisabledDark);
        }

        ExerciseProgress currentExercise = exercisesWithProgress.get(position);
        holder.txtTrackerExerciseItemName.setText(currentExercise.getExercise().getName());
        if (currentExercise.getExercise().getSessionStatus().equals(ExerciseSessionStatus.COMPLETED.toString())) {
            // exercise completed
            holder.trackerItemCard.setBackgroundColor(disabledColor);
            holder.trackerItemIconStatusContainer.setVisibility(View.VISIBLE);
            holder.imgTrackerItemCheckmark.setVisibility(View.VISIBLE);
            holder.imgTrackerItemRemoved.setVisibility(View.GONE);
            holder.txtTrackerExerciseItemName.setTextColor(disabledTextColor);
        } else if (currentExercise.getExercise().getSessionStatus().equals(ExerciseSessionStatus.REMOVED_FROM_SESSION.toString())) {
            // exercise removed from session
            holder.trackerItemCard.setBackgroundColor(disabledColor);
            holder.trackerItemIconStatusContainer.setVisibility(View.VISIBLE);
            holder.imgTrackerItemCheckmark.setVisibility(View.GONE);
            holder.imgTrackerItemRemoved.setVisibility(View.VISIBLE);
            holder.txtTrackerExerciseItemName.setTextColor(disabledTextColor);
        } else if (currentExercise.getButtonDisabled()) {
            // session completed or session in progress
            holder.trackerItemCard.setBackgroundColor(disabledColor);
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
            holder.txtTrackerExerciseItemName.setTextColor(disabledTextColor);
        } else if (currentExercise.getSelected()) {
            // exercise gets selected
            holder.trackerItemCard.setBackgroundColor(selectedColor);
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
            holder.txtTrackerExerciseItemName.setTextColor(enabledTextColor);
        } else {
            // exercise not completed
            holder.trackerItemCard.setBackgroundColor(enabledColor);
            holder.trackerItemIconStatusContainer.setVisibility(View.INVISIBLE);
            holder.txtTrackerExerciseItemName.setTextColor(enabledTextColor);
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

        private ImageView imgTrackerItemRemoved;

        public TrackerExerciseListHolder(@NonNull View itemView) {
            super(itemView);
            txtTrackerExerciseItemName = itemView.findViewById(R.id.txtTrackerExerciseItemName);
            trackerItemCard = itemView.findViewById(R.id.trackerItemCard);
            trackerItemIconStatusContainer = itemView.findViewById(R.id.trackerItemIconStatusContainer);
            imgTrackerItemCheckmark = itemView.findViewById(R.id.imgTrackerItemCheckmark);
            imgTrackerItemRemoved = itemView.findViewById(R.id.imgTrackerItemRemoved);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(exercisesWithProgress.get(position));
                }
            });

            itemView.setOnLongClickListener(view -> {
                int position = getAdapterPosition();
                if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClick(exercisesWithProgress.get(position), itemView);
                }
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ExerciseProgress exerciseProgress);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ExerciseProgress exerciseProgress, View itemView);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
