package com.kykarenlin.physiotracker.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentHomeBinding;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.BaseFragment;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends BaseFragment {

    private ExerciseViewModel exerciseViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageButton btnAddExercise = binding.btnAddExercise;

        btnAddExercise.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("exerciseId", -1);
            Navigation.findNavController(root).navigate(R.id.action_home_to_editExercise, bundle);
        });



        final RecyclerView rclvExercises = binding.rclvExercises;
        rclvExercises.setLayoutManager(new LinearLayoutManager(getContext()));
        rclvExercises.setHasFixedSize(true);

        final ExerciseAdapter adapter = new ExerciseAdapter(getActivity());
        rclvExercises.setAdapter(adapter);


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                // update view
                adapter.setExercises(exercises);
//                if (exercises.size() == 0) return;
//                Toast.makeText(getActivity(), "Archived: " + exercises.get(0).getIsArchived() + " Completed: " + exercises.get(0).getIsCompleted() + " Timestamp: " + exercises.get(0).progressTimestamp(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Bundle bundle = new Bundle();
                bundle.putInt("exerciseId", exercise.getId());
                Navigation.findNavController(root).navigate(R.id.action_home_to_viewExercise, bundle);
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