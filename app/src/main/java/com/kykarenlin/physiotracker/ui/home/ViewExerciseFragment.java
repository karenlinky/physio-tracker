package com.kykarenlin.physiotracker.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentViewExerciseBinding;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.BaseFragment;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.net.URL;

public class ViewExerciseFragment extends BaseFragment {

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
        final TextView txtDescriptionLabel = binding.txtDescriptionLabel;

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

//        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
//            @Override
//            public void onChanged(List<Exercise> exercises) {
//                // update view
//                if (exercises.size() == 0) {
//                    return;         // list not fetched
//                }
//                List<Exercise> targetExercises = exercises.stream()
//                        .filter(exercise -> exercise.getId() == exerciseId)
//                        .collect(Collectors.toList());
//                if (targetExercises.size() == 0) {
//                    // TODO: data error return
//                    return;
//                }
//                exercise = targetExercises.get(0);
//                txtViewExerciseName.setText(exercise.getName());
//
//            }
//        });

        ExerciseDetailsFragment exerciseDetailsFragment = ExerciseDetailsFragment.newInstance();

        FragmentActivity fragmentActivity = getActivity();

        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.viewExerciseDetailsPlaceholder, exerciseDetailsFragment)
                .commit();

        final Button btnVideoLinkClicked = binding.btnVideoLinkClicked;
        final TextView txtViewExerciseDescr = binding.txtViewExerciseDescr;

        final Button btnDeleteExercise = binding.btnDeleteExercise;
        final Button btnEditExercise = binding.btnEditExercise;

        exerciseViewModel.getExerciseById(exerciseId).observe(getViewLifecycleOwner(), new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise fetchedExercise) {
                // update view
//                if (exercises.size() == 0) {
//                    return;         // list not fetched
//                }
//                List<Exercise> targetExercises = exercises.stream()
//                        .filter(exercise -> exercise.getId() == exerciseId)
//                        .collect(Collectors.toList());
//                if (targetExercises.size() == 0) {
//                    return;
//                }

                if (exercise == null) {
                    btnDeleteExercise.setEnabled(false);
                } else {
                    btnDeleteExercise.setEnabled(true);
                }

                if (fetchedExercise == null) {
                    return;
                }

                btnDeleteExercise.setEnabled(true);

                exercise = fetchedExercise;

                txtViewExerciseName.setText(exercise.getName());

                String numSets = exercise.getNumSets();
                int numReps = exercise.getNumReps();
                int duration = exercise.getRepDuration();
                String durationUnit = exercise.getRepDurationUnit();

                exerciseDetailsFragment.updateValues(numSets, numReps, duration, durationUnit);

                String videoUrl = exercise.getVideoUrl();

                if (!videoUrl.equals("")) {
                    try {
                        new URL(videoUrl).toURI();
                        Uri uri = Uri.parse(videoUrl);
                        btnVideoLinkClicked.setOnClickListener(view -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        });
                    } catch (Exception e) {
                        btnVideoLinkClicked.setEnabled(false);
                    }
                } else {
                    btnVideoLinkClicked.setEnabled(false);
                }

                String description = exercise.getDescription();

                if (!description.equals("")) {
                    txtViewExerciseDescr.setText(description);
                } else {
                    txtDescriptionLabel.setText("");
                }
            }
        });
        btnDeleteExercise.setOnClickListener(view -> {
            btnEditExercise.setEnabled(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
            builder.setTitle("Confirm Deletion").setMessage("Are you sure you want to delete this exercise?");
            builder.setPositiveButton(R.string.lbl_confirm, (dialogInterface, i) -> {
                exerciseViewModel.delete(exercise);
                fragmentActivity.getSupportFragmentManager().popBackStack();
            });

            builder.setNegativeButton(R.string.lbl_cancel, (dialogInterface, i) -> {});
            builder.setOnDismissListener(dialogInterface -> btnEditExercise.setEnabled(true));

            builder.show();
        });

        btnEditExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("exerciseId", exercise.getId());
                Navigation.findNavController(root).navigate(R.id.action_viewExercise_to_editExercise, bundle);
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