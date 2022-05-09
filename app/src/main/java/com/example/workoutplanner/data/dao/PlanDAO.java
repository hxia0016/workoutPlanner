package com.example.workoutplanner.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.workoutplanner.data.entity.Plan;

import java.util.List;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM `plan` ORDER BY Activity ASC")
    LiveData<List<Plan>> getAll();
    @Query("SELECT * FROM `plan` WHERE uid = :planId LIMIT 1")
    Plan findByID(int planId);
    @Insert
    void insert(Plan plan);
    @Delete
    void delete(Plan plan);
    @Update
    void updatePlan(Plan plan);
    @Query("DELETE FROM `plan`")
    void deleteAll();
}
