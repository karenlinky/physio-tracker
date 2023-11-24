package com.kykarenlin.physiotracker.ui.commonfragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
import com.kykarenlin.physiotracker.databinding.FragmentExerciseDetailsBinding;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {

    public static final String DEFAULT_VALUE = "--";

    private static final String ARG_NUM_SETS = "numSets";
    private static final String ARG_NUM_REPS = "numReps";
    private static final String ARG_DURATION = "duration";
    private static final String ARG_DURATION_UNIT = "durationUnit";

    private String numSets;
    private int numReps;
    private int duration;
    private String durationUnit;


    private FragmentExerciseDetailsBinding binding;

    public ExerciseDetailsFragment() {}

    public static ExerciseDetailsFragment newInstance(String numSets, int numReps, int duration, String durationUnit) {
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUM_SETS, numSets);
        args.putInt(ARG_NUM_REPS, numReps);
        args.putInt(ARG_DURATION, duration);
        args.putString(ARG_DURATION_UNIT, durationUnit);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getDurationTxt(int duration, String durationUnit) {
        String strDuration = ExerciseDetailsFragment.DEFAULT_VALUE;
        if (duration != 0) {
            strDuration = duration + durationUnit;
        }
        return strDuration;
    }

    private void updateView() {
        if (binding == null) {
            return;
        }
        binding.txtNumSets.setText(numSets);
        if (numReps == 0) {
            binding.txtNumReps.setText(ExerciseDetailsFragment.DEFAULT_VALUE);
        } else {
            binding.txtNumReps.setText(String.valueOf(numReps));
        }
        String strDuration = ExerciseDetailsFragment.getDurationTxt(duration, durationUnit);
        binding.txtDuration.setText(strDuration);
    }

    public void updateValues(Exercise exercise) {
        if (exercise == null) {
            this.numSets = "--";
            this.numReps = 0;
            this.duration = 0;
            this.durationUnit = "s";
        } else {
            this.numSets = exercise.getNumSets();
            this.numReps = exercise.getNumReps();
            this.duration = exercise.getRepDuration();
            this.durationUnit = exercise.getRepDurationUnit();
        }
        updateView();
    }

    public void updateValues(String numSets, int numReps, int duration, String durationUnit) {
        this.numSets = numSets;
        this.numReps = numReps;
        this.duration = duration;
        this.durationUnit = durationUnit;
        updateView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numSets = getArguments().getString(ARG_NUM_SETS);
            numReps = getArguments().getInt(ARG_NUM_REPS);
            duration = getArguments().getInt(ARG_DURATION);
            durationUnit = getArguments().getString(ARG_DURATION_UNIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_exercise_details, container, false);

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        updateView();

        return root;
    }
}