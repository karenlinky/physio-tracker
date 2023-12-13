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
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerNotifPreferences;
import com.kykarenlin.physiotracker.utils.SharedPref;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

public class DeveloperFragment extends Fragment {

    private FragmentDeveloperBinding binding;

    private SharedPref sharedPref;

    private ExerciseViewModel exerciseViewModel;

    private EventViewModel eventViewModel;

    private TrackerNotifPreferences trackerNotifPreferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeveloperBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        eventViewModel =
                new ViewModelProvider(this).get(EventViewModel.class);

        this.sharedPref = new SharedPref(getActivity().getPreferences(Context.MODE_PRIVATE));



        final Button btnDeveloperLoadTestData = binding.btnDeveloperLoadTestData;
        final Button btnDeveloperClearData = binding.btnDeveloperClearData;
        final Button btnDeveloperLoadTestDataWithoutClearingSettings = binding.btnDeveloperLoadTestDataWithoutClearingSettings;
        final Button btnDeveloperClearDataWithoutClearingSettings = binding.btnDeveloperClearDataWithoutClearingSettings;


        btnDeveloperLoadTestData.setOnClickListener(view -> {
            loadTestData();
            Toast.makeText(getContext(), "Data successfully loaded", Toast.LENGTH_SHORT).show();
        });
        btnDeveloperClearData.setOnClickListener(view -> {
            clearData();
            Toast.makeText(getContext(), "Data successfully cleared", Toast.LENGTH_SHORT).show();
        });
        btnDeveloperLoadTestDataWithoutClearingSettings.setOnClickListener(view -> {
            loadTestDataWithoutClearingPreference();
            Toast.makeText(getContext(), "Data successfully loaded", Toast.LENGTH_SHORT).show();
        });
        btnDeveloperClearDataWithoutClearingSettings.setOnClickListener(view -> {
            clearDataWithoutClearingPreference();
            Toast.makeText(getContext(), "Data successfully cleared", Toast.LENGTH_SHORT).show();
        });

        this.trackerNotifPreferences = TrackerNotifPreferences.getInstance(getContext(), getActivity());

        return root;
    }

    private void loadNotifPreferences() {
        trackerNotifPreferences.saveStopwachNotificationSettings(
                true,
                "8",
                "10",
                "12",
                "🤔 It's been awhile. I think you should be done by now.",
                "🤔 I hope you are not slacking.",
                "😒 I am a bit disappointed.",
                "3",
                "5",
                "8",
                "⏲️ Time's up! Break is over.",
                "🫵 Get back to your exercise!",
                "😶 You are a failure. I am speechless."
        );
    }

    private void clearData() {
        this.sharedPref.clearData();
        this.clearDataWithoutClearingPreference();
    }

    private void loadTestData() {
        this.clearData();
        exerciseViewModel.loadTestData();
        eventViewModel.loadTestData();
        this.loadNotifPreferences();
    }

    private void clearDataWithoutClearingPreference() {
        exerciseViewModel.deleteAllExercises();
        eventViewModel.deleteAllEvents();
        StopwatchNotificationObserver.cancelScheduledNotifications(getContext());
    }

    private void loadTestDataWithoutClearingPreference() {
        this.clearDataWithoutClearingPreference();
        exerciseViewModel.loadTestData();
        eventViewModel.loadTestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}