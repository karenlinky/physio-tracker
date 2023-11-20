package com.kykarenlin.physiotracker.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
import com.kykarenlin.physiotracker.databinding.FragmentHomeBinding;
import com.kykarenlin.physiotracker.databinding.FragmentViewExerciseBinding;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class ViewExerciseFragment extends Fragment {

    private FragmentViewExerciseBinding binding;

    private ExerciseViewModel exerciseViewModel;

    private Exercise exercise;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentViewExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int exerciseId = getArguments().getInt("exerciseId");

        final TextView txtViewExerciseName = binding.txtViewExerciseName;

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                // update view
                if (exercises.size() == 0) {
                    return;         // list not fetched
                }
                List<Exercise> targetExercises = exercises.stream()
                        .filter(exercise -> exercise.getId() == exerciseId)
                        .collect(Collectors.toList());
                if (targetExercises.size() == 0) {
                    // TODO: data error return
                    return;
                }
                exercise = targetExercises.get(0);
                txtViewExerciseName.setText(exercise.getName());

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}