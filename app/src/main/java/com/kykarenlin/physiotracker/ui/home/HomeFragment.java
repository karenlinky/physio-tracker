package com.kykarenlin.physiotracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentHomeBinding;
import com.kykarenlin.physiotracker.enums.ExerciseBundleKeys;
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;
import com.kykarenlin.physiotracker.enums.ListTabs;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.commonfragments.BaseFragment;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.HashMap;
import java.util.List;

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
            bundle.putInt(ExerciseBundleKeys.ID.toString(), -1);
            bundle.putString(ExerciseBundleKeys.NAME.toString(), "");
            bundle.putString(ExerciseBundleKeys.VIDEOURL.toString(), "");
            bundle.putString(ExerciseBundleKeys.NUMSETS.toString(), "");
            bundle.putInt(ExerciseBundleKeys.NUMREPS.toString(), 0);
            bundle.putInt(ExerciseBundleKeys.DURATION.toString(), 0);
            bundle.putString(ExerciseBundleKeys.DURATION_UNIT.toString(), "s");
            bundle.putString(ExerciseBundleKeys.DESCRIPTION.toString(), "");
            bundle.putBoolean(ExerciseBundleKeys.IS_ARCHIVED.toString(), false);
            bundle.putString(ExerciseBundleKeys.SESSION_STATUS.toString(), ExerciseSessionStatus.NOT_COMPLETED.toString());
            bundle.putInt(ExerciseBundleKeys.INTERVAL_BETWEEN_REP.toString(), 3);
            bundle.putInt(ExerciseBundleKeys.PROGRESS_TIMESTAMP.toString(), 0);
            Navigation.findNavController(root).navigate(R.id.action_home_to_editExercise, bundle);
        });



        final RecyclerView rclvExercises = binding.rclvExercises;
        rclvExercises.setLayoutManager(new LinearLayoutManager(getContext()));
        rclvExercises.setHasFixedSize(true);

        final ExerciseAdapter adapter = new ExerciseAdapter();
        rclvExercises.setAdapter(adapter);

        final RelativeLayout emptyExerciseListContainer = binding.emptyExerciseListContainer;
        final TextView emptyExerciseListMsg = binding.emptyExerciseListMsg;
        final ScrollView exerciseListContainer = binding.exerciseListContainer;

        final TabLayout tabsExerciseList = binding.tabsExerciseList;

        ExerciseListManager exerciseListManager = new ExerciseListManager(adapter, emptyExerciseListContainer, emptyExerciseListMsg, exerciseListContainer);

        HashMap<ListTabs, Integer> tabsIndices = new HashMap<>();
        tabsIndices.put(ListTabs.ACTIVE, 0);
        tabsIndices.put(ListTabs.ARCHIVED, 1);

        TabLayout.Tab selectedListTab = tabsExerciseList.getTabAt(tabsIndices.get(ListTabs.ACTIVE));
        if (!ExerciseListManager.isShowingActive()) {
            selectedListTab = tabsExerciseList.getTabAt(tabsIndices.get(ListTabs.ARCHIVED));
        }
        if (selectedListTab != null) {
            selectedListTab.select();
        }
        tabsExerciseList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabIndex = tab.getPosition();
                if (selectedTabIndex == tabsIndices.get(ListTabs.ACTIVE)) {
                    exerciseListManager.setShowingActive(true);
                } else if (selectedTabIndex == tabsIndices.get(ListTabs.ARCHIVED)) {
                    exerciseListManager.setShowingActive(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                return;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                return;
            }
        });

        exerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);
//        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
//            @Override
//            public void onChanged(List<Exercise> exercises) {
//                // update view
//                adapter.setExercises(exercises);
//                if (exercises.size() == 0) {
//                    emptyExerciseListContainer.setVisibility(View.VISIBLE);
//                    exerciseListContainer.setVisibility(View.GONE);
//                } else {
//                    emptyExerciseListContainer.setVisibility(View.GONE);
//                    exerciseListContainer.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        exerciseViewModel.getAllActiveExercises().observe(getViewLifecycleOwner(), exercises -> {
            exerciseListManager.setActiveExercises(exercises);
        });

        exerciseViewModel.getAllArchivedExercises().observe(getViewLifecycleOwner(), exercises -> {
            exerciseListManager.setArchivedExercises(exercises);
        });


        adapter.setOnItemClickListener(exercise -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ExerciseBundleKeys.ID.toString(), exercise.getId());
            Navigation.findNavController(root).navigate(R.id.action_home_to_viewExercise, bundle);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}