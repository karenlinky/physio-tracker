package com.kykarenlin.physiotracker.ui.eventlogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEventLogsBinding;
import com.kykarenlin.physiotracker.enums.EventBundleKeys;
import com.kykarenlin.physiotracker.enums.EventImprovementStatus;
import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;

import java.util.Calendar;
import java.util.List;

public class EventLogsFragment extends Fragment {

    private EventViewModel eventViewModel;
    private List<Event> allEventsWithStartDate;
    private List<Event> allEventsWithoutStartDate;
    private List<Event> allArchivedEventsWithStartDate;
    private List<Event> allArchivedEventsWithoutStartDate;

    private FragmentEventLogsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEventLogsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        eventViewModel =
                new ViewModelProvider(this).get(EventViewModel.class);


        final ImageButton btnAddEvent = binding.btnAddEvent;

        btnAddEvent.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            Bundle bundle = new Bundle();
            bundle.putInt(EventBundleKeys.ID.toString(), -1);
            bundle.putString(EventBundleKeys.DETAILS.toString(), "");
            bundle.putLong(EventBundleKeys.START_TIME.toString(), cal.getTimeInMillis());
            bundle.putLong(EventBundleKeys.END_TIME.toString(), cal.getTimeInMillis());
            bundle.putBoolean(EventBundleKeys.IS_ACTIVITY.toString(), false);
            bundle.putBoolean(EventBundleKeys.IS_PAIN_DISCOMFORT.toString(), false);
            bundle.putString(EventBundleKeys.IMPROVEMENT_STATUS.toString(), EventImprovementStatus.UNCHANGED.toString());
            bundle.putBoolean(EventBundleKeys.IS_ARCHIVED.toString(), false);
            Navigation.findNavController(root).navigate(R.id.action_event_to_editEvent, bundle);
        });










        RecyclerView rclvEvents = binding.rclvEvents;
        rclvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        rclvEvents.setHasFixedSize(true);

        EventAdapter eventListAdapter = new EventAdapter();
        rclvEvents.setAdapter(eventListAdapter);

        EventListManager eventListManager = new EventListManager(eventListAdapter);

        eventViewModel.getEventsWithStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                Log.e("TAG", "onChanged: WITH");
                eventListManager.setAllEventsWithStartDate(events);
            }
        });

        eventViewModel.getEventsWithoutStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                Log.e("TAG", "onChanged: WITHOUT");
                eventListManager.setAllEventsWithoutStartDate(events);
            }
        });

        eventViewModel.getArchivedEventsWithStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                Log.e("TAG", "onChanged: ARCHIVED WITH");
                eventListManager.setAllArchivedEventsWithStartDate(events);
            }
        });

        eventViewModel.getArchivedEventsWithoutStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                Log.e("TAG", "onChanged: ARCHIVED WITHOUT");
                eventListManager.setAllArchivedEventsWithoutStartDate(events);
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