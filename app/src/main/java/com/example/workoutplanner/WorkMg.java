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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = r_database.getReference("exerciseDatatestbd22");

        String token = currentUser.getUid();
        String exerciseid = date+token;



        Log.d("Method be called", "doWork");
        Log.d("doWork", "testdate test " + testdate);
        Log.d("doWork", "token test " + token);
        Log.d("doWork", "Useremail test " + LoginUser.StoreUseremail);
        Log.d("doWork", "exerciseid test " + exerciseid);

        Exercise exercise = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().findByDateandUser(dateselect, LoginUser.StoreUseremail);

        List<Exercise> exerciselist = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getAllByDateandUserList(dateselect, LoginUser.StoreUseremail);
        LiveData<List<Exercise>> exercise_db = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getAllByDateandUser(dateselect, LoginUser.StoreUseremail);
        myRef.child(exerciseid).setValue(nullexercise);

        for(Exercise temp: exerciselist){
            Log.d("doWork", "exerciselist test " + exerciselist);
            Log.d("doWork", "exercise test " + exercise);
            //Log.d("doWork", "exercise test " + exerciselist);
            Log.d("Method be called", "Firebase setValue");

            //DatabaseReference OneusermyRef = r_database.getReference("exerciseDatatestbd22"+exerciseid);
            //String dataid = OneusermyRef.push().getKey();

            myRef.child(exerciseid).push().setValue(temp)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("start setValue ", "realtime db successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("start setValue ", "Error writing realtime db", e);
                        }
                    });


            Log.d("doWork", "finish realtime " + LoginUser.StoreUseremail+testdate);

        }


        /*Log.d("doWork", "exerciselist test " + exerciselist);
        Log.d("doWork", "exercise test " + exercise);
        //Log.d("doWork", "exercise test " + exerciselist);
        Log.d("Method be called", "Firebase setValue");

            myRef.child(exerciseid).setValue(exercise)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("start setValue ", "realtime db successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("start setValue ", "Error writing realtime db", e);
                        }
                    });


        Log.d("doWork", "finish realtime " + LoginUser.StoreUseremail+testdate);*/



        //LiveData<List<Exercise>> exercise_db = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getAllByDateandUser(date, LoginUser.StoreUseremail);
        //int count = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getdatacount(date, LoginUser.StoreUseremail);

        //List uidlist = ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().getdatauid(date, LoginUser.StoreUseremail);






      /*Log.d("Method be called", "ExerciseDb collection");
        db.collection("ExerciseDb").document(exerciseid).set(exercise)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("start collection ", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("start collection ", "Error writing document", e);
                    }
                });

        //Log.d("started exercise", "Get:  " + exercise);
        Log.d("doWork", "ExerciseDb collection finished");*/

        //myRef.child("uploadDate").child("exerciseData").push().setValue(exercise_db);


        /*if (exercise != null) {
            //myRef.child("uploadDate").push().setValue(date);
            myRef.child("uploadDate").child("exerciseData").push().setValue(exercise_db);
            Log.d("d-upload", "doWork Start " + testdate);
            Log.i("i-upload", "doWork Start " + date);
        } else {
            //myRef.child("uploadDate").push().setValue(date);
            myRef.child("uploadDate").child("exerciseData").push().setValue("No record for" + date +"++"+ testdate);
            Log.d("d-No record for", " " + testtime);
            Log.i("i-No record for", " " + date);
        }*/
        return Result.success();
    }





    /*public void Onetimeexercise(String uid, String name, int duration, boolean states) {

        this.uid = uid;
        this.name = name;
        this.duration = duration;
        this.states = states;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("duration", duration);
        result.put("body", body);
        result.put("states", states);

        return result;
    }*/



}






/*   @Override
   public Result doWork() {

        Plan Plan = convertData(getInputData());

        if (Plan == null) {
            Log.i("ERROR", "WorkManager: There is no plan data today.");
            return Result.failure();
        } else {
            updateDataToFirebase(Plan);
            Log.i("SUCCESS", "WorkManager: Data Upload work completed successfully.");
            Log.i("SUCCESS", "DATA UPLOADED: \n" + Plan.toString());
            return Result.success();
        }
    }*/

///////还需要print meaningful messages with timestamps from the methods called on Logcat (print the method name too)
//    ///参见Marking Guide
/*    private Result deWork() {
        SimpleDateFormat updatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dailyRecord = database.getReference();
        Plan record = PlanDatabase.getInstance(getApplicationContext()).planDao()//后续取决与数据格式

        record.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("DataupdateWorker", "Firebase data updated at " + updatetime.format(new Date()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataUploadWorker", "Firebase operation cancelled. Error: " + error);
            }
        });
        dailyRecord.child(“record”).setValue(record);

    }
*/