package com.kykarenlin.physiotracker.model.event;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String eventDetails;

    private long eventStartTime;

    private long eventEndTime;

    private boolean isExercise;

    private boolean isActivity;

    private boolean isPainOrDiscomfort;

    private boolean isImportant;

    private String improvementStatus;

    private boolean isArchived;

    private long lastModifiedTime;

    public Event(String eventDetails, long eventStartTime, long eventEndTime, boolean isExercise, boolean isActivity, boolean isPainOrDiscomfort, String improvementStatus) {
        this.eventDetails = eventDetails;
        this.eventStartTime = eventStartTime;
        if (eventEndTime < eventStartTime) {
            eventEndTime = eventStartTime;
        }
        this.eventEndTime = eventEndTime;
        if (eventStartTime == 0) {
            this.eventEndTime = 0;
        } else if (eventEndTime == 0) {
            this.eventEndTime = eventStartTime;
        }
        this.isExercise = isExercise;
        this.isActivity = isActivity;
        this.isPainOrDiscomfort = isPainOrDiscomfort;
        this.improvementStatus = improvementStatus;
        this.isImportant = false;
        this.isArchived = false;
        this.lastModifiedTime = Calendar.getInstance().getTimeInMillis();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventEndTime(long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public int getId() {
        return id;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public long getEventStartTime() {
        return eventStartTime;
    }

    public long getEventEndTime() {
        return eventEndTime;
    }

    public boolean isExercise() {
        return isExercise;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public boolean isPainOrDiscomfort() {
        return isPainOrDiscomfort;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public String getImprovementStatus() {
        return improvementStatus;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public boolean hasStartDate() {
        return this.eventStartTime != 0;
    }
}
