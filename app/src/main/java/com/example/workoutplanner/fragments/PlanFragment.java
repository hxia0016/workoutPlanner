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
import java.util.Calendar;
import java.util.List;

public class PlanFragment extends Fragment {
    private PlanViewModel model;
    private PlanFragmentBinding binding;
    public PlanFragment(){}

    private RecyclerView.LayoutManager layoutManager;
    private List<Exercise> exercises;
    private RecyclerViewAdapter adapter;

    //weather
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid ="9d89733781265480638a045b2dcc4acc";
    DecimalFormat df = new DecimalFormat("#.##");

    private Double lat;
    private Double lng;

    private String eName;
    private String eDuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = PlanFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //MOVE TO HOME SCREEN
//        SharedPreferences sp= this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//        binding.userName.setText(sp.getString("fName","Not found"));
//        try{
//            String[] geocode = sp.getString("geocode",null).split(",");
//            lat = Double.parseDouble(geocode[0]);
//            lng = Double.parseDouble(geocode[1]);;
//        }catch (Exception e){
//            lat = -37.840935;
//            lng = 144.946457;
//        }
        //set the date
//        Calendar calendar = Calendar.getInstance();
//        int month = calendar.get(Calendar.MONTH)+1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        binding.time.setText("Today is: "+day+"/"+month);
        //set the weather
// getWeatherDetails(view, lat, lng);


        //The recycle view
        exercises = new ArrayList<Exercise>();
        exercises = Exercise.createContactsList();
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
                if (!eName.isEmpty() || !eDuration.isEmpty()) {
                    int duration=new Integer(eDuration.split(" ")[0]).intValue();
                    saveData(eName, duration);
                }
            }
        });


        //Get the spinner option
        binding.eName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] exerciseOptions = getResources().getStringArray(R.array.exercisespinnerclass);
                eName = exerciseOptions[pos];
//                Toast.makeText(getActivity(),eName+"", Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(getActivity(),eDuration+"", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        Toast.makeText(getActivity(),eDuration+"", Toast.LENGTH_SHORT).show();




        return view;
    }


    private void saveData(String exercise, int duration) {
        Exercise ex = new Exercise(exercise, duration,false);
        exercises.add(ex);
        adapter.addUnits(exercises);
    }


    //get the weather
//    public void getWeatherDetails(View view, Double lat, Double lng){
//        String tempUrl = "";
//        if (lat == null || lng == null){
//            binding.userName.setText("Can not capture the location");
//        }else {
//            tempUrl = url+"?lat="+lat+"&lon="+lng+"&appid="+appid;
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    //Log.d("response",response);
//                    String output = "";
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
//                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
//                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
//                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
//                        System.out.println(temp);
//                        binding.temp.setText("Today temperature: "+df.format(temp)+"°C");
//                        //Log.d("response",df.format(temp));
//
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();
//                }
//            });
//            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//            requestQueue.add(stringRequest);
//        }
//    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}