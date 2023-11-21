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
import android.widget.EditText;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
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

        int exerciseId = getArguments().getInt("exerciseId");

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        final EditText edtName = binding.edtName;
        final EditText edtVideoUrl = binding.edtVideoUrl;
        final EditText edtNumSets = binding.edtNumSets;
        final EditText edtNumReps = binding.edtNumReps;

        if (exerciseId != -1) {
            exerciseViewModel.getExerciseById(exerciseId).observe(getViewLifecycleOwner(), new Observer<Exercise>() {
                @Override
                public void onChanged(Exercise fetchedExercise) {

                    if (exercise == null) {
//                        btnDeleteExercise.setEnabled(false);
                    } else {
//                        btnDeleteExercise.setEnabled(true);
                    }

                    if (fetchedExercise == null) {
                        return;
                    }

                    exercise = fetchedExercise;
                    edtName.setText(exercise.getName());
                    edtVideoUrl.setText(exercise.getVideoUrl());
                    edtNumSets.setText(exercise.getNumSets());
                    edtNumReps.setText(String.valueOf(exercise.getNumReps()));
                }
            });
        }

        Toast.makeText(getContext(), "exercise id is " + exerciseId, Toast.LENGTH_SHORT).show();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}