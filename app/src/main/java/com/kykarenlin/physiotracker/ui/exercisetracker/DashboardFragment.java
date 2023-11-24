package com.kykarenlin.physiotracker.ui.exercisetracker;

import static com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment.DEFAULT_VALUE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentExerciseTrackerBinding;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.ExerciseControlObserver;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.ExerciseProgressObserver;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerStatusSubject;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerTextObserver;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

public class DashboardFragment extends Fragment {

    private FragmentExerciseTrackerBinding binding;

    private ExerciseViewModel exerciseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentExerciseTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ExerciseDetailsFragment exerciseDetailsFragment = ExerciseDetailsFragment.newInstance(
                DEFAULT_VALUE, 0, 0, "s");

        FragmentActivity fragmentActivity = getActivity();
        fragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.trackerDetailsPlaceholder, exerciseDetailsFragment)
                .commit();

        final RecyclerView rclvTrackerExercises = binding.rclvTrackerExercises;
        rclvTrackerExercises.setLayoutManager(new LinearLayoutManager(getContext()));
        rclvTrackerExercises.setHasFixedSize(true);

        final TrackerExerciseListAdapter adapter = new TrackerExerciseListAdapter();
        rclvTrackerExercises.setAdapter(adapter);

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);

        final TrackerStatusSubject trackerStatusSubject = new TrackerStatusSubject(fragmentActivity, getViewLifecycleOwner(), exerciseViewModel);



        ExerciseProgressObserver exerciseProgressObserver = new ExerciseProgressObserver(adapter);
        trackerStatusSubject.registerObserver(exerciseProgressObserver);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), exercises ->trackerStatusSubject.updateExercises(exercises));
        adapter.setOnItemClickListener(exerciseProgress -> trackerStatusSubject.onExerciseProgressClicked(exerciseProgress));



        final TextView txtTrackerStatus = binding.txtTrackerStatus;
        final Chronometer cnmtTracker = binding.cnmtTracker;
        final TextView txtTrackerExerciseName = binding.txtTrackerExerciseName;

        TrackerTextObserver trackerTextObserver = new TrackerTextObserver(trackerStatusSubject, txtTrackerStatus, cnmtTracker, txtTrackerExerciseName, exerciseDetailsFragment);
        trackerStatusSubject.registerObserver(trackerTextObserver);


        final Button btnStartExercise = binding.btnStartExercise;
        final LinearLayout exerciseOngoingButtonsContainer = binding.exerciseOngoingButtonsContainer;
        final Button btnCancelExercise = binding.btnCancelExercise;
        final Button btnFinishExercise = binding.btnFinishExercise;

        ExerciseControlObserver exerciseControlObserver = new ExerciseControlObserver(trackerStatusSubject, btnStartExercise, exerciseOngoingButtonsContainer, btnCancelExercise, btnFinishExercise);
        trackerStatusSubject.registerObserver(exerciseControlObserver);

        btnStartExercise.setOnClickListener(view -> trackerStatusSubject.startExercise());
        btnCancelExercise.setOnClickListener(view -> trackerStatusSubject.cancelExercise());
        btnFinishExercise.setOnClickListener(view -> trackerStatusSubject.finishExercise());



        final TextView selectExerciseHint = binding.selectExerciseHint;
        final ImageButton btnPauseSession = binding.btnPauseSession;
        final ImageButton btnFinishSession = binding.btnFinishSession;






        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}