package com.kykarenlin.physiotracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.model.event.EventRepository;
import com.kykarenlin.physiotracker.model.exercise.Exercise;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository repository;

    private LiveData<List<Event>> allEvents;

    private LiveData<List<Event>> allEventsWithStartDate;

    private LiveData<List<Event>> allEventsWithoutStartDate;

    private LiveData<List<Event>> allArchivedEventsWithStartDate;

    private LiveData<List<Event>> allArchivedEventsWithoutStartDate;

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
        allEventsWithStartDate = repository.getEventsWithStartDate();
        allEventsWithoutStartDate = repository.getEventsWithoutStartDate();
        allArchivedEventsWithStartDate = repository.getArchivedEventsWithStartDate();
        allArchivedEventsWithoutStartDate = repository.getArchivedEventsWithoutStartDate();
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void update(Event event) {
        repository.update(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public void loadTestData() {
        repository.loadTestData();
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

    public LiveData<Event> getExerciseById(int id) {
        return repository.getEventById(id);
    }
}
