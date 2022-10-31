package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.GridViewCustomAdapter;
import com.example.androidprotalfinal.models.GridViewModel;

import java.util.ArrayList;

public class AdminPortalActivity extends AppCompatActivity {

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        gridView = findViewById(R.id.idGridView);
        ArrayList<GridViewModel> gridViewModelArrayList = new ArrayList<>();

        gridViewModelArrayList.add(new GridViewModel(getString(R.string.view_critical), R.drawable.critical));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.edit_users), R.drawable.edit));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.logout), R.drawable.logout));

        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, gridViewModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            System.out.println(gridViewModelArrayList.get(i).getName());
            if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.view_critical))){
                startActivity(new Intent(AdminPortalActivity.this,CriticalIncidentsActivity.class));
            }else if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.edit_users))){
                startActivity(new Intent(AdminPortalActivity.this,UpdateUsersActivity.class));
            }else{
                startActivity(new Intent(AdminPortalActivity.this,MainActivity.class));
            }
        });
    }
}
