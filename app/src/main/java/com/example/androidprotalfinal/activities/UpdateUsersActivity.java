package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.implementation.Extensions;
import com.example.androidprotalfinal.implementation.UsersAdapter;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.CriticalResponse;
import com.example.androidprotalfinal.models.User;
import com.example.androidprotalfinal.models.UserResponse;
import com.example.androidprotalfinal.models.UsersListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUsersActivity extends AppCompatActivity {

    private ConsumeEndpoints apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
        getUsers();
    }
    private void getUsers(){
        try{
            ListView listView = findViewById(R.id.users_list);
            SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
            String s1 = sh.getString("token", "");
            Call<UsersListResponse> callUsers = apiInterface.getUsers("Bearer "+s1);
            callUsers.enqueue(new Callback<UsersListResponse>() {
                @Override
                public void onResponse(Call<UsersListResponse> call, Response<UsersListResponse> response) {
                    Log.d("TAG", response.code() + "");
                    UsersListResponse users = response.body();
                    UsersAdapter usersAdapter = new UsersAdapter(users,UpdateUsersActivity.this);
                    listView.setAdapter(usersAdapter);
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        try {
                            System.out.println(" " + users.users.get(position).username);
                            updateUserDialog(users.users.get(position).username, s1);

                        }catch (Exception e){e.printStackTrace();}
                    });
                }

                @Override
                public void onFailure(Call<UsersListResponse> call, Throwable t) {

                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void updateUserDialog(String username, String token){
        new AlertDialog.Builder(UpdateUsersActivity.this)
                .setTitle("Update user: " + username)
                .setMessage("Are you sure you want to make this user an admin?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Call<String> sendNotifications = apiInterface.updateUserRole("Bearer "+token,
                            username);
                    sendNotifications.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            System.out.println("SENT"+response.code());
                            Extensions.show(UpdateUsersActivity.this, "Updated user's "+username+" role to admin.");
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Extensions.show(UpdateUsersActivity.this, "Failed to update "+username+" role to admin.");
                        }
                    });
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}