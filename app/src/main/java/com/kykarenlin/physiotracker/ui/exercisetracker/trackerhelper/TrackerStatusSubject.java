package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.enums.EditMode;
import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;
import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.exercisetracker.ExerciseProgress;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrackerStatusSubject {

    private static final String KEY_TRACKER_STATUS = "tracker_status";
    private static final String KEY_TRACKER_SESSION_PAUSED = "session_paused";
    private static final String KEY_TRACKER_TIMESTAMP = "tracker_timestamp";
    private static final String KEY_TRACKER_ACTIVE_EXERCISE_ID = "tracker_active_exercise_id";

    private static final int DEFAULT_EXERCISE_ID = -1;

    private final String LOG_KEY = "TrackerInitialStatus";

    private Context context;

    private FragmentActivity fragmentActivity;

    private SharedPreferences sharedPref;
    private TrackerStatus status;

    private boolean sessionPaused;

    private long timestamp;

    private int activeExerciseId;
    private int selectedExerciseId;

    private TrackerPlaySoundObserver trackerPlaySoundObserver;

//    private Exercise exercise;  // active exercise
//    private Exercise exerciseSelected;  // usually same as exercise, except on break (exercise is last exercise worked on, selected is the potential next exercise)

    private List<TrackerObserver> trackerObservers = new ArrayList<>();

    private ExerciseViewModel exerciseViewModel;

    LifecycleOwner lifecycleOwner;

    private List<Exercise> exercises = new ArrayList<>();
    public TrackerStatusSubject(Context context, FragmentActivity fragmentActivity, LifecycleOwner lifecycleOwner, ExerciseViewModel exerciseViewModel) {
        this.exerciseViewModel = exerciseViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.trackerPlaySoundObserver = null;

        sharedPref = fragmentActivity.getPreferences(Context.MODE_PRIVATE);

        String strStatus = getData(KEY_TRACKER_STATUS, TrackerStatus.SESSION_NOT_STARTED.toString());
        status = TrackerStatus.valueOf(strStatus);
        sessionPaused = getData(KEY_TRACKER_SESSION_PAUSED, false);
//        timestamp = getData(KEY_TRACKER_TIMESTAMP, SystemClock.elapsedRealtime());
        timestamp = getData(KEY_TRACKER_TIMESTAMP, Calendar.getInstance().getTimeInMillis());
        activeExerciseId = getData(KEY_TRACKER_ACTIVE_EXERCISE_ID, DEFAULT_EXERCISE_ID);
        selectedExerciseId = -1;

        Log.i("TrackerInitialStatus", "status: " + strStatus);
        Log.i(LOG_KEY, "sessionPaused: " + sessionPaused);
        Log.i(LOG_KEY, "timestamp: " + timestamp);
        Log.i(LOG_KEY, "activeExerciseId: " + activeExerciseId);
        Log.i(LOG_KEY, "selectedExerciseId: " + selectedExerciseId);
    }

    public Context getContext() {
        return this.context;
    }

    public FragmentActivity getFragmentActivity() {
        return this.fragmentActivity;
    }

    public void registerObserver(TrackerObserver trackerObserver) {
        if (trackerObserver instanceof TrackerPlaySoundObserver) {
            if (this.trackerPlaySoundObserver == null) {
                this.registerPlaySoundObserver((TrackerPlaySoundObserver) trackerObserver);
            }
            return;
        }
        trackerObservers.add(trackerObserver);
    }

    public void registerPlaySoundObserver(TrackerPlaySoundObserver trackerPlaySoundObserver) {
        this.trackerPlaySoundObserver = trackerPlaySoundObserver;
        trackerObservers.add(trackerPlaySoundObserver);
    }

    public void notifyInitialState() {

        this.notifyStateChanged();
    }

    private void notifyStateChanged() {
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyStateChanged();
        }
    }

    public void notifyOnDestroy() {
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyOnDestroy();
        }
    }

    private String getData(String key, String defaultVal) {
        return sharedPref.getString(key, defaultVal);
    }

    private int getData(String key, int defaultVal) {
        return sharedPref.getInt(key, defaultVal);
    }

    private long getData(String key, long defaultVal) {
        return sharedPref.getLong(key, defaultVal);
    }

    private boolean getData(String key, boolean defaultVal) {
        return sharedPref.getBoolean(key, defaultVal);
    }

    private void setData(String key, String defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, defaultVal);
        editor.apply();

    }

    private void setData(String key, int defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, defaultVal);
        editor.apply();
    }

    private void setData(String key, long defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, defaultVal);
        editor.apply();
    }

    private void setData(String key, boolean defaultVal) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, defaultVal);
        editor.apply();
    }

    private void updateStatus(TrackerStatus status) {
        this.status = status;
        this.setData(KEY_TRACKER_STATUS, status.toString());
    }

    private void updateSessionPaused(boolean sessionPaused) {
        this.sessionPaused = sessionPaused;
        this.setData(KEY_TRACKER_SESSION_PAUSED, sessionPaused);
    }

    private void updateTimestamp(long timestamp) {
        this.timestamp = timestamp;
        this.setData(KEY_TRACKER_TIMESTAMP, timestamp);
    }

    private void updateTimestampToCurrentTime() {
        this.updateTimestamp(Calendar.getInstance().getTimeInMillis());
//        this.updateTimestamp(SystemClock.elapsedRealtime());
    }

    private void updateActiveId(int activeExerciseId) {
        this.activeExerciseId = activeExerciseId;
        this.setData(KEY_TRACKER_ACTIVE_EXERCISE_ID, activeExerciseId);
    }

    public TrackerStatus getStatus() {
        return this.status;
    }

    public boolean getSessionPaused() {
        return this.sessionPaused;
    }

    public long getTimestamp() {return this.timestamp;}

    public void onExerciseProgressClicked(ExerciseProgress exerciseProgress) {
        switch(getStatus()) {
            case WORKOUT_IN_PROGRESS:
            case SESSION_COMPLETED:
                break;
            case SESSION_NOT_STARTED:
            case BREAK:
                if (exerciseProgress.getExercise().getSessionStatus().equals(ExerciseSessionStatus.NOT_COMPLETED.toString())) {
                    selectedExerciseId = exerciseProgress.getExercise().getId();
                }
                break;
        }
        this.notifyStateChanged();
    }

    public void onExerciseProgressLongClicked(ExerciseProgress exerciseProgress, View itemView) {
        switch(getStatus()) {
            case WORKOUT_IN_PROGRESS:
            case SESSION_COMPLETED:
                break;
            case SESSION_NOT_STARTED:
            case BREAK:
                Exercise exercise = exerciseProgress.getExercise();
                PopupMenu trackerExercisePopupMenu = new PopupMenu(getContext(), itemView);
                trackerExercisePopupMenu.getMenuInflater().inflate(R.menu.tracker_exercise_popup_menu, trackerExercisePopupMenu.getMenu());

                Menu menu = trackerExercisePopupMenu.getMenu();

                String status = exercise.getSessionStatus();

                if (status.equals(ExerciseSessionStatus.NOT_COMPLETED.toString())) {
                    menu.removeItem(R.id.markUncompleted);
                } else if (status.equals(ExerciseSessionStatus.COMPLETED.toString())) {
//                    menu.removeItem(R.id.markCompleted);
                    menu.removeItem(R.id.markRemovedFromSession);
                } else if (status.equals(ExerciseSessionStatus.REMOVED_FROM_SESSION.toString())) {
                    menu.removeItem(R.id.markRemovedFromSession);
                }

                trackerExercisePopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        String newStatus = "";
                        if (id == R.id.markUncompleted) {
                            newStatus = ExerciseSessionStatus.NOT_COMPLETED.toString();
//                        } else if (id == R.id.markCompleted) {
//                            newStatus = ExerciseSessionStatus.COMPLETED.toString();
                        } else if (id == R.id.markRemovedFromSession) {
                            newStatus = ExerciseSessionStatus.REMOVED_FROM_SESSION.toString();
                        }
                        if (!newStatus.equals("")) {
                            exercise.setSessionStatus(newStatus);
                            if (selectedExerciseId == exercise.getId()) {
                                selectedExerciseId = DEFAULT_EXERCISE_ID;
                                notifyStateChanged();
                            }
                            exerciseViewModel.update(exercise);
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                trackerExercisePopupMenu.show();
                break;
        }
    }

    public int getNumExercise() {
        return this.exercises.size();
    }

    public void updateExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        // TODO: handle removed exercise
        int numExercise = this.exercises.size();
        if (numExercise == 0) {
            resetSession();
        } else {
            int numUncompleted = 0;
            boolean activeExerciseFound = false; // make sure that activeExercise is not removed
            for (Exercise exercise : exercises) {
                if (exercise.getSessionStatus().equals(ExerciseSessionStatus.NOT_COMPLETED.toString())) {
                    numUncompleted += 1;
                }
                if (activeExerciseId == exercise.getId()) {
                    activeExerciseFound = true;
                }
            }

            if (numUncompleted == 0) {
                // all exercises are completed
                this.finishSession();
            } else if (!activeExerciseFound && this.status == TrackerStatus.WORKOUT_IN_PROGRESS) {
                // exercise in progress is removed
                boolean sessionPaused = this.sessionPaused;
                this.cancelExercise();
                if (sessionPaused) {
                    pauseSession();
                }
            }
        }

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyExercisesChanged(exercises);
        }
        this.notifyStateChanged();
    }

    private Exercise searchForExerciseById(int id) {
        if (id == -1) {
            return null;
        }
        for (Exercise exercise : exercises) {
            if (exercise.getId() == id) {
                return exercise;
            }
        }
        return null;
    }

    public Exercise getActiveExercise() {
        return this.searchForExerciseById(activeExerciseId);
    }

    public Exercise getSelectedExercise() {
        return this.searchForExerciseById(selectedExerciseId);
    }

    public void startExercise() {
        if (getStatus() == TrackerStatus.SESSION_NOT_STARTED) {
            // first exercise of the session
            StopwatchNotificationObserver.notificationPermissionCheck(context, fragmentActivity);
        }
        updateStatus(TrackerStatus.WORKOUT_IN_PROGRESS);
        updateSessionPaused(false);
        updateActiveId(selectedExerciseId);

//        for testing stopwatch resetting session
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        cal.add(Calendar.SECOND, 10);
//        long tempTimestamp = cal.getTimeInMillis();
//        updateTimestamp(tempTimestamp);
        updateTimestampToCurrentTime();

        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyStartExercise();
        }
    }

    public void onPlaySoundClick() {
        if (this.trackerPlaySoundObserver != null) {
            this.trackerPlaySoundObserver.onPlaySoundClick();
        }
    }

    public void startPlayingSound() {
        if (this.sessionPaused && this.getStatus() == TrackerStatus.WORKOUT_IN_PROGRESS) {
            this.continueSession();
        }
    }

    public void cancelExercise() {
        updateStatus(TrackerStatus.BREAK);
        updateSessionPaused(false);
        updateTimestampToCurrentTime();
        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyStartBreak();
        }
    }

    public void finishExercise() {
        Exercise exercise = getActiveExercise();
        if (exercise != null) {
            exercise.setSessionStatus(ExerciseSessionStatus.COMPLETED.toString());
            exerciseViewModel.update(exercise);
        }
        selectedExerciseId = DEFAULT_EXERCISE_ID;
        this.cancelExercise();
    }

    public void continueSession() {
        updateSessionPaused(false);
        // time difference between now and the duration the timer ran (set in pauseSession)
        // so timer will display the duration the timer has been running
//        updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());
        updateTimestamp(Calendar.getInstance().getTimeInMillis() - this.getTimestamp());
        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyContinueSession();
        }
    }

    public void pauseSession() {
        updateSessionPaused(true);
//        updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());   // time difference between now and start time

//        for testing stopwatch resetting session
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        cal.add(Calendar.SECOND, -10);
//        long tempTimestamp = cal.getTimeInMillis();
//        updateTimestamp(Calendar.getInstance().getTimeInMillis() - tempTimestamp);   // time difference between now and start time
        updateTimestamp(Calendar.getInstance().getTimeInMillis() - this.getTimestamp());   // time difference between now and start time


        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyPauseSession();
        }
    }

    public void finishSession() {
        if (this.getStatus() == TrackerStatus.SESSION_COMPLETED) {
            return;
        }
        updateStatus(TrackerStatus.SESSION_COMPLETED);
//        if (!this.getSessionPaused()) {
//            updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());
//            updateTimestamp(Calendar.getInstance().getTimeInMillis() - this.getTimestamp());
//        }
        updateTimestampToCurrentTime();
        updateSessionPaused(false);
        selectedExerciseId = -1;
        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyFinishSession();
        }
    }

    public void resetSession() {
        updateSessionPaused(false);
        updateStatus(TrackerStatus.SESSION_NOT_STARTED);
        updateTimestampToCurrentTime();
        selectedExerciseId = -1;
        updateActiveId(-1);
        exerciseViewModel.setAllExercisesAsNotCompleted();
        notifyStateChanged();

        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyResetSession();
        }
    }
}
