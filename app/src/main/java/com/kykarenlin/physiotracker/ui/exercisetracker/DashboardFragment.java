package com.kykarenlin.physiotracker.ui.exercisetracker;

import static com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment.DEFAULT_VALUE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentExerciseTrackerBinding;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.ExerciseDetailsFragment;
import com.kykarenlin.physiotracker.ui.home.ExerciseAdapter;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.List;

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

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.trackerDetailsPlaceholder, exerciseDetailsFragment)
                .commit();

        final RecyclerView rclvTrackerExercises = binding.rclvTrackerExercises;
        rclvTrackerExercises.setLayoutManager(new LinearLayoutManager(getContext()));
        rclvTrackerExercises.setHasFixedSize(true);

        final TrackerExerciseListAdapter adapter = new TrackerExerciseListAdapter();
        rclvTrackerExercises.setAdapter(adapter);

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), exercises -> adapter.setExercises(exercises));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}