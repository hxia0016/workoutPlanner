package com.example.workoutplanner.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.workoutplanner.data.entity.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Query("SELECT * FROM `exercise`")
    LiveData<List<Exercise>> getAll();

    @Query("SELECT * FROM `exercise` WHERE user_email == :email")
    Exercise findByUser(String email);

    @Insert
    void insert(Exercise exercise);

    @Delete
    void delete(Exercise exercise);
    @Update
    void updatePlan(Exercise exercise);

    @Query("DELETE FROM `exercise`")
    void deleteAll();

    @Query("UPDATE `exercise` SET states = :state WHERE user_email == :email")
    void completePlan(boolean state,String email);

}
