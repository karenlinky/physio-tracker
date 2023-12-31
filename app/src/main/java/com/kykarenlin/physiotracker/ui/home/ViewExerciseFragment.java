package com.kykarenlin.physiotracker.ui.home;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentViewExerciseBinding;
import com.kykarenlin.physiotracker.enums.ExerciseBundleKeys;
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

        FragmentActivity fragmentActivity = getActivity();

        int exerciseId = getArguments().getInt(ExerciseBundleKeys.ID.toString());

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

//        ExerciseDetailsFragment exerciseDetailsFragment = ExerciseDetailsFragment.newInstance();
//
//        FragmentActivity fragmentActivity = getActivity();
//
//        fragmentActivity.getSupportFragmentManager().beginTransaction()
//                .replace(R.id.viewExerciseDetailsPlaceholder, exerciseDetailsFragment)
//                .commit();

        final Button btnVideoLinkClicked = binding.btnVideoLinkClicked;
        final TextView txtViewExerciseDescr = binding.txtViewExerciseDescr;

        final ImageButton btnDeleteExercise = binding.btnDeleteExercise;
        final Button btnEditExercise = binding.btnEditExercise;

        final Button btnArchive = binding.btnArchive;
        final Button btnUnarchive = binding.btnUnarchive;

        btnArchive.setOnClickListener(view -> {
            this.updateArchiveState(true, btnArchive, btnUnarchive);
        });

        btnUnarchive.setOnClickListener(view -> {
            this.updateArchiveState(false, btnArchive, btnUnarchive);
        });

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

                ExerciseDetailsFragment exerciseDetailsFragment = ExerciseDetailsFragment.newInstance(numSets, numReps, duration, durationUnit);

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.viewExerciseDetailsPlaceholder, exerciseDetailsFragment)
                        .commit();

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



                manageArchiveBtns(exercise.isArchived(), btnArchive, btnUnarchive);
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

        btnEditExercise.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ExerciseBundleKeys.ID.toString(), exercise.getId());
            bundle.putString(ExerciseBundleKeys.NAME.toString(), exercise.getName());
            bundle.putString(ExerciseBundleKeys.VIDEOURL.toString(), exercise.getVideoUrl());
            bundle.putString(ExerciseBundleKeys.NUMSETS.toString(), exercise.getNumSets());
            bundle.putInt(ExerciseBundleKeys.NUMREPS.toString(), exercise.getNumReps());
            bundle.putInt(ExerciseBundleKeys.DURATION.toString(), exercise.getRepDuration());
            bundle.putString(ExerciseBundleKeys.DURATION_UNIT.toString(), exercise.getRepDurationUnit());
            bundle.putString(ExerciseBundleKeys.DESCRIPTION.toString(), exercise.getDescription());
            bundle.putBoolean(ExerciseBundleKeys.IS_ARCHIVED.toString(), exercise.isArchived());
            bundle.putString(ExerciseBundleKeys.SESSION_STATUS.toString(), exercise.getSessionStatus());
            bundle.putInt(ExerciseBundleKeys.INTERVAL_BETWEEN_REP.toString(), exercise.getIntervalBetweenRep());
            bundle.putInt(ExerciseBundleKeys.PROGRESS_TIMESTAMP.toString(), exercise.getProgressTimestamp());
            Navigation.findNavController(root).navigate(R.id.action_viewExercise_to_editExercise, bundle);
        });

        return root;
    }

    private void updateArchiveState(boolean archived, Button btnArchive, Button btnUnarchive) {
        exercise.setIsArchived(archived);
        exerciseViewModel.update(exercise);
        String toastMessage = archived ? "Exercise archived" : "Exercise unarchived";
        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void manageArchiveBtns(boolean archived, Button btnArchive, Button btnUnarchive) {
        if (archived) {
            btnArchive.setVisibility(View.GONE);
            btnUnarchive.setVisibility(View.VISIBLE);
        } else {
            btnArchive.setVisibility(View.VISIBLE);
            btnUnarchive.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}