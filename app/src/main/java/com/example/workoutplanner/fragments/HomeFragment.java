package com.example.workoutplanner.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.api.OpenWeatherAPI;
import com.example.workoutplanner.databinding.HomeFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding addBinding;
    public HomeFragment(){}

    //weather
    private double lat;
    private double lon;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid ="9d89733781265480638a045b2dcc4acc";
    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the View for this fragment
        addBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        SharedPreferences sp= getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String fName=sp.getString("fName",null);
        String[] geocode = sp.getString("geocode",null).split(",");
        double lat = Double.parseDouble(geocode[0]);
        double lng = Double.parseDouble(geocode[1]);


        //set the user name
        addBinding.userName.setText(fName);

        //set the date
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        addBinding.time.setText("Today is: "+day+"/"+month);


        //set the weather
//        getWeatherDetails(view);
        OpenWeatherAPI openWeatherAPI = new OpenWeatherAPI();
        String url =openWeatherAPI.getWeatherAPI(-lat,lng);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public double temp;
            public double getTemp(){
                return this.temp;
            }
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    temp = jsonObjectMain.getDouble("temp") - 273.15;
                    addBinding.temp.setText("Today's temperature is : "+df.format(temp)+" ℃");
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}