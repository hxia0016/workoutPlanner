package com.example.workoutplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.workoutplanner.data.viewModel.PlanViewModel;
import com.example.workoutplanner.databinding.ProfileFragmentBinding;

public class ProfileFragment extends Fragment {
    private PlanViewModel model;
    private ProfileFragmentBinding addBinding;
    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = ProfileFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}