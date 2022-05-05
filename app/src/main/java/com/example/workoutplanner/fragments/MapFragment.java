package com.example.workoutplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.workoutplanner.data.viewModel.SharedViewModel;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.databinding.MapFragmentBinding;

public class MapFragment extends Fragment {
    private SharedViewModel model;
    private MapFragmentBinding addBinding;
    public MapFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}