package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.implementation.Extensions;
import com.example.androidprotalfinal.implementation.FirebaseInstance;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.JWTTokenResponse;
import com.example.androidprotalfinal.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignupActivity extends AppCompatActivity {

    ConsumeEndpoints apiInterface;
    EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.usernameLogin_input);
        passwordInput = findViewById(R.id.passwordLogin_input);
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
    }
    public void signup(){
        try {
            User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString(), "user", FirebaseInstance.getInstance().firebaseOperations.getToken().getResult());
            Call<String> callAddUser = apiInterface.addUser(user.getUsername(), user.getPassword(), user.getRole(), user.getFcmToken());
            callAddUser.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        Log.d("TAG", response.code() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    call.cancel();
                }
            });

            getAuthenticationToken(new User(user.getUsername(),user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void login(View view){
        try {
            int flag = getIntent().getExtras().getInt("flag");
            if (flag == 2){
                signup();
                User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString());
            }else if (flag == 1) {
                User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString());
                getAuthenticationToken(user);
            }else{
                Extensions.show(this,"Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getAuthenticationToken(User user) {
        Call<JWTTokenResponse> callToken = apiInterface.getToken(user.getUsername(), user.getPassword());
        callToken.enqueue(new Callback<JWTTokenResponse>() {
            @Override
            public void onResponse(Call<JWTTokenResponse> call, Response<JWTTokenResponse> response) {
                try {
                    Log.d("TAG", response.code() + "");
                    JWTTokenResponse resource = response.body();
                    String token = resource.Token;
                    String username = resource.Username;
                    String role = resource.Role;
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor userData = sharedPreferences.edit();
                    userData.putString("token", token);
                    userData.putString("username", user.getUsername());
                    userData.apply();
                    SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);

                    String s1 = sh.getString("username", "");
                    System.out.println("TOKEN " + token);
                    System.out.println("PREFERENCE " + s1);
                    System.out.println("ROLE " + role);
                    if (role.equals("admin")) {
                        startActivity(new Intent(LoginSignupActivity.this, AdminPortalActivity.class));
                    } else {
                        startActivity(new Intent(LoginSignupActivity.this, UsersPortalActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JWTTokenResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}