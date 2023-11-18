package com.kykarenlin.physiotracker.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditExerciseBinding;

public class EditExerciseFragment extends Fragment {

    private FragmentEditExerciseBinding binding;

    public static EditExerciseFragment newInstance() {
        return new EditExerciseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_edit_exercise, container, false);

        EditExerciseViewModel editExerciseViewModel =
                new ViewModelProvider(this).get(EditExerciseViewModel.class);

        binding = FragmentEditExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}