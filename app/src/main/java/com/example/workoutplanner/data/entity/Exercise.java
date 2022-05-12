package com.example.workoutplanner.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_email")
    @NonNull
    public String user_email;
    @ColumnInfo(name = "name")
    @NonNull
    public String exercise_name;
    @ColumnInfo(name = "duration")
    @NonNull
    public int duration;
    @ColumnInfo(name = "states") // 0-un complete; 1- complete
    @NonNull
    public boolean states;
    @ColumnInfo(name = "addTime")
    @NonNull
    public String addTime;
    @ColumnInfo(name = "completeTime")
    public String completeTime;

    public Exercise(String user_email, @NonNull String exercise_name, int duration, boolean states, @NonNull String addTime) {
        this.user_email = user_email;
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.states = states;
        this.addTime = addTime;
    }

    public Exercise(@NonNull String exercise_name, int duration, boolean states) {
        this.user_email = user_email;
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.states = states;
    }



    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public void setExercise_name(@NonNull String exercise_name) {
        this.exercise_name = exercise_name;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setStates(boolean states) {
        this.states = states;
    }
    public void setAddTime(@NonNull String addTime) {
        this.addTime = addTime;
    }
    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }
    public String getUser_email() {
        return user_email;
    }
    @NonNull
    public String getExercise_name() {
        return exercise_name;
    }
    public int getDuration() {
        return duration;
    }
    public boolean isStates() {
        return states;
    }
    @NonNull
    public String getAddTime() {
        return addTime;
    }
    public String getCompleteTime() {
        return completeTime;
    }

    public static List<Exercise> createContactsList() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("Running",30,false));
        exercises.add(new Exercise("Walk",30,false));
        return exercises;
    }
}

