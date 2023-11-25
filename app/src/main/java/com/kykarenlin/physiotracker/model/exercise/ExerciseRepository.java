package com.kykarenlin.physiotracker.model.exercise;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);

        exerciseDao = database.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
    }

    public void insert(Exercise exercise) {
//        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            exerciseDao.insert(exercise);
        });
    }

    public void update(Exercise exercise) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            exerciseDao.update(exercise);
        });
    }

    public void delete(Exercise exercise) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            exerciseDao.delete(exercise);
        });
    }

    public void deleteAllExercises() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            exerciseDao.deleteAllExercise();
        });
    }

    public void setAllExercisesAsNotCompleted() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            exerciseDao.setAllExercisesAsNotCompleted();
        });
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<Exercise> getExerciseById(int id) {
        return exerciseDao.getExerciseById(id);
    }

//    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
//
//        private ExerciseDao exerciseDao;
//
//        private InsertExerciseAsyncTask(ExerciseDao exerciseDao) {
//            this.exerciseDao = exerciseDao;
//        }
//
//        @Override
//        protected Void doInBackground(Exercise... exercises) {
//            exerciseDao.insert(exercises[0]);
//            return null;
//        }
//    }
}
