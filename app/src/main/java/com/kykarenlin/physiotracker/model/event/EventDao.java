package com.kykarenlin.physiotracker.model.event;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Query("UPDATE event_table SET isArchived = 1 WHERE eventStartTime != 0 AND eventStartTime < :timestamp AND eventEndTime != 0 AND eventEndTime < :timestamp")
    void archiveAllEventsBeforeTimestamp(long timestamp);

    @Query("SELECT * FROM event_table WHERE eventStartTime != 0 AND eventStartTime < :timestamp AND eventEndTime != 0 AND eventEndTime < :timestamp")
    LiveData<List<Event>> getEventsBeforeTimestamp(long timestamp);

    @Query("UPDATE event_table SET isArchived = 1 WHERE eventStartTime == 0 AND (lastModifiedTime < :lastModifiedTime OR lastModifiedTime = :lastModifiedTime AND id < :id)")
    void archiveAllEventsWithoutStartDateCreatedBeforeTimestamp(int id, long lastModifiedTime);

    @Query("SELECT * FROM event_table WHERE eventStartTime == 0 AND (lastModifiedTime < :lastModifiedTime OR lastModifiedTime = :lastModifiedTime AND id < :id)")
    LiveData<List<Event>> getEventsWithoutStartDateBeforeTimestamp(int id, long lastModifiedTime);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM event_table WHERE (eventStartTime != 0 AND NOT isArchived) ORDER BY eventStartTime DESC")
    LiveData<List<Event>> getEventsWithStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime == 0 AND NOT isArchived) ORDER BY lastModifiedTime DESC")
    LiveData<List<Event>> getEventsWithoutStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime != 0 AND isArchived) ORDER BY eventStartTime DESC")
    LiveData<List<Event>> getArchivedEventsWithStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime == 0 AND isArchived) ORDER BY lastModifiedTime DESC")
    LiveData<List<Event>> getArchivedEventsWithoutStartDate();

    @Query("SELECT * FROM event_table WHERE id=:id")
    LiveData<Event> getEventById(int id);
}
