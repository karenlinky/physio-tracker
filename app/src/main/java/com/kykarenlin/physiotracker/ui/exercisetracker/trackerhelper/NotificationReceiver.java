package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.StopwatchNotificationObserver.STOPWATCH_CHANNEL_ID;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kykarenlin.physiotracker.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(StopwatchNotificationObserver.INTENT_KEY_TITLE);
        String text = intent.getStringExtra(StopwatchNotificationObserver.INTENT_KEY_TEXT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, STOPWATCH_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_run_circle)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {200, 200, 500});

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
