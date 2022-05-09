package com.example.workoutplanner.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplanner.adapter.RecyclerViewAdapter;
import com.example.workoutplanner.data.viewModel.PlanViewModel;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.model.Exercies;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private PlanViewModel model;
    private HomeFragmentBinding binding;
    public HomeFragment(){}

    private RecyclerView.LayoutManager layoutManager;
    private List<Exercies> exercises;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //set the user name

        //set the date
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.time.setText("Today is: "+day+"/"+month);


        //The recycle view
        exercises = new ArrayList<Exercies>();
        exercises = Exercies.createContactsList();
        adapter = new RecyclerViewAdapter(exercises);
        binding.recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //diff: this -> getActivity()
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercise = binding.eName.getText().toString().trim();
                String sduration= binding.eDuration.getText().toString().trim();
                if (!exercise.isEmpty() || !sduration.isEmpty()) {
                    int duration=new Integer(sduration).intValue();
                    saveData(exercise, duration);
                }
            }
        });


        return view;
    }

    private void saveData(String exercise, int duration) {
        Exercies ex = new Exercies(exercise, duration);
        exercises.add(ex);
        adapter.addUnits(exercises);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}