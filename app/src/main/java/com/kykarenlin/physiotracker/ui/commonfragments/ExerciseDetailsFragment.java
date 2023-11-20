package com.kykarenlin.physiotracker.ui.commonfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
import com.kykarenlin.physiotracker.databinding.FragmentExerciseDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_SETS = "numSets";
    private static final String ARG_PARAM_REPS = "numReps";
    private static final String ARG_PARAM_DURATION = "duration";
    private static final String ARG_PARAM_DURATION_UNIT = "durationUnit";

    private String numSets;
    private int numReps;
    private int duration;
    private String durationUnit;


    private FragmentExerciseDetailsBinding binding;

    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }

    public static ExerciseDetailsFragment newInstance() {
        return new ExerciseDetailsFragment();
    }

//    public static ExerciseDetailsFragment newInstance(String numSets, int numReps, int duration, String durationUnit) {
//        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM_SETS, numSets);
//        args.putInt(ARG_PARAM_REPS, numReps);
//        args.putInt(ARG_PARAM_DURATION, duration);
//        args.putString(ARG_PARAM_DURATION_UNIT, durationUnit);
//        fragment.setArguments(args);
//        return fragment;
//    }

    private void updateView() {
        if (binding == null) {
            return;
        }
        binding.txtNumSets.setText(numSets);
        binding.txtNumReps.setText(String.valueOf(numReps));
        String strDuration = "--";
        if (duration != 0) {
            strDuration = duration + durationUnit;
        }
        binding.txtDuration.setText(strDuration);
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
//            numSets = getArguments().getString(ARG_PARAM_SETS);
//            numReps = getArguments().getInt(ARG_PARAM_REPS);
//            duration = getArguments().getInt(ARG_PARAM_DURATION);
//            durationUnit = getArguments().getString(ARG_PARAM_DURATION_UNIT);
            numSets = "HI";
            numReps = 2;
            duration = 5;
            durationUnit = "BYE";
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