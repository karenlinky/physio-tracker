package com.kykarenlin.physiotracker.utils;

import android.Manifest;

public class NotificationIds {
    public static final int NOTIFICATION_STOPWATCH_ID = 1;

    public static final String[] notificationPermissions = new String[]{
        Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.VIBRATE};

    public static final String notificationExplanation = "Permission required to schedule tracker notification.";
}
