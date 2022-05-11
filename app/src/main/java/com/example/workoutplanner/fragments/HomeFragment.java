package com.example.workoutplanner.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.workoutplanner.UserSignIn.LoginUser;
import com.example.workoutplanner.adapter.RecyclerViewAdapter;
import com.example.workoutplanner.data.viewModel.PlanViewModel;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.example.workoutplanner.model.Exercies;
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

public class HomeFragment extends Fragment {
    private PlanViewModel model;
    private HomeFragmentBinding binding;
    public HomeFragment(){}

    private RecyclerView.LayoutManager layoutManager;
    private List<Exercies> exercises;
    private RecyclerViewAdapter adapter;

    //weather
    private double lat = -37.840935;
    private double lon = 144.946457;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid ="9d89733781265480638a045b2dcc4acc";
    DecimalFormat df = new DecimalFormat("#.##");




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sp= this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        binding.userName.setText(sp.getString("fName","Not found"));



        //set the date
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.time.setText("Today is: "+day+"/"+month);


        //set the weather
        getWeatherDetails(view);


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

//        binding.addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String exercise = binding.eName.getText().toString().trim();
//                String sduration= binding.eDuration.getText().toString().trim();
//                if (!exercise.isEmpty() || !sduration.isEmpty()) {
//                    int duration=new Integer(sduration).intValue();
//                    saveData(exercise, duration);
//                }
//            }
//        });



        return view;
    }


    private void saveData(String exercise, int duration) {
        Exercies ex = new Exercies(exercise, duration);
        exercises.add(ex);
        adapter.addUnits(exercises);
    }


    //get the weather
    public void getWeatherDetails(View view){
        String tempUrl = "";
        Double reaLat = -37.840935;
        Double reaLon = 144.946457;


        if (reaLat == null || reaLon == null){
            binding.userName.setText("Can not capture the location");
        }else {
            tempUrl = url+"?lat="+reaLat+"&lon="+reaLon+"&appid="+appid;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d("response",response);
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        System.out.println(temp);
                        binding.temp.setText("Today temperature: "+df.format(temp)+"°C");
                        //Log.d("response",df.format(temp));

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}