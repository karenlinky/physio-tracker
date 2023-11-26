package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.enums.NotificationType;
import com.kykarenlin.physiotracker.utils.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;

public class TrackerNotifPreferences {

    private Context context;

    private SharedPref sharedPref;

    private static final String KEY_STOPWATCH_NOTIF_ON = "stopwatch_notif_on";

    private static final String KEY_EXERCISE_NOTIF_DELAY_1 = "exercise_notif_delay_1";
    private static final String KEY_EXERCISE_NOTIF_MSG_1 = "exercise_notif_msg_1";
    private static final String KEY_EXERCISE_NOTIF_DELAY_2 = "exercise_notif_delay_2";
    private static final String KEY_EXERCISE_NOTIF_MSG_2 = "exercise_notif_msg_2";
    private static final String KEY_EXERCISE_NOTIF_DELAY_3 = "exercise_notif_delay_3";
    private static final String KEY_EXERCISE_NOTIF_MSG_3 = "exercise_notif_msg_3";

    private static final ArrayList<String> KEY_EXERCISE_DELAY = new ArrayList<>(Arrays.asList(KEY_EXERCISE_NOTIF_DELAY_1, KEY_EXERCISE_NOTIF_DELAY_2, KEY_EXERCISE_NOTIF_DELAY_3));
    private static final ArrayList<String> KEY_EXERCISE_MSG = new ArrayList<>(Arrays.asList(KEY_EXERCISE_NOTIF_MSG_1, KEY_EXERCISE_NOTIF_MSG_2, KEY_EXERCISE_NOTIF_MSG_3));


    private static final String KEY_BREAK_NOTIF_DELAY_1 = "break_notif_delay_1";
    private static final String KEY_BREAK_NOTIF_MSG_1 = "break_notif_msg_1";
    private static final String KEY_BREAK_NOTIF_DELAY_2 = "break_notif_delay_2";
    private static final String KEY_BREAK_NOTIF_MSG_2 = "break_notif_msg_2";
    private static final String KEY_BREAK_NOTIF_DELAY_3 = "break_notif_delay_3";
    private static final String KEY_BREAK_NOTIF_MSG_3 = "break_notif_msg_3";

    private static final ArrayList<String> KEY_BREAK_DELAY = new ArrayList<>(Arrays.asList(KEY_BREAK_NOTIF_DELAY_1, KEY_BREAK_NOTIF_DELAY_2, KEY_BREAK_NOTIF_DELAY_3));
    private static final ArrayList<String> KEY_BREAK_MSG = new ArrayList<>(Arrays.asList(KEY_BREAK_NOTIF_MSG_1, KEY_BREAK_NOTIF_MSG_2, KEY_BREAK_NOTIF_MSG_3));


    private static TrackerNotifPreferences instance = null;
    private TrackerNotifPreferences(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.sharedPref = new SharedPref(fragmentActivity.getPreferences(Context.MODE_PRIVATE));
    }

    public synchronized static TrackerNotifPreferences getInstance(Context context, FragmentActivity fragmentActivity) {
        if (instance == null) {
            instance = new TrackerNotifPreferences(context, fragmentActivity);
        }
        return instance;
    }

    public TrackerNotifItemList getExerciseNotifications() {
        //TODO: replace delay
        boolean notificationTurnedOn = this.sharedPref.getData(KEY_STOPWATCH_NOTIF_ON, true);
        TrackerNotifItemList trackerNotifItemList = new TrackerNotifItemList(notificationTurnedOn);

        int delay = 0;
        String msg = "";
        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_1, 8);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_1, "🤔 It's been awhile. I think you should be done by now.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_2, 10);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_2, "🤔 I hope you are not slacking.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_3, 12);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_3, "😒 I am a bit disappointed.");
        trackerNotifItemList.addItem(delay, msg);

        return trackerNotifItemList;
    }

    public TrackerNotifItemList getBreakNotifications() {
        //TODO: replace delay
        boolean notificationTurnedOn = this.sharedPref.getData(KEY_STOPWATCH_NOTIF_ON, true);
        TrackerNotifItemList trackerNotifItemList = new TrackerNotifItemList(notificationTurnedOn);

        int delay = 0;
        String msg = "";
        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_1, 3);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_1, "⏲️ Time's up! Break is over.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_2, 5);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_2, "💪 Get back to your exercise!");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_3, 8);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_3, "😶 You are a failure. I am speechless.");
        trackerNotifItemList.addItem(delay, msg);

        return trackerNotifItemList;
    }

    private TrackerNotifItemList generateTrackerNotifItemList(boolean notifOn, String delay1, String delay2, String delay3, String msg1, String msg2, String msg3) {
        TrackerNotifItemList trackerNotifItemList = new TrackerNotifItemList(notifOn);
        trackerNotifItemList.addItem(delay1, msg1);
        trackerNotifItemList.addItem(delay2, msg2);
        trackerNotifItemList.addItem(delay3, msg3);
        return trackerNotifItemList;
    }

    private void saveToSharedPreferences(TrackerNotifItemList trackerNotifItemList, NotificationType notificationType) {
        ArrayList<TrackerNotifItem> trackerNotiflist = trackerNotifItemList.getList();

        for (int i = 0; i < trackerNotiflist.size(); i++) {
            TrackerNotifItem trackerNotifItem = trackerNotiflist.get(i);
            if (notificationType == NotificationType.WORKOUT) {
                this.sharedPref.setData(KEY_EXERCISE_DELAY.get(i), trackerNotifItem.getDelay());
                this.sharedPref.setData(KEY_EXERCISE_MSG.get(i), trackerNotifItem.getMessage());
            } else {
                this.sharedPref.setData(KEY_BREAK_DELAY.get(i), trackerNotifItem.getDelay());
                this.sharedPref.setData(KEY_BREAK_MSG.get(i), trackerNotifItem.getMessage());
            }
        }
    }

    public void saveStopwachNotificationSettings(
        boolean notifOn,
        String strWorkoutDelay1,
        String strWorkoutDelay2,
        String strWorkoutDelay3,
        String strWorkoutMsg1,
        String strWorkoutMsg2,
        String strWorkoutMsg3,
        String strBreakDelay1,
        String strBreakDelay2,
        String strBreakDelay3,
        String strBreakMsg1,
        String strBreakMsg2,
        String strBreakMsg3
    ) {
        TrackerNotifItemList workoutTrackerNotifItemList = generateTrackerNotifItemList(
            notifOn,
            strWorkoutDelay1,
            strWorkoutDelay2,
            strWorkoutDelay3,
            strWorkoutMsg1,
            strWorkoutMsg2,
            strWorkoutMsg3
        );
        TrackerNotifItemList breakTrackerNotifItemList = generateTrackerNotifItemList(
                notifOn,
                strBreakDelay1,
                strBreakDelay2,
                strBreakDelay3,
                strBreakMsg1,
                strBreakMsg2,
                strBreakMsg3
        );
        this.saveToSharedPreferences(workoutTrackerNotifItemList, NotificationType.WORKOUT);
        this.saveToSharedPreferences(breakTrackerNotifItemList, NotificationType.BREAK);
    }

}
