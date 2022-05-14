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

    @Query("SELECT * FROM `exercise` WHERE states == 0")
    LiveData<List<Exercise>> getAllUnComplete();


    @Query("SELECT * FROM `exercise` WHERE states == :flag AND user_email == :email")
    LiveData<List<Exercise>> getAllByStates(boolean flag,String email);

    @Query("SELECT * FROM `exercise` WHERE user_email == :email")
    LiveData<List<Exercise>> getAllByUser(String email);


    @Query("SELECT * FROM `exercise` WHERE user_email == :email")
    Exercise findByUser(String email);

    @Query("SELECT * FROM `exercise` WHERE addTime == :date AND user_email == :email LIMIT 1")
    Exercise findByDateandUser(String date,String email);

    @Query("SELECT * FROM `exercise` WHERE addTime == :date AND user_email == :email")
    LiveData<List<Exercise>> getAllByDateandUser(String date,String email);

    /*@Query("SELECT * FROM `exercise` WHERE addTime == :date AND user_email == :email")
    List<List<Exercise>> getAllByDateandUserList(String date,String email);*/

    @Query("SELECT * FROM `exercise` WHERE addTime == :date AND user_email == :email")
    List<Exercise> getAllByDateandUserList(String date,String email);

    @Query("SELECT count(*) FROM `exercise` WHERE addTime == :date AND user_email == :email")
    int getdatacount(String date,String email);

    /*@Query("SELECT uid FROM `exercise` WHERE addTime == :date AND user_email == :email")
    List getdatauid(String date,String email);*/

    /*@Query("SELECT * FROM `exercise` WHERE uid = uidlist[i]")
    Exercise getoneData(List uidlist,int i);*/

    @Insert
    void insert(Exercise exercise);

    @Delete
    void delete(Exercise exercise);
    @Update
    void updatePlan(Exercise exercise);

    @Query("DELETE FROM `exercise`")
    void deleteAll();

    @Query("UPDATE `exercise` SET states = :state WHERE user_email == :email AND uid=:id")
    void completePlan(boolean state,String email,String id);



}
