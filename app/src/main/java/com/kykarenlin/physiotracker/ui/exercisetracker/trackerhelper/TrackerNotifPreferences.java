package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.utils.SharedPref;

public class TrackerNotifPreferences {

    private Context context;

    private SharedPref sharedPref;

    private static final String KEY_EXERCISE_NOTIF_ON = "exercise_notif_on";
    private static final String KEY_EXERCISE_NOTIF_DELAY_1 = "exercise_notif_delay_1";
    private static final String KEY_EXERCISE_NOTIF_MSG_1 = "exercise_notif_msg_1";
    private static final String KEY_EXERCISE_NOTIF_DELAY_2 = "exercise_notif_delay_2";
    private static final String KEY_EXERCISE_NOTIF_MSG_2 = "exercise_notif_msg_2";
    private static final String KEY_EXERCISE_NOTIF_DELAY_3 = "exercise_notif_delay_3";
    private static final String KEY_EXERCISE_NOTIF_MSG_3 = "exercise_notif_msg_3";

    private static final String KEY_BREAK_NOTIF_ON = "break_notif_on";
    private static final String KEY_BREAK_NOTIF_DELAY_1 = "break_notif_delay_1";
    private static final String KEY_BREAK_NOTIF_MSG_1 = "break_notif_msg_1";
    private static final String KEY_BREAK_NOTIF_DELAY_2 = "break_notif_delay_2";
    private static final String KEY_BREAK_NOTIF_MSG_2 = "break_notif_msg_2";
    private static final String KEY_BREAK_NOTIF_DELAY_3 = "break_notif_delay_3";
    private static final String KEY_BREAK_NOTIF_MSG_3 = "break_notif_msg_3";


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
        boolean notificationTurnedOn = this.sharedPref.getData(KEY_EXERCISE_NOTIF_ON, true);
        TrackerNotifItemList trackerNotifItemList = new TrackerNotifItemList();
        if (!notificationTurnedOn) {
            return trackerNotifItemList;
        }

        int delay = 0;
        String msg = "";
        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_1, 1);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_1, "🤔 It's been awhile. I think you should be done by now.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_2, 2);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_2, "🤔 I hope you are not slacking.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_EXERCISE_NOTIF_DELAY_3, 3);
        msg = this.sharedPref.getData(KEY_EXERCISE_NOTIF_MSG_3, "😒 I am a bit disappointed.");
        trackerNotifItemList.addItem(delay, msg);

        return trackerNotifItemList;
    }

    public TrackerNotifItemList getBreakNotifications() {
        //TODO: replace delay
        boolean notificationTurnedOn = this.sharedPref.getData(KEY_BREAK_NOTIF_ON, true);
        TrackerNotifItemList trackerNotifItemList = new TrackerNotifItemList();
        if (!notificationTurnedOn) {
            return trackerNotifItemList;
        }

        int delay = 0;
        String msg = "";
        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_1, 1);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_1, "⏲️ Time's up! Break is over.");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_2, 2);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_2, "💪 Get back to your exercise!");
        trackerNotifItemList.addItem(delay, msg);

        delay = this.sharedPref.getData(KEY_BREAK_NOTIF_DELAY_3, 3);
        msg = this.sharedPref.getData(KEY_BREAK_NOTIF_MSG_3, "😶 You are a failure. I am speechless.");
        trackerNotifItemList.addItem(delay, msg);

        return trackerNotifItemList;
    }

}
