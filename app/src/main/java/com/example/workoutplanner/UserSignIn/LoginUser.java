package com.example.workoutplanner.UserSignIn;

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

import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.R;
import com.example.workoutplanner.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forget;
    private EditText userEmail, userPassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        // set click listener for register button
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        // set click listener for forget password button
        forget = (TextView) findViewById(R.id.forgetPassword);
        forget.setOnClickListener(this);

        // set click listener for login button
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.mainProgressBar);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                //once clicked, go to register screen
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.login:
                userLogin();
                break;

            case R.id.forgetPassword:
                // Once clicked, go to forget password screen
                startActivity(new Intent(this, ForgetPassword.class));
                break;
        }
    }

    private void userLogin() {
        //get input information
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        //verification
        if(email.isEmpty()){
            userEmail.setError("Email is required!");
            userEmail.requestFocus();
            return;
        }

        //verification
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please enter a valid email address!");
            userEmail.requestFocus();
            return;
        }

        //verification
        if(password.isEmpty()){
            userPassword.setError("Password is required!");
            userPassword.requestFocus();
            return;
        }

        //firebase require at least 6 character
        if(password.length()<6){
            userPassword.setError("Password enter at least 6 character for password!");
            userPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //authorize email and password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //delete last two lines and uncomment to use email verification
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    updateUI(user);
                    progressBar.setVisibility(View.GONE);

                    //Pass the User to the rest fragments

                }
                else{
                    Toast.makeText(LoginUser.this, "Failed to login! Please check!",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }*/

    public void updateUI(FirebaseUser currentUser){
        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.putExtra("email",currentUser.getEmail());
        startActivity(newIntent);
    }

}