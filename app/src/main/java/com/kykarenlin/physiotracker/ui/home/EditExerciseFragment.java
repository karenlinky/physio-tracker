package com.kykarenlin.physiotracker.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
import com.kykarenlin.physiotracker.enums.ExerciseBundleKeys;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.model.exercise.ExerciseRepository;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.net.URL;

public class EditExerciseFragment extends Fragment {

    private FragmentEditExerciseBinding binding;

    private ExerciseViewModel exerciseViewModel;

    private Exercise exercise;

    public static EditExerciseFragment newInstance() {
        return new EditExerciseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_edit_exercise, container, false);

        EditExerciseViewModel editExerciseViewModel =
                new ViewModelProvider(this).get(EditExerciseViewModel.class);

        binding = FragmentEditExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int exerciseId = getArguments().getInt(ExerciseBundleKeys.ID.toString());
        String initName = getArguments().getString(ExerciseBundleKeys.NAME.toString());
        String initVideoUrl = getArguments().getString(ExerciseBundleKeys.VIDEOURL.toString());
        String initNumSets = getArguments().getString(ExerciseBundleKeys.NUMSETS.toString());
        int numReps = getArguments().getInt(ExerciseBundleKeys.NUMREPS.toString());
        String initNumReps = String.valueOf(numReps);
        if (numReps == 0) {
            initNumReps = "";
        }
        int duration = getArguments().getInt(ExerciseBundleKeys.DURATION.toString());
        String initDuration = String.valueOf(duration);
        if (duration == 0) {
            initDuration = "";
        }
        String initDurationUnit = getArguments().getString(ExerciseBundleKeys.DURATIONUNIT.toString());
        String initDescription =getArguments().getString(ExerciseBundleKeys.DESCRIPTION.toString());

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        final EditText edtName = binding.edtName;
        final EditText edtVideoUrl = binding.edtVideoUrl;
        final EditText edtNumSets = binding.edtNumSets;
        final EditText edtNumReps = binding.edtNumReps;
        final EditText edtDuration = binding.edtDuration;
        final EditText edtDescription = binding.edtDescription;

        final Spinner spnDurationUnit = binding.spnDurationUnit;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.units, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDurationUnit.setAdapter(spinnerAdapter);
        spnDurationUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtName.setText(initName);
        edtVideoUrl.setText(initVideoUrl);
        edtNumSets.setText(initNumSets);
        edtNumReps.setText(initNumReps);
        edtDuration.setText(initDuration);
        edtDescription.setText(initDescription);

//        if (exerciseId != -1) {
//            exerciseViewModel.getExerciseById(exerciseId).observe(getViewLifecycleOwner(), new Observer<Exercise>() {
//                @Override
//                public void onChanged(Exercise fetchedExercise) {
//
//                    if (exercise != null || fetchedExercise == null) {
//                        return;
//                    }
//
//                    exercise = fetchedExercise;
//                    edtName.setText(exercise.getName());
//                    edtVideoUrl.setText(exercise.getVideoUrl());
//                    edtNumSets.setText(exercise.getNumSets());
//                    edtNumReps.setText(String.valueOf(exercise.getNumReps()));
//                    edtDuration.setText(String.valueOf(exercise.getRepDuration()));
//                    edtDescription.setText(exercise.getDescription());
//                }
//            });
//        }

        Toast.makeText(getContext(), "exercise id is " + exerciseId, Toast.LENGTH_SHORT).show();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}