package com.example.androidprotalfinal.activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.databinding.ActivityMapsBinding;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.implementation.Extensions;
import com.example.androidprotalfinal.implementation.InformationWindow;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.IncidentResponse;
import com.example.androidprotalfinal.models.IncidentsList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ConsumeEndpoints apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
    }
    public void menu (View view){
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
            String s1 = sh.getString("token", "");
            Call<IncidentsList> getIncidents = apiInterface.getAllIncidents("Bearer " + s1);
            getIncidents.enqueue(new Callback<IncidentsList>() {
                @Override
                public void onResponse(Call<IncidentsList> call, Response<IncidentsList> response) {
                    IncidentsList incidentsList = new IncidentsList();
                    incidentsList.users = response.body().users;
                    incidentsList.users.forEach(x -> {
                        mMap.setInfoWindowAdapter(new InformationWindow(MapsActivity.this, incidentsList.users));
                        mMap.addMarker(Extensions.setMarkerOptions(x));
                    });
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLng(new LatLng(Double.parseDouble(incidentsList.users
                                    .stream()
                                    .skip(incidentsList.users
                                            .size() - 1)
                                    .findFirst()
                                    .get()
                                    .latitude),
                                    Double.parseDouble(incidentsList.users
                                            .stream()
                                            .skip(incidentsList.users.
                                                    size() - 1)
                                            .findFirst()
                                            .get()
                                            .longitude))));
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    Extensions.show(MapsActivity.this,"Map initialized! Viewing all incidents!");
                }

                @Override
                public void onFailure(Call<IncidentsList> call, Throwable t) {

                    Extensions.show(MapsActivity.this,t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Extensions.show(MapsActivity.this,e.getMessage());
        }
    }
}