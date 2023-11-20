package com.kykarenlin.physiotracker.model.exercise;

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



    public Exercise(String name, String videoUrl, String numSets, int numReps, int repDuration, String repDurationUnit, String description) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.numSets = numSets;
        this.numReps = numReps;
        this.repDuration = repDuration;
        this.repDurationUnit = repDurationUnit;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
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
}
