package com.example.workoutplanner.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Plan {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "Activity")
    @NonNull
    public String activity;
    @ColumnInfo(name = "Duration")
//    @NonNull
//    public String date;
//    @ColumnInfo(name = "Date")
    @NonNull
    public int duration;
    @ColumnInfo(name = "Complete")
    @NonNull
    public boolean complete;


    public Plan(@NonNull String activity, @NonNull int duration, @NonNull boolean complete) {
        this.activity = activity;
        this.duration = duration;
        this.complete = complete;
//        this.date = date;
    }

    @NonNull
    public String getActivity() {
        return activity;
    }

    public void setActivity(@NonNull String activity) {
        this.activity = activity;
    }

    @NonNull
    public int getDuration() {
        return duration;
    }

    public void setDuration(@NonNull int duration) {
        this.duration = duration;
    }

    @NonNull
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(@NonNull boolean complete) {
        this.complete = complete;
    }

//    public void setDate(@NonNull String date){this.date = date;}
}
