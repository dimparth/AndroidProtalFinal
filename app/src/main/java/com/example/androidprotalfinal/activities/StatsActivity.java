package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.StatisticsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {
    private ConsumeEndpoints apiInterface;
    private Gson gson;
    ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        simpleList = findViewById(R.id.simpleListView);
        getUsersCount();
    }

    private void getUsersCount() {
        SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
        String s1 = sh.getString("token", "");
        Call<StatisticsResponse> callUsers = apiInterface.getStatistics("Bearer " + s1);
        callUsers.enqueue(new Callback<StatisticsResponse>() {
            @Override
            public void onResponse(Call<StatisticsResponse> call, Response<StatisticsResponse> response) {
                try {
                    Log.d("TAG", response.code() + "");
                    StatisticsResponse resource = response.body();
                    ArrayList<String> list = new ArrayList<>();
                    System.out.println(gson.toJson(resource.incidentsByCategory));
                    list.add("Total Users: "+resource.usersCount);
                    list.add("Total number of Critical Incidents: "+resource.totalCount);
                    resource.incidentsByCategory.forEach(incident->list.add("Category: "+incident.category+"\n Count: "+incident.count
                    +"\n Date: "+incident.day));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StatsActivity.this, R.layout.layout_liststats, R.id.textView, list);
                    simpleList.setAdapter(arrayAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<StatisticsResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}