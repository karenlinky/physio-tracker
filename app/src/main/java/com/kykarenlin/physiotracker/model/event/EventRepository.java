package com.kykarenlin.physiotracker.model.event;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> allEventsWithStartDate;
    private LiveData<List<Event>> allEventsWithoutStartDate;
    private LiveData<List<Event>> allArchivedEventsWithStartDate;
    private LiveData<List<Event>> allArchivedEventsWithoutStartDate;

    public EventRepository(Application application) {
        EventDatabase database = EventDatabase.getInstance(application);
        eventDao = database.eventDao();
        allEvents = eventDao.getAllEvents();
        allEventsWithStartDate = eventDao.getEventsWithStartDate();
        allEventsWithoutStartDate = eventDao.getEventsWithoutStartDate();
        allArchivedEventsWithStartDate = eventDao.getArchivedEventsWithStartDate();
        allArchivedEventsWithoutStartDate = eventDao.getArchivedEventsWithoutStartDate();
    }

    public void insert(Event event) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.insert(event);
        });
    }

    public void update(Event event) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.update(event);
        });
    }

    public void delete(Event event) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.delete(event);
        });
    }

    public void deleteAllEvents() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.deleteAllEvents();
        });
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Event>> getEventsWithStartDate() {
        return allEventsWithStartDate;
    }
    public LiveData<List<Event>> getEventsWithoutStartDate() {
        return allEventsWithoutStartDate;
    }
    public LiveData<List<Event>> getArchivedEventsWithStartDate() {
        return allArchivedEventsWithStartDate;
    }
    public LiveData<List<Event>> getArchivedEventsWithoutStartDate() {
        return allArchivedEventsWithoutStartDate;
    }

    public LiveData<Event> getEventById(int id) {
        return eventDao.getEventById(id);
    }
}
