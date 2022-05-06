package com.example.workoutplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.workoutplanner.data.viewModel.SharedViewModel;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.databinding.ReportFragmentBinding;

public class ReportFragment extends Fragment {
    private SharedViewModel model;
    private ReportFragmentBinding addBinding;
    public ReportFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        return view;
//       this is test comment
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}