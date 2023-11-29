package com.kykarenlin.physiotracker.model.event;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kykarenlin.physiotracker.enums.EventImprovementStatus;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase instance = null;

    public abstract EventDao eventDao();

    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventDatabase.class, "event_database")
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

//            this.populateDB(instance);
        }

        private void populateDB(EventDatabase db) {
            EventDao eventDao = db.eventDao();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Calendar cal1 = Calendar.getInstance();
                cal1.set(2023, Calendar.OCTOBER, 26);
                long time1 = cal1.getTimeInMillis();
                Calendar cal2 = Calendar.getInstance();
                cal2.set(2023, Calendar.NOVEMBER, 28);
                long time2 = cal2.getTimeInMillis();
                eventDao.insert(
                        new Event(
                                "Back pain",
                                time1, // 2023 Oct 26
                                time2, // 2023 Nov 28
                                false,
                                true,
                                EventImprovementStatus.UNCHANGED.toString()
                        )
                );
                Calendar cal3 = Calendar.getInstance();
                cal3.set(2023, Calendar.NOVEMBER, 12);
                long time3 = cal3.getTimeInMillis();
                eventDao.insert(
                        new Event(
                                "Knee discomfort while jumping forward",
                                time3, // 2023 Nov 12
                                0,
                                false,
                                true,
                                EventImprovementStatus.UNCHANGED.toString()
                        )
                );
                eventDao.insert(
                        new Event(
                                "Knee discomfort",
                                time3, // 2023 Nov 12
                                0,
                                false,
                                true,
                                EventImprovementStatus.UNCHANGED.toString()
                        )
                );
                eventDao.insert(
                        new Event(
                                "Thumb pain",
                                time3, // 2023 Nov 12
                                0,
                                false,
                                true,
                                EventImprovementStatus.WORSENED.toString()
                        )
                );
                Calendar cal4 = Calendar.getInstance();
                cal4.set(2023, Calendar.NOVEMBER, 13);
                long time4 = cal4.getTimeInMillis();
                eventDao.insert(
                        new Event(
                                "Thumb pain",
                                time4, // 2023 Nov 13
                                0,
                                true,
                                true,
                                EventImprovementStatus.UNCHANGED.toString()
                        )
                );
                Calendar cal5 = Calendar.getInstance();
                cal5.set(2023, Calendar.NOVEMBER, 15);
                long time5 = cal5.getTimeInMillis();
                eventDao.insert(
                        new Event(
                                "Thumb pain",
                                time5, // 2023 Nov 15
                                0,
                                false,
                                true,
                                EventImprovementStatus.IMPROVED.toString()
                        )
                );
                eventDao.insert(
                        new Event(
                                "Thumb pain",
                                0,
                                0,
                                false,
                                true,
                                EventImprovementStatus.IMPROVED.toString()
                        )
                );
                eventDao.insert(
                        new Event(
                                "Thumb pain",
                                0,
                                0,
                                false,
                                true,
                                EventImprovementStatus.IMPROVED.toString()
                        )
                );
            });
        }
    };
}
