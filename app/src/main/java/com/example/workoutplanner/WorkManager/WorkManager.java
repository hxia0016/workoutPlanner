package com.example.workoutplanner.WorkManager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.workoutplanner.data.entity.Plan;
import com.example.workoutplanner.data.database.PlanDatabase;
import com.example.workoutplanner.data.repository.PlanRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkManager extends Worker{
    public WorkManager(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }




    private Plan convertData(Data inputData) {
        return new Plan(
                inputData.getString("activity"),
                inputData.getInt("duration",-1),
                inputData.getBoolean("complete",false)

        );
    }

    @Override
    public Result doWork() {

        Plan Plan = convertData(getInputData());

        if (Plan == null) {
            Log.i("ERROR", "WorkManager: Can not get(parse) plan data.");
            return Result.failure();
        } else {
            updateDataToFirebase(Plan);
            Log.i("SUCCESS", "WorkManager: Data Upload work completed successfully.");
            Log.i("SUCCESS", "DATA UPLOADED: \n" + Plan.toString());
            return Result.success();
        }
    }

/////还需要print meaningful messages with timestamps from the methods called on Logcat (print the method name too)
    ///参见Marking Guide
    private void updateDataToFirebase(Plan Data) {
        SimpleDateFormat updatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dailyRecord = database.getReference();
        Plan record = PlanDatabase.getInstance(getApplicationContext()).planDao()//后续取决与数据格式

        record.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("DataupdateWorker", "Firebase data modified at " + updatetime.format(new Date()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataUploadWorker", "Firebase operation cancelled. Error: " + error);
            }
        });
        dailyRecord.child(“record”).setValue(record);

    }
}
