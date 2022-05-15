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
import android.widget.Toast;

import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText email;
    private Button reset;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = (EditText) findViewById(R.id.forgetEmail);
        reset = (Button) findViewById(R.id.reset);
        progressBar = (ProgressBar) findViewById(R.id.forgetProgressBar);

        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String userEmail = email.getText().toString().trim();

        if(userEmail.isEmpty()){
            email.setError("Please enter a email address!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please enter a valid email address!");
            email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // send email to reset password
        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Verification sent, please check your email!",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPassword.this, LoginUser.class));
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(ForgetPassword.this, "Invalid email address, please try again!",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}