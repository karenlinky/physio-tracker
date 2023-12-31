package com.kykarenlin.physiotracker.ui.eventlogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEventLogsBinding;
import com.kykarenlin.physiotracker.enums.EditMode;
import com.kykarenlin.physiotracker.enums.EventBundleKeys;
import com.kykarenlin.physiotracker.enums.EventImprovementStatus;
import com.kykarenlin.physiotracker.enums.ListTabs;
import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.ui.eventlogs.filters.FilterKeyword;
import com.kykarenlin.physiotracker.ui.eventlogs.filters.FilterManager;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;

import java.util.Calendar;
import java.util.HashMap;
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
            Bundle bundle = generateBundle(
                    -1,
                    "",
                    cal.getTimeInMillis(),
                    cal.getTimeInMillis(),
                    false,
                    false,
                    false,
                    false,
                    EventImprovementStatus.UNCHANGED.toString(),
                    false,
                    EditMode.CREATE
            );
            Navigation.findNavController(root).navigate(R.id.action_event_to_editEvent, bundle);
        });

        Context context = getContext();
        FragmentActivity fragmentActivity = getActivity();

        RecyclerView rclvEvents = binding.rclvEvents;
        rclvEvents.setLayoutManager(new LinearLayoutManager(context));
        rclvEvents.setHasFixedSize(true);

        EventAdapter eventListAdapter = new EventAdapter();
        rclvEvents.setAdapter(eventListAdapter);

        eventListAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EventWrapped eventWrapped) {
                Event event = eventWrapped.getEvent();
                Bundle bundle = generateBundle(
                        event.getId(),
                        event.getEventDetails(),
                        event.getEventStartTime(),
                        event.getEventEndTime(),
                        event.isExercise(),
                        event.isActivity(),
                        event.isPainOrDiscomfort(),
                        event.isImportant(),
                        event.getImprovementStatus(),
                        event.isArchived(),
                        EditMode.EDIT
                );
                Navigation.findNavController(root).navigate(R.id.action_event_to_editEvent, bundle);
            }
        });

        eventListAdapter.setOnItemLongClickListener(new EventAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(EventWrapped eventWrapped, View itemView) {
                Event event = eventWrapped.getEvent();

                PopupMenu eventPopupMenu = new PopupMenu(context, itemView);
                eventPopupMenu.getMenuInflater().inflate(R.menu.event_popup_menu, eventPopupMenu.getMenu());

                Menu menu = eventPopupMenu.getMenu();

                if (!event.hasStartDate()) {
                    menu.removeItem(R.id.extendEndDate);
                }

                if (event.isImportant()) {
                    menu.removeItem(R.id.markImportantEvent);
                } else {
                    menu.removeItem(R.id.unmarkImportantEvent);
                }

                if (event.isArchived()) {
                    menu.removeItem(R.id.archiveEvent);
                    menu.removeItem(R.id.archivePreviousEvents);
                    menu.removeItem(R.id.archivePreviousEventsWithoutStartTime);
                } else {
                    menu.removeItem(R.id.unarchiveEvent);
                    if (!eventWrapped.getHasPreviousEvents()) {
                        menu.removeItem(R.id.archivePreviousEvents);
                        menu.removeItem(R.id.archivePreviousEventsWithoutStartTime);
                    } else if (event.hasStartDate()) {
                        menu.removeItem(R.id.archivePreviousEventsWithoutStartTime);
                    } else {
                        menu.removeItem(R.id.archivePreviousEvents);
                    }
                }
                eventPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == R.id.markImportantEvent) {
                            event.setImportant(true);
                            eventViewModel.update(event);
                        } else if (id == R.id.unmarkImportantEvent) {
                            event.setImportant(false);
                            eventViewModel.update(event);
                        } else if (id == R.id.deleteEvent) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
                            builder.setTitle("Confirm Deletion").setMessage("Are you sure you want to delete this event?");
                            builder.setPositiveButton(R.string.lbl_confirm, (dialogInterface, i) -> {
                                eventViewModel.delete(event);
                            });
                            builder.setNegativeButton(R.string.lbl_cancel, (dialogInterface, i) -> {});
                            builder.show();
                        } else if (id == R.id.archiveEvent) {
                            event.setArchived(true);
                            eventViewModel.update(event);
                        } else if (id == R.id.unarchiveEvent) {
                            event.setArchived(false);
                            eventViewModel.update(event);
                        } else if (id == R.id.duplicateEvent) {
                            Calendar cal = Calendar.getInstance();
                            Event event = eventWrapped.getEvent();
                            Bundle bundle = generateBundle(
                                    -1,
                                    event.getEventDetails(),
                                    cal.getTimeInMillis(),
                                    cal.getTimeInMillis(),
                                    event.isExercise(),
                                    event.isActivity(),
                                    event.isPainOrDiscomfort(),
                                    event.isImportant(),
                                    event.getImprovementStatus(),
                                    false,
                                    EditMode.DUPLICATE
                            );
                            Navigation.findNavController(root).navigate(R.id.action_event_to_editEvent, bundle);
                        } else if (id == R.id.archivePreviousEvents) {
                            eventViewModel.archiveAllEventsBeforeTimestamp(event);
                        } else if (id == R.id.archivePreviousEventsWithoutStartTime) {
                            eventViewModel.archiveAllEventsWithoutStartDateCreatedBeforeTimestamp(event);
                        } else if (id == R.id.extendEndDate) {
                            if (event.hasStartDate()) {
                                event.setEventEndTime(Calendar.getInstance().getTimeInMillis());
                                eventViewModel.update(event);
                            }
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                eventPopupMenu.show();
            }
        });

        RelativeLayout emptyEventListContainer = binding.emptyEventListContainer;
        ScrollView eventListContainer = binding.eventListContainer;
        TextView emptyEventListMsg = binding.emptyEventListMsg;

        EditText edtFilterKeyword = binding.edtFilterKeyword;
        Chip chpFilterImportance = binding.chpFilterImportance;
        Chip chpFilterPainDiscomfort = binding.chpFilterPainDiscomfort;
        Chip chpFilterExercise = binding.chpFilterExercise;
        Chip chpFilterActivity = binding.chpFilterActivity;

        FilterManager filterManager = new FilterManager(context, fragmentActivity, edtFilterKeyword, chpFilterImportance, chpFilterPainDiscomfort, chpFilterExercise, chpFilterActivity);

        EventListManager eventListManager = new EventListManager(eventViewModel, getViewLifecycleOwner(), eventListAdapter, emptyEventListContainer, emptyEventListMsg, eventListContainer, filterManager);

        edtFilterKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                FilterKeyword filterKeyword = FilterManager.getKeywordFilter();
                filterKeyword.notifyKeywordChanged();
                String keyword = editable.toString().trim();
                filterManager.updateFilter(filterKeyword, !keyword.isEmpty());
                eventListManager.notifyFilterUpdated();
            }
        });

        chpFilterImportance.setOnCheckedChangeListener((compoundButton, checked) -> {
            filterManager.updateFilter(FilterManager.getImportantFilter(), checked);
            eventListManager.notifyFilterUpdated();
        });

        chpFilterPainDiscomfort.setOnCheckedChangeListener((compoundButton, checked) -> {
            filterManager.updateFilter(FilterManager.getPainDiscomfortFilter(), checked);
            eventListManager.notifyFilterUpdated();
        });

        chpFilterExercise.setOnCheckedChangeListener((compoundButton, checked) -> {
            filterManager.updateFilter(FilterManager.getExerciseFilter(), checked);
            eventListManager.notifyFilterUpdated();
        });

        chpFilterActivity.setOnCheckedChangeListener((compoundButton, checked) -> {
            filterManager.updateFilter(FilterManager.getActivityFilter(), checked);
            eventListManager.notifyFilterUpdated();
        });

        final TabLayout tabsEventList = binding.tabsEventList;

        HashMap<ListTabs, Integer> tabsIndices = new HashMap<>();
        tabsIndices.put(ListTabs.ACTIVE, 0);
        tabsIndices.put(ListTabs.ARCHIVED, 1);

        TabLayout.Tab selectedListTab = tabsEventList.getTabAt(tabsIndices.get(ListTabs.ACTIVE));
        if (!EventListManager.isShowingActive()) {
            selectedListTab = tabsEventList.getTabAt(tabsIndices.get(ListTabs.ARCHIVED));
        }
        if (selectedListTab != null) {
            selectedListTab.select();
        }
        tabsEventList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabIndex = tab.getPosition();
                if (selectedTabIndex == tabsIndices.get(ListTabs.ACTIVE)) {
                    eventListManager.setShowingActive(true);
                } else if (selectedTabIndex == tabsIndices.get(ListTabs.ARCHIVED)) {
                    eventListManager.setShowingActive(false);
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

        eventViewModel.getEventsWithStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventListManager.setAllEventsWithStartDate(events);
            }
        });

        eventViewModel.getEventsWithoutStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventListManager.setAllEventsWithoutStartDate(events);
            }
        });

        eventViewModel.getArchivedEventsWithStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventListManager.setAllArchivedEventsWithStartDate(events);
            }
        });

        eventViewModel.getArchivedEventsWithoutStartDate().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventListManager.setAllArchivedEventsWithoutStartDate(events);
            }
        });
        return root;
    }

    private Bundle generateBundle(int id, String details, long startTime, long endTime, boolean isExercise, boolean isActivity, boolean isPain, boolean isImportant, String improvementStatus, boolean isArchived, EditMode editMode) {
        Bundle bundle = new Bundle();
        bundle.putInt(EventBundleKeys.ID.toString(), id);
        bundle.putString(EventBundleKeys.DETAILS.toString(), details);
        bundle.putLong(EventBundleKeys.START_TIME.toString(), startTime);
        bundle.putLong(EventBundleKeys.END_TIME.toString(), endTime);
        bundle.putBoolean(EventBundleKeys.IS_EXERCISE.toString(), isExercise);
        bundle.putBoolean(EventBundleKeys.IS_ACTIVITY.toString(), isActivity);
        bundle.putBoolean(EventBundleKeys.IS_PAIN_DISCOMFORT.toString(), isPain);
        bundle.putBoolean(EventBundleKeys.IS_IMPORTANT.toString(), isImportant);
        bundle.putString(EventBundleKeys.IMPROVEMENT_STATUS.toString(), improvementStatus);
        bundle.putBoolean(EventBundleKeys.IS_ARCHIVED.toString(), isArchived);
        bundle.putString(EventBundleKeys.EDIT_MODE.toString(), editMode.toString());
        return bundle;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}