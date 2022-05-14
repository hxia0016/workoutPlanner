package com.example.workoutplanner.data.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workoutplanner.data.entity.Plan;
import com.example.workoutplanner.data.repository.PlanRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlanViewModel extends AndroidViewModel {
    private PlanRepository pRepository;
    private LiveData<List<Plan>> allPlans;
    public PlanViewModel (Application application) {
        super(application);
        pRepository = new PlanRepository(application);
        allPlans = pRepository.getAllPlans();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Plan> findByIDFuture(final int planId){
        return pRepository.findByIDFuture(planId);
    }
    public LiveData<List<Plan>> getAllPlans() {
        return allPlans;
    }
    public void insert(Plan plan) {
        pRepository.insert(plan);
    }


    public void deleteAll() {
        pRepository.deleteAll();
    }
    public void update(Plan plan) {
        pRepository.updatePlan(plan);
    }
}