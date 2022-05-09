package com.example.workoutplanner.data;

public class Report {
    public float consumedCal, burnedCal, remainCal;

    public Report(){

    }

    public Report(float consumedCal, float burnedCal, float remainCal) {
        this.consumedCal = consumedCal;
        this.burnedCal = burnedCal;
        this.remainCal = remainCal;
    }

    public double getconsumedCal() {
        return consumedCal;
    }

    public void setconsumedCal(float consumedCal) {
        this.consumedCal = consumedCal;
    }

    public double getburnedCal() {
        return burnedCal;
    }

    public void setburnedCal(float burnedCal) {
        this.burnedCal = burnedCal;
    }

    public double getremainCal() {
        return remainCal;
    }

    public void setremainCal(float remainCal) {
        this.remainCal = remainCal;
    }
}
