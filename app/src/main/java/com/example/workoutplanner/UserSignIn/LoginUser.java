package com.example.workoutplanner.UserSignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forget;
    private EditText userEmail, userPassword;
    private Button login;

    private Button fblogin;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USER = "Users";
    private String token;
    private static final String EMAIL = "email";


    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);

        // set click listener for fb login button
        fblogin = (Button) findViewById(R.id.fblogin_button);
        fblogin.setOnClickListener(this);


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginUser.this, "Login Canceled",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginUser.this, "Failed to login! Please check!",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


        //facebookLogin();
        /*fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginManager.logInWithReadPermissions(
                        MainActivity.this,
                        Arrays.asList(
                                "email",
                                "public_profile",
                                "user_birthday"));
            }
        });
*/


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
                    token = user.getUid();
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
        String userEmail = currentUser.getEmail();
        SharedPreferences sp= LoginUser.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("email").getValue().equals(userEmail)){
                        editor.putString("uid", token);
                        editor.putString("email", ds.child("email").getValue(String.class));
                        editor.putString("lName", ds.child("lName").getValue(String.class));
                        editor.putString("address", ds.child("address").getValue(String.class));
                        editor.putString("age", ds.child("age").getValue(String.class));
                        editor.putString("gender", ds.child("gender").getValue(String.class));
                        editor.putString("zipcode", ds.child("zipcode").getValue(String.class));
                        editor.putString("fName", ds.child("fName").getValue(String.class));

                        String address = ds.child("address").getValue(String.class)+ds.child("zipcode").getValue(String.class);
                        LatLng lagLng = getLocationFromAddress(LoginUser.this, address);
                        String geocode = lagLng.toString();
                        editor.putString("geocode", geocode.replace("lat/lng: (", "").replace(")",""));
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(newIntent);

    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void facebookLogin()
    {

        loginManager
                = LoginManager.getInstance();
        callbackManager
                = CallbackManager.Factory.create();

        loginManager
                .registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {
                                GraphRequest request = GraphRequest.newMeRequest(

                                        loginResult.getAccessToken(),

                                        new GraphRequest.GraphJSONObjectCallback() {

                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response)
                                            {

                                                if (object != null) {
                                                    try {
                                                        String name = object.getString("name");
                                                        String email = object.getString("email");
                                                        String fbUserID = object.getString("id");

                                                        disconnectFromFacebook();

                                                        // do action after Facebook login success
                                                        // or call your API
                                                    }
                                                    catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString(
                                        "fields",
                                        "id, name, email, gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "
                                        + error.getMessage());
                            }
                        });
    }

    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        // add this line
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);

        super.onActivityResult(requestCode,
                resultCode,
                data);
    }



}