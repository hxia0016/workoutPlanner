package com.example.workoutplanner;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.workoutplanner.UserSignIn.LoginUser;
import com.example.workoutplanner.data.database.ExerciseDatabase;
import com.example.workoutplanner.data.entity.Exercise;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkMg extends Worker {
    Exercise exercise;
    Exercise nullexercise = null;


    public WorkMg(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public Result doWork() {
        SimpleDateFormat updatetime = new SimpleDateFormat("ddMyyyy");
        SimpleDateFormat updatetimeselect = new SimpleDateFormat("dd/M/yyyy");
        SimpleDateFormat testtime = new SimpleDateFormat("ddMyyyy hh:mm:ss");
        String date = updatetime.format(new Date());
        String dateselect = updatetimeselect.format(new Date());
        String testdate = testtime.format(new Date());

        FirebaseDatabase r_database = FirebaseDatabase.getInstance();
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = r_database.getReference("exerciseDatatestbd22");

        String token = currentUser.getUid();
        String exerciseid = date + token;


        Log.d("Method be called", "doWork");
        Log.d("doWork", "testdate test " + testdate);
        Log.d("doWork", "token test " + token);
        Log.d("doWork", "Useremail test " + LoginUser.StoreUseremail);
        Log.d("doWork", "exerciseid test " + exerciseid);

        //Exercise exercise = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().findByDateandUser(dateselect, LoginUser.StoreUseremail);

        List<Exercise> exerciselist = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getAllByDateandUserList(dateselect, LoginUser.StoreUseremail);
        //LiveData<List<Exercise>> exercise_db = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getAllByDateandUser(dateselect, LoginUser.StoreUseremail);
        myRef.child(exerciseid).setValue(nullexercise)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("set Null ", " successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("set Null ", "Failed", e);
                    }
                });
        Log.d("doWork", "exerciselist test " + exerciselist);

        for (Exercise temp : exerciselist) {
            Log.d("doWork", "temp test " + temp);
            Log.d("Method be called", "Firebase setValue");

            myRef.child(exerciseid).push().setValue(temp)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("start setValue ", " successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("start setValue ", "Failed ", e);
                        }
                    });
        }

        Log.d("doWork", "finish realtime " + LoginUser.StoreUseremail + testdate);


        return Result.success();
    }
}



