package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import static com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.StopwatchNotificationObserver.STOPWATCH_CHANNEL_ID;
import static com.kykarenlin.physiotracker.utils.NotificationIds.NOTIFICATION_STOPWATCH_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kykarenlin.physiotracker.MainActivity;
import com.kykarenlin.physiotracker.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(StopwatchNotificationObserver.INTENT_KEY_TITLE);
        String text = intent.getStringExtra(StopwatchNotificationObserver.INTENT_KEY_TEXT);

        Intent openAppIntent = new Intent(context, MainActivity.class);
        openAppIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingOpenAppIntent = PendingIntent.getActivity(context, 10, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, STOPWATCH_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_run_circle)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {200, 200, 500})
                .setContentIntent(pendingOpenAppIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_STOPWATCH_ID, builder.build());
    }
}
