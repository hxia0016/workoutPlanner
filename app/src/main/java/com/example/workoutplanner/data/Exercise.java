package com.example.workoutplanner.data;

public class Exercise {
    public String eName, time;
    public double calories;
    public Exercise(){

    }
    public Exercise(String eName, String time, double calories) {
        this.eName = eName;
        this.time = time;
        this.calories = calories;

    }
    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public double getcalories() {
        return calories;
    }

    public void setcalories(double calories) {
        this.calories = calories;
    }
}
