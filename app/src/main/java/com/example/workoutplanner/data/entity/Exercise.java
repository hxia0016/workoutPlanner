package com.example.workoutplanner.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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


    public Exercise(@NonNull String user_email, @NonNull String exercise_name, int duration, @NonNull String addTime) {
        this.user_email = user_email;
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.states = false;
        this.addTime = addTime;
    }
    @Ignore
    public Exercise(@NonNull String exercise_name, int duration) {
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.states = false;
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


    public static List<Exercise> createContactsList() {

        List<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("Running",30));
        exercises.add(new Exercise("Walk",30));
        return exercises;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "uid=" + uid +
                ", user_email='" + user_email + '\'' +
                ", exercise_name='" + exercise_name + '\'' +
                ", duration=" + duration +
                ", states=" + states +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}

