package com.example.workoutplanner.data.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.workoutplanner.data.dao.ExerciseDAO;
import com.example.workoutplanner.data.database.ExerciseDatabase;
import com.example.workoutplanner.data.entity.Exercise;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ExerciseRepository {
    private ExerciseDAO exerciseDAO;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseRepository(Application application){
        ExerciseDatabase db = ExerciseDatabase.getInstance(application);
        exerciseDAO =db.exerciseDAO();
        allExercises= exerciseDAO.getAll();
    }

    // Room executes this query on a separate thread
    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void insert(final Exercise exercise){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.insert(exercise);
            }
        });
    }
    public void update(final Exercise exercise){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.updatePlan(exercise);
            }
        });
    }

    public void completeExercise(final boolean state,String userName,String uid){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.completePlan(state,userName,uid);
            }
        });
    }

    public void deleteExercise(){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.deleteAll();
            }
        });
    }

    public void getAllByStates(final boolean state,String userName){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.getAllByStates(state,userName);
            }
        });
    }

    public void getAllByUser(String userName){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.getAllByUser(userName);
            }
        });
    }

    public void getAllByDateandUser(String date,String email){
        ExerciseDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                exerciseDAO.getAllByDateandUser(date,email);
            }
        });
    }

    public LiveData<List<Exercise>>  getAllUnComplete(){
        return exerciseDAO.getAllUnComplete();
    }


//    public void updateExercise(final Exercise customer){
//        CustomerDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                customerDao.updateCustomer(customer);
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Exercise> findByUserEmail(final String userEmail) {
        return CompletableFuture.supplyAsync(new Supplier<Exercise>() {
            @Override
            public Exercise get() {
                return exerciseDAO.findByUser(userEmail);
            }
        }, ExerciseDatabase.databaseWriteExecutor);
    }
}
