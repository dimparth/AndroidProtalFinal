package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.GridViewCustomAdapter;
import com.example.androidprotalfinal.models.GridViewModel;

import java.util.ArrayList;

public class UsersPortalActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        gridView = findViewById(R.id.idGridViewUsers);
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
        ArrayList<GridViewModel> gridViewModelArrayList = new ArrayList<>();

        gridViewModelArrayList.add(new GridViewModel(getString(R.string.upload_inc), R.drawable.upload));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.view_map), R.drawable.map));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.goto_statistics), R.drawable.stats));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.logout), R.drawable.logout));

        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, gridViewModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            System.out.println(gridViewModelArrayList.get(i).getName());
            if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.view_map))){
                startActivity(new Intent(UsersPortalActivity.this,MapsActivity.class));
            }else if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.goto_statistics))){
                startActivity(new Intent(UsersPortalActivity.this,StatsActivity.class));
            }else if (gridViewModelArrayList.get(i).getName().equals(getString(R.string.upload_inc))){
                startActivity(new Intent(UsersPortalActivity.this,UploadActivity.class));
            }
            else{
                startActivity(new Intent(UsersPortalActivity.this,MainActivity.class));
            }
        });
    }
}
