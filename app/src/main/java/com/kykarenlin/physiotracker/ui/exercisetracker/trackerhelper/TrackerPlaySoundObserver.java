package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrackerPlaySoundObserver extends TrackerObserver {
    private static TrackerPlaySoundObserver instance = null;

    private TrackerStatusSubject trackerStatusSubject;

    private Button btnPlaySound;

    private boolean isPlaying;

    private SoundPool soundPool;
    private int soundId;
    private ScheduledExecutorService executor;
    private ScheduledExecutorService repExecutor;

    private Exercise lastPlayedExercise;

    private final int DELAY_IN_SECOND = 3;
    private final int INTERVAL_IN_SECOND = 3;
    private TrackerPlaySoundObserver(TrackerStatusSubject trackerStatusSubject, Button btnPlaySound) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.btnPlaySound = btnPlaySound;
        this.lastPlayedExercise = null;

        updateIsNotPlaying();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        soundId = soundPool.load(trackerStatusSubject.getContext(), R.raw.beep_low, 1);
    }

    public static synchronized TrackerPlaySoundObserver getInstance(TrackerStatusSubject trackerStatusSubject, Button btnPlaySound) {
        if (instance == null) {
            instance = new TrackerPlaySoundObserver(trackerStatusSubject, btnPlaySound);
        }
        return instance;
    }

    private void updateIsPlaying() {
        this.isPlaying = true;
        btnPlaySound.setText("Stop 🔇");
    }

    private void updateIsNotPlaying() {
        this.isPlaying = false;
        this.lastPlayedExercise = null;
        btnPlaySound.setText("Play 🔊");
    }

    private void startSound() {
        if (soundPool != null) {
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    private void shutDownExecutors() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        if (repExecutor != null && !repExecutor.isShutdown()) {
            repExecutor.shutdownNow();
        }
    }

    private void playOneRepSound(int durationInSecond) {
        repExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Play the beep sound
                startSound();
            }
        }, 0, 1, TimeUnit.SECONDS);
        repExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                if (repExecutor != null && !repExecutor.isShutdown()) {
                    repExecutor.shutdownNow();
                }
            }
        }, durationInSecond, TimeUnit.SECONDS);
    }

    private void playSound() {
        this.shutDownExecutors();
        executor = Executors.newSingleThreadScheduledExecutor();
        Exercise activeExercise = this.trackerStatusSubject.getActiveExercise();
        this.lastPlayedExercise = activeExercise;
        if (activeExercise == null) {
            return;
        }

        int numRep = activeExercise.getNumReps();
        int duration = activeExercise.getRepDuration();
        String durationUnit = activeExercise.getRepDurationUnit();

        if (duration == 0 || numRep == 0) {
            return;
        }
        
        Toast.makeText(trackerStatusSubject.getContext(), "Starting in 3 seconds...", Toast.LENGTH_SHORT).show();


        updateIsPlaying();
        this.trackerStatusSubject.startPlayingSound();

        final int[] repCount = {0};
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (repCount[0] < numRep) {
                    repExecutor = Executors.newSingleThreadScheduledExecutor();
                    playOneRepSound(duration);
                    repCount[0]++;
                } else {
                    if (executor != null && !executor.isShutdown()) {
                        executor.shutdownNow();
                    }
                    updateIsNotPlaying();
                }
            }
        }, DELAY_IN_SECOND, duration + INTERVAL_IN_SECOND, TimeUnit.SECONDS);

    }

    private void stopSound() {
        this.shutDownExecutors();
        updateIsNotPlaying();
    }

    public void onPlaySoundClick() {
        if (!isPlaying) {
            this.playSound();
        } else {
            this.stopSound();
        }
    }

    private void updateViews() {
        Exercise activeExercise = this.trackerStatusSubject.getActiveExercise();
        if (activeExercise != null && activeExercise.getRepDuration() > 0 && activeExercise.getNumReps() > 0) {
            this.btnPlaySound.setEnabled(true);
        } else {
            this.btnPlaySound.setEnabled(false);
        }
    }

    @Override
    public void notifyStateChanged() {
        this.updateViews();
    }
    @Override
    public void notifyExercisesChanged(List<Exercise> exercises) {
        if (!isPlaying || this.lastPlayedExercise == null) {
            return;
        }

        Exercise activeExercise = this.trackerStatusSubject.getActiveExercise();

        if (activeExercise == null || this.lastPlayedExercise == null) {
            stopSound();
        } else if (activeExercise.getId() != this.lastPlayedExercise.getId()) {
            stopSound();
        }
    }

    @Override
    public void notifyStartExercise() {
        stopSound();
    }

    @Override
    public void notifyStartBreak() {
        stopSound();
    }

    @Override
    public void notifyContinueSession() {}

    @Override
    public void notifyPauseSession() {
        stopSound();
    }

    @Override
    public void notifyFinishSession() {
        stopSound();
    }

    @Override
    public void notifyResetSession() {
        stopSound();
    }

    @Override
    public void notifyOnDestroy() {
        stopSound();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}