package com.example.workoutplanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.workoutplanner.R;
import com.example.workoutplanner.UserSignIn.LoginUser;
import com.example.workoutplanner.UserSignIn.RegisterUser;
import com.example.workoutplanner.data.User;
import com.example.workoutplanner.databinding.HomeFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding addBinding;
    public HomeFragment(){}

    //weather
    private double lat = -37.840935;
    private double lon = 144.946457;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid ="9d89733781265480638a045b2dcc4acc";
    DecimalFormat df = new DecimalFormat("#.##");


    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        SharedPreferences sp= getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String fName=sp.getString("fName",null);


        //set the user name
        addBinding.userName.setText(fName);

        //set the date
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        addBinding.time.setText("Today is: "+day+"/"+month);


        //set the weather
        getWeatherDetails(view);
        return view;

        //button
        button=findViewById(R.id.upLoad_tofirebase);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upLoadToFirebase();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("Hello, World!");
            }
        });


    }

    //upLoadToFirebase
    private void upLoadToFirebase() {
        String regFName = fName.getText().toString().trim().toLowerCase();
        String regLName = lName.getText().toString().trim().toLowerCase();
        String regAge = age.getText().toString().trim();
        String regEmail = email.getText().toString().trim();
        String regPassword = password.getText().toString().trim();
        String regZipcode = zipcode.getText().toString().trim();
        String regAddress = address.getText().toString().trim();
        String regGender = regGenderSelect;


        //create user
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(regEmail,regPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(regFName,regLName,regAge,regEmail, regGender, regZipcode, regAddress);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this,"Successfully registered!",
                                                        Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisterUser.this, LoginUser.class));
                                                progressBar.setVisibility(View.GONE);

                                            }
                                            else{
                                                Toast.makeText(RegisterUser.this,"Failed to register! Try again!",
                                                        Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });
                        }
                        else{
                            Toast.makeText(RegisterUser.this,"Failed to register!",
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                });
    }






    //get the weather
    public void getWeatherDetails(View view){
        String tempUrl = "";
        Double reaLat = -37.840935;
        Double reaLon = 144.946457;


        if (reaLat == null || reaLon == null){
            addBinding.userName.setText("Can not capture the location");
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
                        addBinding.temp.setText("Today's temperature is : "+df.format(temp)+" ℃");
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
        addBinding = null;
    }
}