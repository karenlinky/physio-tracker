package com.kykarenlin.physiotracker.model.exercise;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void deleteAllExercise();

    @Query("UPDATE exercise_table SET sessionStatus = 'NOT_COMPLETED'")
    void setAllExercisesAsNotCompleted();

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise_table WHERE NOT isArchived")
    LiveData<List<Exercise>> getAllActiveExercises();

    @Query("SELECT * FROM exercise_table WHERE isArchived")
    LiveData<List<Exercise>> getAllArchivedExercises();

    @Query("SELECT * FROM exercise_table WHERE id=:id")
    LiveData<Exercise> getExerciseById(int id);
}
