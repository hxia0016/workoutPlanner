package com.example.workoutplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.workoutplanner.databinding.RvLayoutBinding;
import com.example.workoutplanner.model.Exercies;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder> {
    private static List<Exercies> exercies;

    public RecyclerViewAdapter(List<Exercies> exercies) {
        this.exercies = exercies;
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int
            position) {
        final Exercies ex = exercies.get(position);
        viewHolder.binding.tvRvunit.setText(ex.geteName());
        viewHolder.binding.tvRvmark.setText(Integer.toString(ex.geteDuration()));
        viewHolder.binding.ivItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercies.remove(ex);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercies.size();
    }
    public void addUnits(List<Exercies> results) {
        exercies = results;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


