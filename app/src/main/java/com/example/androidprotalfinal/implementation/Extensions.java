package com.example.androidprotalfinal.implementation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.androidprotalfinal.models.IncidentResponse;
import com.example.androidprotalfinal.models.Information;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Extensions {
    public static MarkerOptions setMarkerOptions(IncidentResponse information) {
        return new MarkerOptions().
                position(getLatLng(information.latitude, information.longitude))
                .snippet(information.comments+ " " + information.category)
                .title(information.user);
    }
    public static ArrayAdapter<CharSequence> populateSpinner(final FragmentActivity activity, int list) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
    public static String getDate() {
        return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
                .format(new Date(Calendar.getInstance()
                        .getTimeInMillis()));
    }
    public static void show(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static LatLng getLatLng(String latitude, String longitude) {
        return new LatLng(Double.parseDouble(latitude),
                Double.parseDouble(longitude));
    }

    public static MarkerOptions setMarkerOptions(Information information) {
        return new MarkerOptions().
                position(getLatLng(information.getLatitude(), information.getLongitude()))
                .snippet(information.toString())
                .title(information.getUsername());
    }
}
