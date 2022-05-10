package com.example.workoutplanner.UserSignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.workoutplanner.R;
import com.example.workoutplanner.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;

    private EditText fName, lName, age, email, password, zipcode, address;
    private Spinner gender;
    private TextView registerUser, banner;
    private ProgressBar progressBar;
    private String regGenderSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();


        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.Register);
        registerUser.setOnClickListener(this);

        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        age = (EditText) findViewById(R.id.age);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        zipcode = (EditText) findViewById(R.id.zipcode);
        address = (EditText) findViewById(R.id.address);
        gender = (Spinner) findViewById(R.id.gender);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this , R.array.gender_spinner,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(spinnerAdapter);
        gender.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //once banner clicked, return to login screen
            case R.id.banner:
                startActivity(new Intent(this, LoginUser.class));
                break;
            case R.id.Register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String regFName = fName.getText().toString().trim();
        String regLName = lName.getText().toString().trim();
        String regAge = age.getText().toString().trim();
        String regEmail = email.getText().toString().trim();
        String regPassword = password.getText().toString().trim();
        String regZipcode = zipcode.getText().toString().trim();
        String regAddress = address.getText().toString().trim();
        String regGender = regGenderSelect;

        //verification
        if(regFName.isEmpty()){
            fName.setError("First name required!");
            fName.requestFocus();
            return;
        }

        //verification
        if(regLName.isEmpty()){
            lName.setError("Last name required!");
            lName.requestFocus();
            return;
        }

        //verification
        if(regAge.isEmpty()){
            age.setError("Age name required!");
            age.requestFocus();
            return;
        }

        //verification
        if(regEmail.isEmpty()){
            email.setError("Email name required!");
            email.requestFocus();
            return;
        }

        //verification
        if(!Patterns.EMAIL_ADDRESS.matcher(regEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }

        //verification
        if(regPassword.isEmpty()){
            password.setError("Password name required!");
            password.requestFocus();
            return;
        }

        //verification
        if(regPassword.length()<6){
            password.setError("Password should have at least 6 characters!");
            password.requestFocus();
            return;
        }

        //verification
        if(regAddress.isEmpty()){
            address.setError("Address required!");
            address.requestFocus();
            return;
        }

        //verification
        if(regZipcode.isEmpty()){
            zipcode.setError("Zipcode required!");
            zipcode.requestFocus();
            return;
        }


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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        regGenderSelect = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}