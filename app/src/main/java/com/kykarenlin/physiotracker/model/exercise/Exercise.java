package com.kykarenlin.physiotracker.model.exercise;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    private boolean isCompleted;

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
        this.isCompleted = false;
        this.progressTimestamp = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public boolean getIsArchived() {
        return isArchived;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public int progressTimestamp() {
        return progressTimestamp;
    }
}
