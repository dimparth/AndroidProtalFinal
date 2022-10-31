package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.implementation.Extensions;
import com.example.androidprotalfinal.implementation.MyAdapter;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.CriticalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriticalIncidentsActivity extends AppCompatActivity {

    private ConsumeEndpoints apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical_incidents);
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
        getCriticalIncidents();
    }

    private void getCriticalIncidents() {
        try {
            ListView listView = findViewById(R.id.incidents_list);
            SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
            String s1 = sh.getString("token", "");
            Call<List<CriticalResponse>> callToken = apiInterface.getDanger("Bearer "+s1);
            callToken.enqueue(new Callback<List<CriticalResponse>>() {
                @Override
                public void onResponse(Call<List<CriticalResponse>> call, Response<List<CriticalResponse>> response) {
                    try {
                        Log.d("TAG", response.code() + "");
                        List<CriticalResponse> criticalResponses = response.body();
                        MyAdapter adapter = new MyAdapter(criticalResponses, CriticalIncidentsActivity.this);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            try {
                                System.out.println("The best : " + criticalResponses.get(position).Locality);
                                notifyUsersDialog(s1,criticalResponses.get(position).Category,criticalResponses.get(position).Locality);
                            }catch (Exception e){e.printStackTrace();}
                        });
                        Extensions.show(CriticalIncidentsActivity.this,"showing all user reports");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<List<CriticalResponse>> call, Throwable t) {
                    t.printStackTrace();
                    call.cancel();
                }
            });
        } catch (Exception e) {
            Extensions.show(this,e.getMessage());
        }
    }
    private void notifyUsersDialog(String token, String category, String locality){
        new AlertDialog.Builder(CriticalIncidentsActivity.this)
                .setTitle("Send notifications? ")
                .setMessage("Are you sure you want to notify users?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Call<String> sendNotifications = apiInterface.notifyUsers("Bearer "+token,
                            category,locality);
                    sendNotifications.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            System.out.println("SENT"+response.code());
                            Extensions.show(CriticalIncidentsActivity.this,"Notified all users in range!");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Extensions.show(CriticalIncidentsActivity.this,"Failed to notify users in range!");
                        }
                    });
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}