package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.List;

public abstract class TrackerObserver {
    public abstract void notifyStateChanged();

    public void notifyExercisesChanged(List<Exercise> exercises) {};

    public void notifyStartExercise() {}

    public void notifyStartBreak() {}

    public void notifyContinueSession() {}

    public void notifyPauseSession() {}

    public void notifyFinishSession() {}

    public void notifyResetSession() {}

    public void notifyOnDestroy() {}
}
