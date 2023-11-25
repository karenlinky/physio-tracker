package com.kykarenlin.physiotracker.ui.home;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
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
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditExerciseFragment extends Fragment {

    private FragmentEditExerciseBinding binding;

    private ExerciseViewModel exerciseViewModel;

    private String unitSelected;

    private FragmentActivity fragmentActivity;

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

        fragmentActivity = getActivity();

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
        String sessionStatus = getArguments().getString(ExerciseBundleKeys.SESSION_STATUS.toString());
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

            private void displayError(String title, String errMsg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
                builder.setTitle(title).setMessage(errMsg);
                builder.setPositiveButton("Confirm", (dialogInterface, i) -> {});
                builder.show();
            }
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String videoUrl = edtVideoUrl.getText().toString().trim();
                String numSets = edtNumSets.getText().toString().trim();
                String numReps = edtNumReps.getText().toString().trim();
                String duration = edtDuration.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();

                List<String> emptyFields = new ArrayList<>();
                int numEmptyFields = 0;

                if (name.equals("")) {
                    emptyFields.add("name");
                }

                if (numSets.equals("")) {
                    emptyFields.add("number of sets");
                }

                if (numReps.equals("")) {
                    emptyFields.add("number of reps");
                }

                numEmptyFields = emptyFields.size();

                if (numEmptyFields > 0) {
                    String errMsgPrefix = "Please provide the ";
                    String errMsgSuffix = ".";
                    String errMsg = "";
                    for (int i = 0; i < numEmptyFields; i++) {
                        if (emptyFields.size() == 1) {
                            errMsg = emptyFields.get(i);
                            break;
                        }
                        if (i == 0) {
                            errMsg = emptyFields.get(i);
                        } else if (i < numEmptyFields - 1) {
                            errMsg += ", " + emptyFields.get(i);
                        } else {
                            // last element
                            errMsg += " and " + emptyFields.get(i);
                        }
                    }
                    errMsg = errMsgPrefix + errMsg + errMsgSuffix;

                    displayError("Required Fields", errMsg);
                    return;
                }

                int intNumReps = 0;
                try {
                    intNumReps = Integer.parseInt(numReps);
                } catch (Exception e) {
                    displayError("Invalid Value", "Number of Reps only accept numerical values (0-9).");
                    return;
                }
                boolean emptyDuration = duration.equals("");

                int intDuration = 0;
                if (!emptyDuration) {
                    try {
                        intDuration = Integer.parseInt(duration);
                    } catch (Exception e) {
                        displayError("Invalid Value", "Duration only accept numerical values (0-9).");
                        return;
                    }
                }
                String errMsg = "";

                if (intNumReps == 0 || (!emptyDuration && intDuration < 0)) {
                    String zeroError = " cannot be 0.";
                    String durationError = " You may leave the duration empty if you do not wish to specify one.";
                    if (intNumReps == 0 && (!emptyDuration && intDuration < 0)) {
                        errMsg = "Number of reps and duration" + zeroError + durationError;
                    } else if (!emptyDuration && intDuration < 0) {
                        errMsg = "Duration" + zeroError + durationError;
                    } else {
                        errMsg = "Number of reps" + zeroError;
                    }
                    displayError("Invalid Value", errMsg);
                    return;
                }



                Exercise newExercise = new Exercise(
                        name,
                        videoUrl,
                        numSets,
                        intNumReps,
                        intDuration,
                        unitSelected,
                        description);
                if (exerciseId == -1) {
                    exerciseViewModel.insert(newExercise);
                    Toast.makeText(getContext(), "New exercise created", Toast.LENGTH_SHORT).show();
                } else {
                    newExercise.setId(exerciseId);
                    newExercise.setIsArchived(isArchived);
                    newExercise.setSessionStatus(sessionStatus);
                    newExercise.setProgressTimestamp(progressTimestamp);
                    exerciseViewModel.update(newExercise);
                    Toast.makeText(getContext(), "Exercise updated", Toast.LENGTH_SHORT).show();
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