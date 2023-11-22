package com.kykarenlin.physiotracker.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;
import com.kykarenlin.physiotracker.enums.ExerciseBundleKeys;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

public class EditExerciseFragment extends Fragment {

    private FragmentEditExerciseBinding binding;

    private ExerciseViewModel exerciseViewModel;

    private String unitSelected;

    public static EditExerciseFragment newInstance() {
        return new EditExerciseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_edit_exercise, container, false);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

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
        String initDurationUnit = getArguments().getString(ExerciseBundleKeys.DURATION_UNIT.toString());
        String initDescription = getArguments().getString(ExerciseBundleKeys.DESCRIPTION.toString());
        boolean isArchived = getArguments().getBoolean(ExerciseBundleKeys.IS_ARCHIVED.toString());
        boolean isCompleted = getArguments().getBoolean(ExerciseBundleKeys.IS_COMPLETED.toString());
        int progressTimestamp = getArguments().getInt(ExerciseBundleKeys.PROGRESS_TIMESTAMP.toString());

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

        unitSelected = initDurationUnit;
        spnDurationUnit.setSelection(spinnerAdapter.getPosition(unitSelected));
        spnDurationUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitSelected = adapterView.getItemAtPosition(i).toString();
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

        final Button btnSave = binding.btnSave;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String videoUrl = edtVideoUrl.getText().toString();
                String numSets = edtNumSets.getText().toString();
                String numReps = edtNumReps.getText().toString();
                String duration = edtDuration.getText().toString();
                String description = edtDescription.getText().toString();
                Exercise newExercise = new Exercise(
                        name,
                        videoUrl,
                        numSets,
                        Integer.parseInt(numReps),
                        Integer.parseInt(duration),
                        unitSelected,
                        description);
                if (exerciseId == -1) {
                    exerciseViewModel.insert(newExercise);
                } else {
                    newExercise.setId(exerciseId);
                    newExercise.setIsArchived(isArchived);
                    newExercise.setIsCompleted(isCompleted);
                    newExercise.setProgressTimestamp(progressTimestamp);
                    exerciseViewModel.update(newExercise);
                }

                getActivity().getSupportFragmentManager().popBackStack();
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