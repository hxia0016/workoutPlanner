package com.example.workoutplanner.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.workoutplanner.data.dao.ExerciseDAO;
import com.example.workoutplanner.data.dao.PlanDAO;
import com.example.workoutplanner.data.entity.Exercise;
import com.example.workoutplanner.data.entity.Plan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Exercise.class}, version = 6, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {
    public abstract ExerciseDAO exerciseDAO();
    private static ExerciseDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized ExerciseDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ExerciseDatabase.class, "ExerciseDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
