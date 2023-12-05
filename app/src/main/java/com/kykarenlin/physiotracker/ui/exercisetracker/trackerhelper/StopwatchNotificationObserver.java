package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static com.kykarenlin.physiotracker.MainActivity.NOTIFICATION_PERMISSION_CODE;
import static com.kykarenlin.physiotracker.utils.NotificationIds.NOTIFICATION_STOPWATCH_ID;
import static com.kykarenlin.physiotracker.utils.NotificationIds.notificationExplanation;
import static com.kykarenlin.physiotracker.utils.NotificationIds.notificationPermissions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.utils.PermissionCheck;

import java.util.ArrayList;
import java.util.Calendar;

public class StopwatchNotificationObserver extends TrackerObserver {

    private TrackerStatusSubject trackerStatusSubject;

    private Context context;

    public static final String INTENT_KEY_TITLE = "intent_title";
    public static final String INTENT_KEY_TEXT = "intent_text";
    public static final String INTENT_KEY_SESSION_MODE_WORKING_OUT = "intent_session_mode";
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

    public static void notificationPermissionCheck(Context context, FragmentActivity fragmentActivity) {
        PermissionCheck permissionCheck = PermissionCheck.getInstance(context, fragmentActivity);
        permissionCheck.getPermissions(
                notificationPermissions, NOTIFICATION_PERMISSION_CODE,
                notificationExplanation);
    }

    private void notificationPermissionCheck() {
        notificationPermissionCheck(context, fragmentActivity);
    }

    private void scheduleNotif(long launchTime, String notifTitle, String msg, int notifId, boolean workingOut) {
        Intent notificationIntent = new Intent(this.context, NotificationReceiver.class);
        notificationIntent.putExtra(INTENT_KEY_TITLE, notifTitle);
        notificationIntent.putExtra(INTENT_KEY_TEXT, msg);
        notificationIntent.putExtra(INTENT_KEY_SESSION_MODE_WORKING_OUT, workingOut);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, launchTime, pendingIntent);
    }

    private void scheduleNotif(TrackerNotifItemList trackerNotifItemList, String notifTitle, long offset, boolean workingOut) {
        ArrayList<TrackerNotifItem> notifList = trackerNotifItemList.getValidList();
        if (!trackerNotifItemList.getNotificationOn() || notifList.size() == 0) {
            return; // no notification needs to be scheduled
        }

//        this.notificationPermissionCheck(); // removed to avoid asking for permission everytime; asking only when the first exercise start

        int counter = 0;
        for (TrackerNotifItem notifItem : notifList) {
            counter += 1;
            Calendar cal = Calendar.getInstance();
            long currentTime = cal.getTimeInMillis();
            cal.add(Calendar.MINUTE, notifItem.getDelay());
            long scheduledTime = cal.getTimeInMillis() - offset;
            if (scheduledTime < currentTime) {    // scheduled time is in the past
                continue;
            }
            this.scheduleNotif(scheduledTime, notifTitle, notifItem.getMessage(), counter, workingOut);
        }
    }

    private void removeNotifications() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFICATION_STOPWATCH_ID);
    }

    public static void cancelScheduledNotifications(Context context, int notifId) {
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                notifId,
                notificationIntent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE // Use FLAG_NO_CREATE to get existing PendingIntent or null if it doesn't exist
        );

        if (pendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static void cancelScheduledNotifications(Context context) {
        cancelScheduledNotifications(context, 1);
        cancelScheduledNotifications(context, 2);
        cancelScheduledNotifications(context, 3);
    }

    private void cancelScheduledNotifications(int notifId) {
        cancelScheduledNotifications(context, notifId);
//        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context,
//                notifId,
//                notificationIntent,
//                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE // Use FLAG_NO_CREATE to get existing PendingIntent or null if it doesn't exist
//        );
//
//        if (pendingIntent != null) {
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.cancel(pendingIntent);
//            pendingIntent.cancel();
//        }
    }

    private void cancelScheduledNotifications() {
        this.cancelScheduledNotifications(1);
        this.cancelScheduledNotifications(2);
        this.cancelScheduledNotifications(3);
    }

    @Override
    public void notifyStartExercise() {
//        this.removeNotifications();
        this.cancelScheduledNotifications();
        this.scheduleNotif(this.trackerNotifPreferences.getExerciseNotifications(), NOTIF_WORKOUT_TITLE, 0, true);
    }

    @Override
    public void notifyStartBreak() {
//        this.removeNotifications();
        this.cancelScheduledNotifications();
        this.scheduleNotif(this.trackerNotifPreferences.getBreakNotifications(), NOTIF_BREAK_TITLE, 0, false);
    }

    @Override
    public void notifyContinueSession() {
        long offset = Calendar.getInstance().getTimeInMillis() - this.trackerStatusSubject.getTimestamp(); // duration the stopwatch has been running
        if (this.trackerStatusSubject.getStatus() == TrackerStatus.WORKOUT_IN_PROGRESS) {
            this.scheduleNotif(this.trackerNotifPreferences.getExerciseNotifications(), NOTIF_WORKOUT_TITLE, offset, true);
        } else {
            this.scheduleNotif(this.trackerNotifPreferences.getBreakNotifications(), NOTIF_BREAK_TITLE, offset, false);
        }
    }

    @Override
    public void notifyPauseSession() {
        this.cancelScheduledNotifications();
    }

    @Override
    public void notifyFinishSession() {
//        this.removeNotifications();
        this.cancelScheduledNotifications();
    }

    @Override
    public void notifyResetSession() {
//        this.removeNotifications();
        this.cancelScheduledNotifications();
    }
}
