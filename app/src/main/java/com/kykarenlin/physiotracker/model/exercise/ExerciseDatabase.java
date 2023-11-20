package com.kykarenlin.physiotracker.model.exercise;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public static synchronized ExerciseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExerciseDatabase.class, "exercise_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            this.populateDB(instance);
        }

        private void populateDB(ExerciseDatabase db) {
            ExerciseDao exerciseDao = db.exerciseDao();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                exerciseDao.insert(
                        new Exercise(
                                "Star Excursion Balance Exercise",
                                "https://www.youtube.com/watch?v=UoMSMdMKfBY",
                                "1 per leg",
                                5,
                                0,
                                "s",
                                "This exercise challenges single leg balance. " +
                                        "If you feel ankle discomfort, focus more on sitting your hips back and this should bias your glute muscles more and alleviate the ankle discomfort. " +
                                        "Perform 5 times in a row on each leg. Progression: Perform with eyes closed."
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "Dorsiflexion Self Mobilization",
                                "https://www.youtube.com/watch?v=rvkyQc60-HA",
                                "1 per leg",
                                10,
                                0,
                                "s",
                                "If you have your front foot elevated on a step it will be easier to practice this exercise. " +
                                        "Tie the resistance band to form a loop. You can either use your other leg to step on the band or secure the band to a heavy object. " +
                                        "Place the band around the front of your ankle at the hinge point. " +
                                        "You should feel the resistance band pulling on your ankle backwards. " +
                                        "With tension in the band, transfer your weight through your front foot while keeping your front heel flat on the surface. " +
                                        "Don't let the front knee cave inwards. Perform 15 reps each side with control."
                        )
                );
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
                exerciseDao.insert(new Exercise("Final Exercise name","https://www.kykarenlin.com","2",10,10,"s",""));
            });
        }
    };
}
