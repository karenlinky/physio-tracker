package com.kykarenlin.physiotracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kykarenlin.physiotracker.model.exercise.Exercise;
import com.kykarenlin.physiotracker.model.exercise.ExerciseRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> allActiveExercises;
    private LiveData<List<Exercise>> allArchivedExercises;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
        allActiveExercises = repository.getAllActiveExercises();
        allArchivedExercises = repository.getAllArchivedExercises();
    }

    public void insert(Exercise exercise) {
        repository.insert(exercise);
    }

    public void update(Exercise exercise) {
        repository.update(exercise);
    }

    public void delete(Exercise exercise) {
        repository.delete(exercise);
    }

    public void deleteAllExercises() {
        repository.deleteAllExercises();
    }

    public void loadTestData() {
        repository.loadTestData();
    }

    public void setAllExercisesAsNotCompleted() {
        repository.setAllExercisesAsNotCompleted();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<List<Exercise>> getAllActiveExercises() {
        return allActiveExercises;
    }

    public LiveData<List<Exercise>> getAllArchivedExercises() {
        return allArchivedExercises;
    }

    public LiveData<Exercise> getExerciseById(int id) {
        return repository.getExerciseById(id);
    }
}
