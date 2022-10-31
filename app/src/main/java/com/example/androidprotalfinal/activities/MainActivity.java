package com.example.androidprotalfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.implementation.GridViewCustomAdapter;
import com.example.androidprotalfinal.models.GridViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.idMainGridView);
        ArrayList<GridViewModel> gridViewModelArrayList = new ArrayList<>();

        gridViewModelArrayList.add(new GridViewModel(getString(R.string.login_btn_text), R.drawable.login));
        gridViewModelArrayList.add(new GridViewModel(getString(R.string.signup_btn_text), R.drawable.signup));

        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, gridViewModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.login_btn_text))){
                intent.putExtra("flag",1);
                startActivity(intent);
            }else if(gridViewModelArrayList.get(i).getName().equals(getString(R.string.signup_btn_text))){
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
    }
}
