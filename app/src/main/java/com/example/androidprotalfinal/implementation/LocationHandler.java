package com.example.androidprotalfinal.implementation;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHandler {
    private Location mLastLocation;
    private final LocationManager mLocationManager;
    private static final int LOCATION_REFRESH_TIME = 1000;
    private static final int LOCATION_REFRESH_DISTANCE = 1;

    public LocationHandler(LocationManager mLocationManager) {
        this.mLocationManager = mLocationManager;
    }

    @SuppressLint("MissingPermission")
    public Location GetLocation() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);
        if (mLastLocation == null)
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationWIFIListener);
        if (mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null)
            return mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        else return mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
            System.out.println("onProviderEnabled");
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private final LocationListener mLocationWIFIListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            System.out.println("OnWifiLocChanged");
            mLastLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
