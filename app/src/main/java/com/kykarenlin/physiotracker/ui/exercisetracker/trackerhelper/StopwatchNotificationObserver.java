package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static com.kykarenlin.physiotracker.MainActivity.EXACT_ALARM_PERMISSION_CODE;
import static com.kykarenlin.physiotracker.MainActivity.NOTIFICATION_PERMISSION_CODE;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.utils.PermissionCheck;

import java.util.ArrayList;
import java.util.Calendar;

public class StopwatchNotificationObserver extends TrackerObserver {

    private TrackerStatusSubject trackerStatusSubject;

    private Context context;

    public static final String INTENT_KEY_TITLE = "intent_title";
    public static final String INTENT_KEY_TEXT = "intent_text";
    private static final String NOTIF_WORKOUT_TITLE = "Workout In Progress";
    private static final String NOTIF_BREAK_TITLE = "Break Is Over";

    public static final String STOPWATCH_CHANNEL_ID = "tracker_stopwatch_channel";

    private TrackerNotifPreferences trackerNotifPreferences;

    private FragmentActivity fragmentActivity;

    public StopwatchNotificationObserver (TrackerStatusSubject trackerStatusSubject) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.context = trackerStatusSubject.getContext();
        this.trackerNotifPreferences = TrackerNotifPreferences.getInstance(this.context, trackerStatusSubject.getFragmentActivity());
        this.fragmentActivity = trackerStatusSubject.getFragmentActivity();
    }
    @Override
    public void notifyStateChanged() {}



    private void notificationPermissionCheck() {
        PermissionCheck permissionCheck = PermissionCheck.getInstance(context, fragmentActivity);
        permissionCheck.getPermissions(
                new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.VIBRATE}
                , NOTIFICATION_PERMISSION_CODE,
                "Permission required to schedule tracker notification.");
    }

    private void scheduleNotif(long launchTime, String title, String msg, int notifId) {
        Intent notificationIntent = new Intent(this.context, NotificationReceiver.class);
        notificationIntent.putExtra(INTENT_KEY_TITLE, title);
        notificationIntent.putExtra(INTENT_KEY_TEXT, msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, launchTime, pendingIntent);
    }

    @Override
    public void notifyStartExercise() {
        TrackerNotifItemList trackerNotifItemList = this.trackerNotifPreferences.getExerciseNotifications();
        if (trackerNotifItemList.getNumItems() == 0) {
            return; // no notification needs to be scheduled
        }

        this.notificationPermissionCheck();

        ArrayList<TrackerNotifItem> notifList = trackerNotifItemList.getList();
        int counter = 0;
        for (TrackerNotifItem notifItem : notifList) {
            Log.e("TAG", "notifyStartExercise: notif 1 scheduled after " + notifItem.getMinute() + "min");
            counter += 1;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, notifItem.getMinute());
            this.scheduleNotif(cal.getTimeInMillis(), NOTIF_WORKOUT_TITLE, notifItem.getMessage(), counter);
        }
    }

    @Override
    public void notifyStartBreak() {

    }

    @Override
    public void notifyContinueSession() {

    }

    @Override
    public void notifySessionPaused() {

    }
}
