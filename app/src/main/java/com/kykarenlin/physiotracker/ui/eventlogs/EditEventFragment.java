package com.kykarenlin.physiotracker.ui.eventlogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.databinding.FragmentEditEventBinding;
import com.kykarenlin.physiotracker.enums.EventBundleKeys;
import com.kykarenlin.physiotracker.enums.EventImprovementStatus;
import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.utils.DateTimeHelper;
import com.kykarenlin.physiotracker.viewmodel.EventViewModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEventFragment extends Fragment {

    private FragmentEditEventBinding binding;

    private EventViewModel eventViewModel;
    private FragmentActivity fragmentActivity;

    private final String NO_START_DATE = "Not selected";
    private final String NO_END_DATE = "Not selected";

    private EditText edtEventDetails;
    private TextView txtSelectedStartDate;
    private TextView invisibleStartDate;
    private ImageButton btnSelectStartDate;
    private TextView txtSelectedEndDate;
    private TextView invisibleEndDate;
    private ImageButton btnSelectEndDate;
    private CheckBox chkbxIsActivity;
    private CheckBox chkbxIsPainDiscomfort;
    private RadioGroup rgChanges;
    public static EditEventFragment newInstance() { return new EditEventFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding = FragmentEditEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentActivity = getActivity();

        int eventId = getArguments().getInt(EventBundleKeys.ID.toString());
        String initDetails = getArguments().getString(EventBundleKeys.DETAILS.toString());
        long initStartTime = getArguments().getLong(EventBundleKeys.START_TIME.toString());
        long initEndTime = getArguments().getLong(EventBundleKeys.END_TIME.toString());
        boolean isActivity = getArguments().getBoolean(EventBundleKeys.IS_ACTIVITY.toString());
        boolean isPain = getArguments().getBoolean(EventBundleKeys.IS_PAIN_DISCOMFORT.toString());
        String improvementStatus = getArguments().getString(EventBundleKeys.IMPROVEMENT_STATUS.toString());
        boolean isArchived = getArguments().getBoolean(EventBundleKeys.IS_ARCHIVED.toString());

        edtEventDetails = binding.edtEventDetails;
        txtSelectedStartDate = binding.txtSelectedStartDate;
        invisibleStartDate = binding.invisibleStartDate;
        btnSelectStartDate = binding.btnSelectStartDate;
        txtSelectedEndDate = binding.txtSelectedEndDate;
        invisibleEndDate = binding.invisibleEndDate;
        btnSelectEndDate = binding.btnSelectEndDate;
        chkbxIsActivity = binding.chkbxIsActivity;
        chkbxIsPainDiscomfort = binding.chkbxIsPainDiscomfort;
        rgChanges = binding.rgChanges;

        edtEventDetails.setText(initDetails);
        updateStartTime(initStartTime);
        updateEndTime(initEndTime);
        chkbxIsActivity.setChecked(isActivity);
        chkbxIsPainDiscomfort.setChecked(isPain);

        if (improvementStatus.equals(EventImprovementStatus.UNCHANGED.toString())) {
            rgChanges.check(R.id.rbUnchanged);
        } else if (improvementStatus.equals(EventImprovementStatus.IMPROVED.toString())) {
            rgChanges.check(R.id.rbImproved);
        } else {
            rgChanges.check(R.id.rbWorsened);
        }

        btnSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = invisibleStartDate != null ? invisibleStartDate.getText().toString() : "";
                if (strDate.equals("")) {
                    return;
                }
                openDatePicker(Long.parseLong(strDate), txtSelectedStartDate, invisibleStartDate, NO_START_DATE);
            }
        });

        btnSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = invisibleEndDate != null ? invisibleEndDate.getText().toString() : "";
                if (strDate.equals("")) {
                    return;
                }
                openDatePicker(Long.parseLong(strDate), txtSelectedEndDate, invisibleEndDate, NO_END_DATE);
            }
        });

        final Button btnSave = binding.btnSave;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String details = edtEventDetails.getText().toString().trim();
                String strStartTime = invisibleStartDate.getText().toString().trim();
                String strEndTime = invisibleEndDate.getText().toString().trim();
                boolean isActivity = chkbxIsActivity.isChecked();
                boolean isPain = chkbxIsPainDiscomfort.isChecked();
                int selectedChangesId = rgChanges.getCheckedRadioButtonId();
                String improvementStatus = EventImprovementStatus.UNCHANGED.toString();
                if (selectedChangesId == R.id.rbImproved) {
                    improvementStatus = EventImprovementStatus.IMPROVED.toString();
                } else if (selectedChangesId == R.id.rbWorsened) {
                    improvementStatus = EventImprovementStatus.WORSENED.toString();
                }

                Event newEvent = new Event(
                    details,
                    Long.parseLong(strStartTime),
                    Long.parseLong(strEndTime),
                    isActivity,
                    isPain,
                    improvementStatus
                );
                if (eventId == -1) {
                    eventViewModel.insert(newEvent);
                    Toast.makeText(getContext(), "New event created", Toast.LENGTH_SHORT).show();
                } else {
                    newEvent.setId(eventId);
                    newEvent.setArchived(isArchived);
                    eventViewModel.update(newEvent);
                    Toast.makeText(getContext(), "Event updated", Toast.LENGTH_SHORT).show();
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }

    private void updateTime(long dateTime, TextView visibleDate, TextView invisibleDate, String emptyMsg) {
        String strDateTime = DateTimeHelper.toStringDateWithTime(dateTime);
        if (strDateTime.equals(DateTimeHelper.INVALID_DATE_RESULT)) {
            visibleDate.setText(emptyMsg);
            invisibleDate.setText("0");
        } else {
            visibleDate.setText(strDateTime);
            invisibleDate.setText(String.valueOf(dateTime));
        }
    }

    private void updateStartTime(long dateTime) {
        this.updateTime(dateTime, txtSelectedStartDate, invisibleStartDate, NO_START_DATE);
    }

    private void updateEndTime(long dateTime) {
        this.updateTime(dateTime, txtSelectedEndDate, invisibleEndDate, NO_END_DATE);
    }

    private void openDatePicker(long initialValue, TextView visibleDate, TextView invisibleDate, String emptyMsg) {
        Calendar calendar = Calendar.getInstance();
        if (initialValue != 0) {
            calendar.setTimeInMillis(initialValue);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                        long timeInMillis = DateTimeHelper.getTimeInMillis(year, month, day, hours, minutes);
                        updateTime(timeInMillis, visibleDate, invisibleDate, emptyMsg);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        updateTime(0, visibleDate, invisibleDate, emptyMsg);
                    }
                });
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                updateTime(0, visibleDate, invisibleDate, emptyMsg);
            }
        });

        datePickerDialog.show();
    }
}