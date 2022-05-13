package com.example.workoutplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.example.workoutplanner.data.entity.Exercise;
import com.example.workoutplanner.data.viewModel.ExerciseViewModel;
import com.example.workoutplanner.databinding.RvLayoutBinding;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder> {
    List<Exercise> exercies = new ArrayList<>();

    public RecyclerViewAdapter(List<Exercise> exercies) {
        this.exercies = exercies;
    }

    public RecyclerViewAdapter() {

    }

    public void setExercise(List<Exercise> exercises){
        this.exercies = exercises;
    }

    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        RvLayoutBinding binding =
                RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Exercise ex = exercies.get(position);
        viewHolder.binding.tvRvunit.setText(ex.getExercise_name());
        viewHolder.binding.tvRvmark.setText(Integer.toString(ex.getDuration()));
        viewHolder.binding.ivItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercies.remove(ex);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercies.size();
    }


    public void addExercise(Exercise ex){
        this.exercies.add(ex);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


