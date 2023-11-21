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
                exerciseDao.insert(
                        new Exercise(
                                "Single Leg Soleus Bridge",
                                "https://www.youtube.com/watch?v=mVfoGFiZzDc",
                                "1/leg",
                                10,
                                10,
                                "s",
                                "Use a textbook or some stairs to help elevate your feet. " +
                                        "When you are bridging up, make sure your heel is not touching the elevated surface and you are pushing through the balls of your foot. " +
                                        "You should feel your lower leg muscles, thigh muscles, glute muscles, and abdominal muscles working. " +
                                        "Hold for 10 seconds each repetition, repeat 10 repetitions on each leg"
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "FHL Calf Raise (Single Leg)",
                                "https://www.youtube.com/watch?v=X7Va-ujGCp0",
                                "1/leg",
                                15,
                                10,
                                "s",
                                "The further you slide your foot back, the more stretch you should feel in the calf. " +
                                        "Make sure you lower your heel back to the ground with control with each repetition. " +
                                        "Perform 15 reps each side, hold for 3 seconds at the top. " +
                                        "Possible Progression: Hold for 10 seconds at the top."
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "Chin Tuck Head Lift",
                                "https://www.youtube.com/watch?v=UaIheiSGXHE",
                                "1",
                                15,
                                3,
                                "s",
                                "Keep your chin tucked (i.e., chin touching your throat) throughout the entire exercise. " +
                                        "Do not let your shoulders shrug. " +
                                        "Hold your head up for 3 seconds before lowering back down with control. " +
                                        "We added rotation, doing this while maintaining the chin tuck. " +
                                        "Perform 15 reps each side. " +
                                        "Possible Progression: Perform with your head \"dangling off\" the bed. " +
                                        "You will need to slowly lower your head all the way down (neck extension)."
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "Tibialis Anterior Stretch",
                                "https://www.youtube.com/watch?v=iaA5PT85azU",
                                "1 per leg",
                                3,
                                10,
                                "s",
                                "To stretch the front of your ankle. Hold for 10 seconds each side, perform 3 reps each side."
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "Cat Cow",
                                "https://www.youtube.com/watch?v=hv5QnaDPyfY",
                                "1",
                                10,
                                0,
                                "s",
                                "Notice how he moves segment by segment, ensuring every part of his spine is bending/arching. " +
                                        "Your goal is to make sure you're not just folding/hinging through your low back and neck. " +
                                        "(10 reps each direction, everyday)"
                        )
                );
                exerciseDao.insert(
                        new Exercise(
                                "Quad Squats and Glute squats",
                                "",
                                "3 (2 quad, 1 glute)",
                                10,
                                0,
                                "s",
                                ""
                        )
                );
            });
        }
    };
}
