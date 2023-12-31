package com.kykarenlin.physiotracker.model.event;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kykarenlin.physiotracker.enums.EventImprovementStatus;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.ArrayList;
import java.util.Calendar;
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

    public void archiveAllEventsBeforeTimestamp(long timestamp) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.archiveAllEventsBeforeTimestamp(timestamp);
        });
    }

    public LiveData<List<Event>> getEventsBeforeTimestamp(long timestamp) {
        return eventDao.getEventsBeforeTimestamp(timestamp);
    }

    public void archiveAllEventsWithoutStartDateCreatedBeforeTimestamp(int id, long lastModifiedTime) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            eventDao.archiveAllEventsWithoutStartDateCreatedBeforeTimestamp(id, lastModifiedTime);
        });
    }

    public LiveData<List<Event>> getEventsWithoutStartDateBeforeTimestamp(int id, long lastModifiedTime) {
        return eventDao.getEventsWithoutStartDateBeforeTimestamp(id, lastModifiedTime);
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

    private long getTimeInMillis(int year, int month, int date) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, date, 0, 0, 0);
        return c.getTimeInMillis();
    }

    private long getTimeInMillis(int year, int month, int date, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, date, hour, minute, 0);
        return c.getTimeInMillis();
    }

    private long getCurrentTime() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    public void loadTestData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        List<Event> archivedEvents = new ArrayList<>();

        archivedEvents.add(new Event(
                "Leg workout",
                getTimeInMillis(2023, 9, 26),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Bike machine",
                getTimeInMillis(2023, 9, 28),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Abs workout",
                getTimeInMillis(2023, 9, 29),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Arm workout",
                getTimeInMillis(2023, 9, 30),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Bike machine",
                getTimeInMillis(2023, 9, 31),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Back workout",
                getTimeInMillis(2023, 10, 1),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Leg workout",
                getTimeInMillis(2023, 10, 4),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Calf raise - left ankle (inner) pain",
                getTimeInMillis(2023, 10, 4),
                0,
                false,
                false,
                true,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Knee discomfort while jumping forward",
                getTimeInMillis(2023, 10, 12),
                0,
                false,
                false,
                true,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Leg workout",
                getTimeInMillis(2023, 10, 22),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Arm workout",
                getTimeInMillis(2023, 10, 23),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Glute exercise - the other side also feels tired",
                0,
                0,
                false,
                false,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Pain in right thumb",
                0,
                0,
                false,
                false,
                true,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Goblet squat (back gets tired)/ back pain",
                0,
                0,
                false,
                false,
                true,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        archivedEvents.add(new Event(
                "Wing Chun",
                getTimeInMillis(2023, 10, 18),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.IMPROVED.toString()
        ));

        archivedEvents.add(new Event(
                "Wing Chun",
                getTimeInMillis(2023, 10, 25),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.IMPROVED.toString()
        ));

        for (Event archivedEvent : archivedEvents) {
            archivedEvent.setArchived(true);
            executor.execute(() -> {
                eventDao.insert(archivedEvent);
            });
        }

        List<Event> activeEvents = new ArrayList<>();

        activeEvents.add(new Event(
                "Back pain",
                getTimeInMillis(2023, 10, 29),
                getCurrentTime(),
                false,
                false,
                true,
                true,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        activeEvents.add(new Event(
                "Back workout",
                getTimeInMillis(2023, 10, 29, 15, 0),
                0,
                false,
                true,
                false,
                false,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        activeEvents.add(new Event(
                "Calf raise - left second toe hurts\n" +
                        "Single leg hopping (on spot) - right knee hurts on second hop (with popping sound)\n" +
                        "Hopping sideways - right knee hurts when touches floor",
                getTimeInMillis(2023, 10, 30, 21, 0),
                0,
                true,
                false,
                true,
                true,
                EventImprovementStatus.UNCHANGED.toString()
        ));

        for (Event activeEvent : activeEvents) {
            executor.execute(() -> {
                eventDao.insert(activeEvent);
            });
        }

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
