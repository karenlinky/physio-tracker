package com.kykarenlin.physiotracker.ui.settings;

import static com.kykarenlin.physiotracker.MainActivity.NOTIFICATION_PERMISSION_CODE;
import static com.kykarenlin.physiotracker.utils.NotificationIds.notificationExplanation;
import static com.kykarenlin.physiotracker.utils.NotificationIds.notificationPermissions;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.kykarenlin.physiotracker.databinding.FragmentSettingsBinding;
import com.kykarenlin.physiotracker.enums.NotificationType;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerNotifItem;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerNotifItemList;
import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerNotifPreferences;
import com.kykarenlin.physiotracker.utils.PermissionCheck;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Switch switchStopwatchNotification;

    private boolean switchStopwatchNotificationChecked;

    private TrackerNotifPreferences trackerNotifPreferences;

    private ArrayList<EditText> edtWorkoutDelayFields;
    private ArrayList<EditText> edtWorkoutMsgFields;
    private ArrayList<EditText> edtBreakDelayFields;
    private ArrayList<EditText> edtBreakMsgFields;
    private EditText edtWorkoutDelay1;
    private EditText edtWorkoutDelay2;
    private EditText edtWorkoutDelay3;
    private EditText edtWorkoutMsg1;
    private EditText edtWorkoutMsg2;
    private EditText edtWorkoutMsg3;
    private EditText edtBreakDelay1;
    private EditText edtBreakDelay2;
    private EditText edtBreakDelay3;
    private EditText edtBreakMsg1;
    private EditText edtBreakMsg2;
    private EditText edtBreakMsg3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        switchStopwatchNotification = binding.switchStopwatchNotification;

        edtWorkoutDelay1 = binding.edtWorkoutDelay1;
        edtWorkoutDelay2 = binding.edtWorkoutDelay2;
        edtWorkoutDelay3 = binding.edtWorkoutDelay3;
        edtWorkoutDelayFields = new ArrayList<>(Arrays.asList(edtWorkoutDelay1, edtWorkoutDelay2, edtWorkoutDelay3));

        edtWorkoutMsg1 = binding.edtWorkoutMsg1;
        edtWorkoutMsg2 = binding.edtWorkoutMsg2;
        edtWorkoutMsg3 = binding.edtWorkoutMsg3;
        edtWorkoutMsgFields = new ArrayList<>(Arrays.asList(edtWorkoutMsg1, edtWorkoutMsg2, edtWorkoutMsg3));

        edtBreakDelay1 = binding.edtBreakDelay1;
        edtBreakDelay2 = binding.edtBreakDelay2;
        edtBreakDelay3 = binding.edtBreakDelay3;
        edtBreakDelayFields = new ArrayList<>(Arrays.asList(edtBreakDelay1, edtBreakDelay2, edtBreakDelay3));

        edtBreakMsg1 = binding.edtBreakMsg1;
        edtBreakMsg2 = binding.edtBreakMsg2;
        edtBreakMsg3 = binding.edtBreakMsg3;
        edtBreakMsgFields = new ArrayList<>(Arrays.asList(edtBreakMsg1, edtBreakMsg2, edtBreakMsg3));

        trackerNotifPreferences = TrackerNotifPreferences.getInstance(getContext(), getActivity());
        this.updateFields();

        switchStopwatchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    PermissionCheck permissionCheck = PermissionCheck.getInstance(getContext(), getActivity());
                    permissionCheck.getPermissions(
                        notificationPermissions, NOTIFICATION_PERMISSION_CODE,
                        notificationExplanation);
                }
                setNotificationFieldsStatus(b);
                saveSettings();
            }
        });

//        final Button btnSaveSettings = binding.btnSaveSettings;
//        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveSettings();
//            }
//        });

        return root;
    }

    private void updateFields() {
        TrackerNotifItemList workoutNotifList = trackerNotifPreferences.getExerciseNotifications();
        TrackerNotifItemList breakNotifList = trackerNotifPreferences.getBreakNotifications();

        setNotificationFieldsStatus(workoutNotifList.getNotificationOn());
        this.populateFields(workoutNotifList, breakNotifList);
    }
    @Override
    public void onStart() {
        super.onStart();
        this.updateFields();
    }

    private void saveSettings() {
        trackerNotifPreferences.saveStopwachNotificationSettings(
            switchStopwatchNotificationChecked,
            edtWorkoutDelay1.getText().toString(),
            edtWorkoutDelay2.getText().toString(),
            edtWorkoutDelay3.getText().toString(),
            edtWorkoutMsg1.getText().toString(),
            edtWorkoutMsg2.getText().toString(),
            edtWorkoutMsg3.getText().toString(),
            edtBreakDelay1.getText().toString(),
            edtBreakDelay2.getText().toString(),
            edtBreakDelay3.getText().toString(),
            edtBreakMsg1.getText().toString(),
            edtBreakMsg2.getText().toString(),
            edtBreakMsg3.getText().toString()
        );
//        Toast.makeText(getContext(), "Settings successfully saved.", Toast.LENGTH_SHORT).show();
    }

    private void populateFields(ArrayList<TrackerNotifItem> notifs, NotificationType notificationType) {
        for (int i = 0; i < notifs.size(); i++) {
            TrackerNotifItem notif = notifs.get(i);
            int delay = notif.getDelay();
            String msg = notif.getMessage();
            String strDelay = "";
            if (delay >= 0) {
                strDelay = String.valueOf(delay);
            }
            if (notificationType == NotificationType.WORKOUT) {
                edtWorkoutDelayFields.get(i).setText(strDelay);
                edtWorkoutMsgFields.get(i).setText(String.valueOf(msg));
            } else {
                edtBreakDelayFields.get(i).setText(strDelay);
                edtBreakMsgFields.get(i).setText(String.valueOf(msg));
            }
        }
    }

    private void populateFields(TrackerNotifItemList workoutNotifList, TrackerNotifItemList breakNotifList) {
        ArrayList<TrackerNotifItem> workoutNotifs =  workoutNotifList.getList();
        ArrayList<TrackerNotifItem> breakNotifs =  breakNotifList.getList();

        populateFields(workoutNotifs, NotificationType.WORKOUT);
        populateFields(breakNotifs, NotificationType.BREAK);
    }

    private void setNotificationFieldsStatus(boolean value) {
        switchStopwatchNotification.setChecked(value);
        switchStopwatchNotificationChecked = value;
        edtWorkoutDelay1.setEnabled(value);
        edtWorkoutDelay2.setEnabled(value);
        edtWorkoutDelay3.setEnabled(value);
        edtWorkoutMsg1.setEnabled(value);
        edtWorkoutMsg2.setEnabled(value);
        edtWorkoutMsg3.setEnabled(value);
        edtBreakDelay1.setEnabled(value);
        edtBreakDelay2.setEnabled(value);
        edtBreakDelay3.setEnabled(value);
        edtBreakMsg1.setEnabled(value);
        edtBreakMsg2.setEnabled(value);
        edtBreakMsg3.setEnabled(value);
    }

    private void disableNotificationFields() {
        this.setNotificationFieldsStatus(false);
    }

    private void enableNotificationFields() {
        this.setNotificationFieldsStatus(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.saveSettings();
    }
}