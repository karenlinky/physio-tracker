package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;
import com.kykarenlin.physiotracker.enums.TrackerStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.ui.exercisetracker.ExerciseProgress;
import com.kykarenlin.physiotracker.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrackerStatusSubject {

    private static final String KEY_TRACKER_STATUS = "tracker_status";
    private static final String KEY_TRACKER_SESSION_PAUSED = "session_paused";
    private static final String KEY_TRACKER_TIMESTAMP = "tracker_timestamp";
    private static final String KEY_TRACKER_ACTIVE_EXERCISE_ID = "tracker_active_exercise_id";

    private static final int DEFAULT_EXERCISE_ID = -1;

    private SharedPreferences sharedPref;
    private TrackerStatus status;

    private boolean sessionPaused;

    private long timestamp;

    private int activeExerciseId;
    private int selectedExerciseId;

//    private Exercise exercise;  // active exercise
//    private Exercise exerciseSelected;  // usually same as exercise, except on break (exercise is last exercise worked on, selected is the potential next exercise)

    private List<TrackerObserver> trackerObservers = new ArrayList<>();

    private ExerciseViewModel exerciseViewModel;

    LifecycleOwner lifecycleOwner;

    private List<Exercise> exercises = new ArrayList<>();
    public TrackerStatusSubject(FragmentActivity fragmentActivity, LifecycleOwner lifecycleOwner, ExerciseViewModel exerciseViewModel) {
        this.exerciseViewModel = exerciseViewModel;
        this.lifecycleOwner = lifecycleOwner;

        sharedPref = fragmentActivity.getPreferences(Context.MODE_PRIVATE);

        String strStatus = getData(KEY_TRACKER_STATUS, TrackerStatus.SESSION_NOT_STARTED.toString());
        status = TrackerStatus.valueOf(strStatus);
        sessionPaused = getData(KEY_TRACKER_SESSION_PAUSED, false);
        timestamp = getData(KEY_TRACKER_TIMESTAMP, SystemClock.elapsedRealtime());
        activeExerciseId = getData(KEY_TRACKER_ACTIVE_EXERCISE_ID, DEFAULT_EXERCISE_ID);
        selectedExerciseId = -1;

        Log.i("TAG", "status: " + strStatus);
        Log.i("TAG", "sessionPaused: " + sessionPaused);
        Log.i("TAG", "timestamp: " + timestamp);
        Log.i("TAG", "activeExerciseId: " + activeExerciseId);
        Log.i("TAG", "selectedExerciseId: " + selectedExerciseId);
    }

    public void registerObserver(TrackerObserver trackerObserver) {
        trackerObservers.add(trackerObserver);
    }

    public void notifyInitialState() {

        this.notifyStateChanged();
    }

    private void notifyStateChanged() {
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyStateChanged();
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
        this.updateTimestamp(SystemClock.elapsedRealtime());
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

    public void updateExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyExercisesChanged(exercises);
        }
        // TODO: handle removed exercise
        this.notifyStateChanged();
    }

    private Exercise searchForExerciseById(int id) {
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
        updateStatus(TrackerStatus.WORKOUT_IN_PROGRESS);
        updateSessionPaused(false);
        updateActiveId(selectedExerciseId);
        updateTimestampToCurrentTime();
        notifyStateChanged();
    }

    public void cancelExercise() {
        updateStatus(TrackerStatus.BREAK);
        updateSessionPaused(false);
        updateTimestampToCurrentTime();
        notifyStateChanged();
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
        updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());
        notifyStateChanged();
    }

    public void pauseSession() {
        updateSessionPaused(true);
        updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());   // time difference between now and start time
        notifyStateChanged();
    }

    public void finishSession() {
        updateStatus(TrackerStatus.SESSION_COMPLETED);
        if (!this.getSessionPaused()) {
            updateTimestamp(SystemClock.elapsedRealtime() - this.getTimestamp());
        }
        updateSessionPaused(false);
        selectedExerciseId = -1;
        notifyStateChanged();
    }

    public void resetSession() {
        updateSessionPaused(false);
        updateStatus(TrackerStatus.SESSION_NOT_STARTED);
        updateTimestampToCurrentTime();
        selectedExerciseId = -1;
        updateActiveId(-1);
        exerciseViewModel.setAllExercisesAsNotCompleted();
        notifyStateChanged();
    }
}
