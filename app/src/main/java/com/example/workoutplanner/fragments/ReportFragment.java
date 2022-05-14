package com.example.workoutplanner.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.R;
import com.example.workoutplanner.data.entity.Exercise;
import com.example.workoutplanner.data.viewModel.ExerciseViewModel;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.databinding.ReportFragmentBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;
    public ReportFragment(){}
    private PieChart pieChart;
    View report;
    private DatePickerDialog.OnDateSetListener piedateListener;
    private DatePickerDialog.OnDateSetListener bardateListener1;
    private DatePickerDialog.OnDateSetListener bardateListener2;
    private ExerciseViewModel exerciseViewModel;
    private String userEmail;
    private String Piedate;
    private String Bardate1;
    private String Bardate2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        report =inflater.inflate(R.layout.report_fragment, container, false);
        View view = binding.getRoot();
        SharedPreferences sp= getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userEmail=sp.getString("email",null);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        List<Exercise> all = new ArrayList<>();
        List<Exercise> Run = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        Date date = new Date();//get today
        String currentDate = dateFormat.format(date);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //get yesterday
        Date date1 = calendar.getTime();
        String startdate = dateFormat.format(date1);
        // set default date in Edittext
        binding.pieDate.setText(currentDate);
        binding.barDate1.setText(startdate);
        binding.barDate2.setText(currentDate);
        // get all exercises data
        exerciseViewModel.getAllExercises().observe(getActivity(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                String run = "Run";

                for (Exercise temp : exercises) {
                    all.add(temp);
                }
                for (Exercise temp : exercises) {
                    if (run.equals(temp.getExercise_name() ) ){
                        Run.add(temp);
                    }
                }
                System.out.println("All data: " +all);
                System.out.println("Running: " +Run);
            }
        });
        //date picker
        binding.pieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.pieDate.getWindowToken(), 0);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,piedateListener,year,month,day);
                datePickerDialog.show();
            }
        });

        piedateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day+"/"+month+"/"+year;
                binding.pieDate.setText(date);


            }


        };
        binding.barDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.barDate1.getWindowToken(), 0);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,bardateListener1,year,month,day);
                datePickerDialog.show();
            }
        });
        bardateListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day+"/"+month+"/"+year;
                binding.barDate1.setText(date);

            }
        };
        binding.barDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.barDate2.getWindowToken(), 0);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,bardateListener2,year,month,day);
                datePickerDialog.show();
            }
        });
        bardateListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day+"/"+month+"/"+year;
                binding.barDate2.setText(date);

            }
        };

        // draw chart
        binding.drawpie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Piedate = binding.pieDate.getText().toString();
                if (Piedate.isEmpty()){
                    Toast.makeText(getActivity(),"Date can not be empty",Toast.LENGTH_LONG).show();
                }else{
                    loadpiedata(all,Piedate);
                }

            }
        });

        binding.drawbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bardate1 = binding.barDate1.getText().toString();
                Bardate2 = binding.barDate2.getText().toString();
                //check if date range is wrong
                if(Bardate2.compareTo(Bardate1)>=0) {

                    loadbardata(all, Bardate1, Bardate2);
                }
                else{

                    Toast.makeText(getActivity(),"Second date should be after the first date!",Toast.LENGTH_LONG).show();
                    return;
                }
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

    // draw pie chart
    public void loadpiedata(List<Exercise>alldata,String date){
        float completed = 0;
        float imcompleted = 0;

        for (Exercise temp : alldata) {
            System.out.println(temp.getAddTime());
        }
        // get data in selected date
        List<Exercise> oneday = new ArrayList<>();
        for (Exercise temp : alldata) {
            if (Piedate.equals(temp.getAddTime()) ){
                oneday.add(temp);
            }
        }
        System.out.println("one day: " +oneday);
        System.out.println("Pie date: " +Piedate);
        for (Exercise temp : oneday) {
            // get the number of completed and incompleted exercises
            if (temp.isStates() == true){
                completed +=1.0;
            }
            else{
                imcompleted +=1.0;
            }
        }
        System.out.println("completed: " +completed);
        System.out.println("imcompleted: " +imcompleted);

        //set data
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(completed,"Completed"));
        entries.add(new PieEntry(imcompleted,"Incompleted"));


        PieDataSet Pdataset = new PieDataSet(entries,null);
        Pdataset.setDrawIcons(false);
        ArrayList<Integer> piecolor = new ArrayList<>();
        piecolor.add(Color.rgb(255,0,0));
        piecolor.add(Color.rgb(0,111,0));

        Pdataset.setColors(piecolor);
        PieData Pdata = new PieData(Pdataset);
        Pdata.setValueFormatter(new PercentFormatter(pieChart));
        Pdata.setValueTextSize(11f);
        Pdata.setValueTextColor(Color.WHITE);
        binding.piechart.setDrawEntryLabels(false);
        binding.piechart.setData(Pdata);
        binding.piechart.invalidate();
    }
    public void loadbardata(List<Exercise>alldata,String startdate,String enddate){
        int completed = 0;
        int imcompleted = 0;
        ArrayList<BarEntry> complete = new ArrayList<>();
        ArrayList<BarEntry> incomplete = new ArrayList<>();
        List<String> datelist = new ArrayList<>();

        // get date range
        List<Exercise> daterange = new ArrayList<>();
        for (Exercise temp : alldata) {
            if (temp.getAddTime().compareTo(startdate)>=0 && temp.getAddTime().compareTo(enddate)<=0){
                daterange.add(temp);
            }

        }
        System.out.println("date range: " +daterange);
        // get all date
        for(Exercise temp:daterange){
            datelist.add(temp.getAddTime());

        }
        // remove duplicate to get unique date
        datelist = removeDuplicate(datelist);
        System.out.println("datelist : "+datelist);

        List<Integer> completelist = new ArrayList<>();
        List<Integer> incompletelist = new ArrayList<>();
        for(int i = 0; i <datelist.size();i++){
            for(Exercise temp:daterange){
                if(datelist.get(i).equals(temp.getAddTime())){
                    if (temp.isStates() == true){
                        completed +=1.0;
                    }
                    else{
                        imcompleted +=1.0;
                    }
                }
            }
            // get number of completed and incompleted in each date
            completelist.add(completed);
            incompletelist.add(imcompleted);
        }
        for(int i = 0; i <datelist.size();i++){
            complete.add(new BarEntry(i,completelist.get(i)));
            incomplete.add(new BarEntry(i,incompletelist.get(i)));
        }

        // set data
        BarDataSet bar1 = new BarDataSet(incomplete,"Incompleted");
        bar1.setColor(Color.RED);
        BarDataSet bar2 = new BarDataSet(complete,"Completed");
        bar2.setColor(Color.BLUE);

        BarData data = new BarData(bar1,bar2);
        binding.barchart.setData(data);
        data.setBarWidth(0.3f);
        List<String> xAxisValues = new ArrayList<>();
        for(int i = 0; i < datelist.size(); i++){

            xAxisValues.add(datelist.get(i));
            System.out.println("get i :"+datelist.get(i));
            System.out.println("xAxisValues: "+xAxisValues);
        }
        binding.barchart.getXAxis().setValueFormatter(new
                com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        binding.barchart.invalidate();
    }



    //remove duplicate date
    //reference:https://blog.csdn.net/jike11231/article/details/120991792
    public List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

}