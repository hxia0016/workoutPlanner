package com.example.workoutplanner.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.workoutplanner.R;
import com.example.workoutplanner.UserSignIn.LoginUser;
import com.example.workoutplanner.adapter.RecyclerViewAdapter;
import com.example.workoutplanner.data.entity.Exercise;
import com.example.workoutplanner.data.entity.Plan;
import com.example.workoutplanner.data.viewModel.ExerciseViewModel;
import com.example.workoutplanner.data.viewModel.PlanViewModel;
import com.example.workoutplanner.databinding.PlanFragmentBinding;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class PlanFragment extends Fragment {
    private PlanViewModel model;
    private PlanFragmentBinding binding;
    public PlanFragment(){}

    private RecyclerView.LayoutManager layoutManager;
    private List<Exercise> exercises;
    private RecyclerViewAdapter adapter;


    private String eName;
    private String eDuration;

    private ExerciseViewModel exerciseViewModel;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = PlanFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //get the user name
        SharedPreferences sp= getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userEmail=sp.getString("email",null);

        //we make sure that AndroidViewModelFactory creates the view model so it can
        //accept the Application as the parameter
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        List<Exercise> all = new ArrayList<>();
        exerciseViewModel.getAllExercises().observe(getActivity(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                for (Exercise temp : exercises) {
                    System.out.println(temp);
                }

            }
        });



        //The recycle view
        exercises = new ArrayList<Exercise>();

        adapter = new RecyclerViewAdapter(all);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);



        //Add the new exercise
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eName.isEmpty() || !eDuration.isEmpty()) {
                    int duration=new Integer(eDuration.split(" ")[0]).intValue();
//                    saveData(eName, duration);

                    //insert data
                    //get the now date
                    long currentTime = System.currentTimeMillis();
                    String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                    Exercise newEx = new Exercise(userEmail,eName,duration,timeNow);
                    exerciseViewModel.insert(newEx);

                    exerciseViewModel.getAllExercises().observe(getActivity(), new Observer<List<Exercise>>() {
                        @Override
                        public void onChanged(List<Exercise> exercises) {
                            for (Exercise temp : exercises) {
                                System.out.println("++++++++++++++++++++++");
                                System.out.println(Arrays.toString(exercises.toArray()));
                            }
                        }
                    });


                }

            }
        });


        //Get the spinner option
        binding.eName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] exerciseOptions = getResources().getStringArray(R.array.exercisespinnerclass);
                eName = exerciseOptions[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Get the spinner option
        binding.eDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] durationOptions = getResources().getStringArray(R.array.durationspinnerclass);
                eDuration = durationOptions[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;
    }


    private void saveData(String exercise, int duration) {
        Exercise ex = new Exercise(exercise, duration);
        exercises.add(ex);
        adapter.addUnits(exercises);
    }




    public List<Exercise> allExercisesContent(){
        List<Exercise> all = new ArrayList<Exercise>();
        exerciseViewModel.getAllExercises().observe(getActivity(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                for (Exercise temp : exercises) {
                    all.add(temp);
//                    System.out.println("++++++++++++++++++++++");
//                    System.out.println(Arrays.toString(exercises.toArray()));
                }

            }
        });

        return all;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}