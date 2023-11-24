package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

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
        selectedExerciseId = activeExerciseId;

        Log.e("TAG", "status: " + strStatus);
        Log.e("TAG", "sessionPaused: " + sessionPaused);
        Log.e("TAG", "timestamp: " + timestamp);
        Log.e("TAG", "activeExerciseId: " + activeExerciseId);
        Log.e("TAG", "selectedExerciseId: " + selectedExerciseId);
    }

    public void registerObserver(TrackerObserver trackerObserver) {
        trackerObservers.add(trackerObserver);
        this.notifyInitialState();
    }

    private void notifyInitialState() {
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyInitialState();
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

    public TrackerStatus getStatus() {
        return this.status;
    }

    public boolean getSessionPaused() {
        return this.sessionPaused;
    }

    public void onExerciseProgressClicked(ExerciseProgress exerciseProgress) {

    }

    public void updateExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        for (TrackerObserver trackerObserver : trackerObservers) {
            trackerObserver.notifyExercisesChanged(exercises);
        }
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
        return this.searchForExerciseById(activeExerciseId);
    }
}
