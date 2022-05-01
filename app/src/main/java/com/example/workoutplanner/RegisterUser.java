package com.example.workoutplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.workoutplanner.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText fName, lName, age, email, password;
    private TextView registerUser, banner;
    private ProgressBar progressBar;

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

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
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
        String regFPassword = password.getText().toString().trim();

        if(regFName.isEmpty()){
            fName.setError("First name required!");
            fName.requestFocus();
            return;
        }

        if(regLName.isEmpty()){
            lName.setError("Last name required!");
            lName.requestFocus();
            return;
        }

        if(regAge.isEmpty()){
            age.setError("Age name required!");
            age.requestFocus();
            return;
        }

        if(regEmail.isEmpty()){
            email.setError("Email name required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(regEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }

        if(regFPassword.isEmpty()){
            password.setError("Password name required!");
            password.requestFocus();
            return;
        }

        if(regFPassword.length()<6){
            password.setError("Password should have at least 6 characters!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(regEmail,regFPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(regFName,regLName,regAge,regEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"Successfully registered!",
                                                Toast.LENGTH_LONG).show();
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
}