package com.example.workoutplanner.api;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class OpenWeatherAPI {

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid ="9d89733781265480638a045b2dcc4acc";
    DecimalFormat df = new DecimalFormat("#.##");

    public String getWeatherAPI(double reaLat,double reaLon){
        return  url+"?lat="+reaLat+"&lon="+reaLon+"&appid="+appid;
    }

    }

