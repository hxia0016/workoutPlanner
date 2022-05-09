package com.example.workoutplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.workoutplanner.R;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.databinding.ReportFragmentBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;
    public ReportFragment(){}
    private PieChart pieChart;
    View report;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        report =inflater.inflate(R.layout.report_fragment, container, false);
        View view = binding.getRoot();
        binding.drawpie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadpiedata();
            }
        });

        binding.drawbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadbardata();
            }
        });


        return view;
//       this is test comment 123456789
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void loadpiedata(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(1,"Calories consumed"));
        entries.add(new PieEntry(2,"Calories burned"));
        entries.add(new PieEntry(3,"Remain Calories"));

        PieDataSet Pdataset = new PieDataSet(entries,null);
        Pdataset.setDrawIcons(false);



        ArrayList<Integer> piecolor = new ArrayList<>();
        piecolor.add(Color.rgb(255,0,0));
        piecolor.add(Color.rgb(0,111,0));
        piecolor.add(Color.rgb(0,0,255));

        Pdataset.setColors(piecolor);
        PieData Pdata = new PieData(Pdataset);
        Pdata.setValueFormatter(new PercentFormatter(pieChart));
        Pdata.setValueTextSize(11f);
        Pdata.setValueTextColor(Color.WHITE);
        binding.piechart.setDrawEntryLabels(false);
        binding.piechart.setData(Pdata);
        binding.piechart.invalidate();
    }
    public void loadbardata(){
        ArrayList<BarEntry> consumedCal = new ArrayList<>();
        ArrayList<BarEntry> burnedCal = new ArrayList<>();
        consumedCal.add(new BarEntry(0,1));
        consumedCal.add(new BarEntry(1,6));
        consumedCal.add(new BarEntry(3,13));

        burnedCal.add(new BarEntry(0,3));
        burnedCal.add(new BarEntry(1,8));
        burnedCal.add(new BarEntry(3,18));

        BarDataSet bar1 = new BarDataSet(consumedCal,"Calories Consumed");
        bar1.setColor(Color.RED);
        BarDataSet bar2 = new BarDataSet(burnedCal,"Calories burned");
        bar2.setColor(Color.BLUE);

        BarData data = new BarData(bar1,bar2);
        binding.barchart.setData(data);

        binding.barchart.invalidate();
    }
}