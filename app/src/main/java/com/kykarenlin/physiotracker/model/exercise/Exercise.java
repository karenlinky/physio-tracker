package com.kykarenlin.physiotracker.model.exercise;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.kykarenlin.physiotracker.enums.ExerciseSessionStatus;

@Entity(tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String videoUrl;
    private String numSets;
    private int numReps;
    private int repDuration;
    private String repDurationUnit;
    private String description;

    private boolean isArchived;

    private String sessionStatus;

    private int intervalBetweenRep;

    private int progressTimestamp;



    public Exercise(String name, String videoUrl, String numSets, int numReps, int repDuration, String repDurationUnit, String description) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.numSets = numSets;
        this.numReps = numReps;
        this.repDuration = repDuration;
        this.repDurationUnit = repDurationUnit;
        this.description = description;
        this.isArchived = false;
        this.sessionStatus = ExerciseSessionStatus.NOT_COMPLETED.toString();
        this.intervalBetweenRep = 3;
        this.progressTimestamp = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public void setIntervalBetweenRep(int intervalBetweenRep) {
        this.intervalBetweenRep = intervalBetweenRep;
    }

    public void setProgressTimestamp(int progressTimestamp) {
        this.progressTimestamp = progressTimestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getNumSets() {
        return numSets;
    }

    public int getNumReps() {
        return numReps;
    }

    public int getRepDuration() {
        return repDuration;
    }

    public String getRepDurationUnit() {
        return repDurationUnit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public int getIntervalBetweenRep() {
        return intervalBetweenRep;
    }

    public int getProgressTimestamp() {
        return progressTimestamp;
    }
}
