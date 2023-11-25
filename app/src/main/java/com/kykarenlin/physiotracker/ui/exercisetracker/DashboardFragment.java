package com.kykarenlin.physiotracker.ui.exercisetracker;

import static com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment.DEFAULT_VALUE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentExerciseTrackerBinding;
import com.kykarenlin.physiotracker.enums.ExerciseBundleKeys;
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.SessionControlObserver;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerStopwatchObserver;
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

        Context context = getContext();

        final RecyclerView rclvTrackerExercises = binding.rclvTrackerExercises;
        rclvTrackerExercises.setLayoutManager(new LinearLayoutManager(context));
        rclvTrackerExercises.setHasFixedSize(true);

        final TrackerExerciseListAdapter adapter = new TrackerExerciseListAdapter();
        rclvTrackerExercises.setAdapter(adapter);

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);


        final TrackerStatusSubject trackerStatusSubject = new TrackerStatusSubject(context, fragmentActivity, getViewLifecycleOwner(), exerciseViewModel);



        ExerciseProgressObserver exerciseProgressObserver = new ExerciseProgressObserver(trackerStatusSubject, adapter);
        trackerStatusSubject.registerObserver(exerciseProgressObserver);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), exercises ->trackerStatusSubject.updateExercises(exercises));
        adapter.setOnItemClickListener(exerciseProgress -> trackerStatusSubject.onExerciseProgressClicked(exerciseProgress));


        final Chronometer cnmtTracker = binding.cnmtTracker;
        TrackerStopwatchObserver trackerStopwatchObserver = new TrackerStopwatchObserver(trackerStatusSubject, cnmtTracker);
        trackerStatusSubject.registerObserver(trackerStopwatchObserver);

        final ImageView sessionStatusIndicator = binding.sessionStatusIndicator;
        final TextView txtTrackerStatus = binding.txtTrackerStatus;
        final TextView txtTrackerExerciseName = binding.txtTrackerExerciseName;
        final TextView selectExerciseHint = binding.selectExerciseHint;

        TrackerTextObserver trackerTextObserver = new TrackerTextObserver(trackerStatusSubject,sessionStatusIndicator, txtTrackerStatus, txtTrackerExerciseName, exerciseDetailsFragment, selectExerciseHint);
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

        final ImageButton btnContinueSession = binding.btnContinueSession;
        final ImageButton btnPauseSession = binding.btnPauseSession;
        final ImageButton btnFinishSession = binding.btnFinishSession;
        final ImageButton btnResetSession = binding.btnResetSession;

        SessionControlObserver sessionControlObserver = new SessionControlObserver(trackerStatusSubject, btnContinueSession, btnPauseSession, btnFinishSession, btnResetSession);
        trackerStatusSubject.registerObserver(sessionControlObserver);

        btnContinueSession.setOnClickListener(view -> {trackerStatusSubject.continueSession();});
        btnPauseSession.setOnClickListener(view -> {trackerStatusSubject.pauseSession();});
        btnFinishSession.setOnClickListener(view -> {trackerStatusSubject.finishSession();});
        btnResetSession.setOnClickListener(view -> {trackerStatusSubject.resetSession();});



        trackerStatusSubject.notifyInitialState();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}