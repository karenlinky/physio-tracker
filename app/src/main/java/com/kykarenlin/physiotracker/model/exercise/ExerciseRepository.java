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

    public void loadTestData() {
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
            exerciseDao.insert(
                    new Exercise(
                            "Dorsiflexion with resistance band",
                            "",
                            "1 per leg",
                            15,
                            0,
                            "s",
                            ""
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Single leg snap down",
                            "",
                            "1 per leg",
                            15,
                            0,
                            "s",
                            "https://www.youtube.com/watch?v=4cyedSvX_OA"
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Turn while jumping",
                            "",
                            "1 per side",
                            1,
                            1,
                            "min",
                            ""
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Single leg balance on soft surface",
                            "",
                            "1 per leg",
                            50,
                            0,
                            "s",
                            "throw the ball towards the wall in 4 different directions (up, left, down, right) complete 50 successful passes to yourself. The priority is to maintain the chin tucked position the entire exercise."
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Split squat",
                            "",
                            "3 per leg",
                            10,
                            0,
                            "s",
                            ""
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Dead bug",
                            "",
                            "1 per side",
                            20,
                            0,
                            "s",
                            ""
                    )
            );
            exerciseDao.insert(
                    new Exercise(
                            "Glute exercise",
                            "",
                            "1 per leg",
                            10,
                            10,
                            "s",
                            ""
                    )
            );
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
