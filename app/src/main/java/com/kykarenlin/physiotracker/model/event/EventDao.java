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

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM event_table WHERE (eventStartTime != 0 AND NOT isArchived) ORDER BY eventStartTime")
    LiveData<List<Event>> getEventsWithStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime == 0 AND NOT isArchived) ORDER BY lastModifiedTime")
    LiveData<List<Event>> getEventsWithoutStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime != 0 AND isArchived) ORDER BY eventStartTime")
    LiveData<List<Event>> getArchivedEventsWithStartDate();

    @Query("SELECT * FROM event_table WHERE (eventStartTime == 0 AND isArchived) ORDER BY lastModifiedTime")
    LiveData<List<Event>> getArchivedEventsWithoutStartDate();

    @Query("SELECT * FROM event_table WHERE id=:id")
    LiveData<Event> getEventById(int id);
}
