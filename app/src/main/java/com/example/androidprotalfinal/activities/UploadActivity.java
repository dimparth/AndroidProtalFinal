package com.example.androidprotalfinal.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.APIClient;
import com.example.androidprotalfinal.implementation.Extensions;
import com.example.androidprotalfinal.implementation.FirebaseInstance;
import com.example.androidprotalfinal.implementation.LocationHandler;
import com.example.androidprotalfinal.interfaces.ConsumeEndpoints;
import com.example.androidprotalfinal.models.CriticalResponse;
import com.example.androidprotalfinal.models.IncidentResponse;
import com.example.androidprotalfinal.models.IncidentsList;
import com.example.androidprotalfinal.models.Information;
import com.example.androidprotalfinal.models.JWTTokenResponse;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    private ConsumeEndpoints apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Spinner spinner = findViewById(R.id.issues_spinner);
        spinner.setAdapter(Extensions.populateSpinner(this, R.array.categories_array));
        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            return;
        }
        apiInterface = APIClient.getClient().create(ConsumeEndpoints.class);
    }

    public void uploadImage(View view) {
        try {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            launchUploadActivity.launch(i);
        } catch (Exception e) {
            Extensions.show(this, "error");
        }
    }

    ActivityResultLauncher<Intent> launchUploadActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        try {
                            Uri selectedImageUri = data.getData();
                            FirebaseInstance.getInstance().firebaseOperations.getImagePath(selectedImageUri);
                            Information information = getInformation(selectedImageUri.getLastPathSegment());
                            SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
                            String s1 = sh.getString("token", "");
                            System.out.println("PREFERENCE " + s1);
                            Call<String> addIncident = apiInterface.registerIncident("Bearer " + s1,
                                    information.getUsername(),
                                    information.getComments(),
                                    information.getLatitude(),
                                    information.getLongitude(),
                                    information.getTimestamp(),
                                    information.getImagePath(),
                                    information.getStatus(),
                                    information.getCategory(),
                                    information.getLocality());
                            addIncident.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    try {
                                        Log.d("TAG", response.code() + "");
                                        Extensions.show(UploadActivity.this,"Uploaded! Viewing map!");
                                        startActivity(new Intent(UploadActivity.this,MapsActivity.class));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Extensions.show(UploadActivity.this,e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    t.printStackTrace();
                                    call.cancel();
                                }
                            });



                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Extensions.show(UploadActivity.this,"error");
                        }
                    } else {
                        Extensions.show(this, "Error with input data");
                    }
                }
            });

    private Information getInformation(String imgPath) {
        Information information = new Information();
        Spinner spinner = findViewById(R.id.issues_spinner);
        EditText description = findViewById(R.id.inputDescription);
        LocationManager LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;
        try {
            Location lastLocation = new LocationHandler(LocationManager).GetLocation();
            addressList = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
            information.setTimestamp(Extensions.getDate());
            information.setUsername(getSharedPreferences("UserData", MODE_PRIVATE).getString("username", ""));
            information.setCategory(spinner.getSelectedItem().toString());
            information.setComments(description.getText().toString());
            information.setLatitude(String.valueOf(lastLocation.getLatitude()));
            information.setLongitude(String.valueOf(lastLocation.getLongitude()));
            information.setImagePath(imgPath);
            information.setLocality(addressList.get(0).getLocality());
        } catch (Exception e) {
            Extensions.show(this, "error");
        }
        return information;
    }

}
