package com.kykarenlin.physiotracker.ui.eventlogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kykarenlin.physiotracker.databinding.FragmentEventLogsBinding;

public class EventLogsFragment extends Fragment {

    private FragmentEventLogsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventLogsViewModel eventsLogsViewModel =
                new ViewModelProvider(this).get(EventLogsViewModel.class);

        binding = FragmentEventLogsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        eventsLogsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}