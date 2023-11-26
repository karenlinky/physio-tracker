package com.kykarenlin.physiotracker.ui.developer;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentDeveloperBinding;
import com.kykarenlin.physiotracker.databinding.FragmentSettingsBinding;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.StopwatchNotificationObserver;
import com.kykarenlin.physiotracker.utils.SharedPref;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

public class DeveloperFragment extends Fragment {

    private FragmentDeveloperBinding binding;

    private SharedPref sharedPref;

    private ExerciseViewModel exerciseViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeveloperBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        this.sharedPref = new SharedPref(getActivity().getPreferences(Context.MODE_PRIVATE));



        final Button btnDeveloperLoadTestData = binding.btnDeveloperLoadTestData;
        final Button btnDeveloperClearData = binding.btnDeveloperClearData;

        btnDeveloperClearData.setOnClickListener(view -> {
            clearData();
            Toast.makeText(getContext(), "Data successfully cleared", Toast.LENGTH_SHORT).show();
        });
        btnDeveloperLoadTestData.setOnClickListener(view -> {
            loadTestData();
            Toast.makeText(getContext(), "Data successfully loaded", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void clearData() {
        exerciseViewModel.deleteAllExercises();
        this.sharedPref.clearData();
        StopwatchNotificationObserver.cancelScheduledNotifications(getContext());
    }

    private void loadTestData() {
        this.clearData();
        exerciseViewModel.loadTestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}