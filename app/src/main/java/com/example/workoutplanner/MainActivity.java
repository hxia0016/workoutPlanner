package com.example.workoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import androidx.work.WorkManager;
import androidx.work.WorkRequest;


import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.example.workoutplanner.UserSignIn.LoginUser;
import com.example.workoutplanner.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private long delay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //long currentTime = System.currentTimeMillis();
        Calendar currentdate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();
        dueDate.set(Calendar.HOUR_OF_DAY, 0);
        dueDate.set(Calendar.MINUTE, 5);
        dueDate.set(Calendar.SECOND, 0);
        if (dueDate.before(currentdate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24);
        }
        long dueDatetime = dueDate.getTimeInMillis();
        long currentTime = currentdate.getTimeInMillis();
        delay = dueDatetime - currentTime;


        /*PeriodicWorkRequest dailyworkRequest = new PeriodicWorkRequest.Builder(WorkMg.class,24, TimeUnit.HOURS)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag("Upload data per 24h ")
                .build();*/

        /*WorkRequest dailyworkRequest = new OneTimeWorkRequest.Builder(WorkMg.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag("Upload data per 24h ")
                .build();

        WorkManager.getInstance(this).enqueue(dailyworkRequest);*/

        WorkRequest dailyworkRequest =
                new OneTimeWorkRequest.Builder(WorkMg.class)
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .addTag("Upload data per 24h ")
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(dailyworkRequest);


        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_map_fragment,
                R.id.nav_report_fragment,
                R.id.nav_profile_fragment)
//to display the Navigation button as a drawer symbol,not being shown as an Up button
                .setOpenableLayout(binding.drawerLayout).build();

        FragmentManager fragmentManager= getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController, mAppBarConfiguration);
    }

    /*public long getDelay() {

        Calendar currentdate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();
        dueDate.set(Calendar.HOUR_OF_DAY, 06);
        dueDate.set(Calendar.MINUTE, 38);
        dueDate.set(Calendar.SECOND, 0);
        if (dueDate.before(currentdate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24);
        }
        long dueDatetime = dueDate.getTimeInMillis();
        long currentTime = currentdate.getTimeInMillis();
        delay = dueDatetime - currentTime;
        rerurn delay;
    }*/

}