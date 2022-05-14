package com.example.workoutplanner.data.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workoutplanner.data.entity.Exercise;
import com.example.workoutplanner.data.repository.ExerciseRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository eRepository;
    private LiveData<List<Exercise>> allExercises;
    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        eRepository = new ExerciseRepository(application);
        allExercises =eRepository.getAllExercises();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Exercise> findByUserEmail(final String userEmail){
        return eRepository.findByUserEmail(userEmail);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public void insert(Exercise ex) {
        eRepository.insert(ex);
    }

    public void completeExercise(boolean state,String userEmail,String uid){
        eRepository.completeExercise(state,userEmail,uid);
    }

    public void deleteAllExercise(){
        eRepository.deleteExercise();
    }

    public void getAllByStates(boolean state,String userEmail){
        eRepository.getAllByStates( state, userEmail);
    }

    public void getAllByUser(String userEmail){
        eRepository.getAllByUser(userEmail);
    }

    public void getAllByDateandUser(String date,String email){
         eRepository.getAllByDateandUser( date, email);
    }

    public LiveData<List<Exercise>> getAllUnComplete(){
        return eRepository.getAllUnComplete();
    }


}
