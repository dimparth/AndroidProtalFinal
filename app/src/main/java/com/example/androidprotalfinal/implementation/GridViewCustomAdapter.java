package com.example.androidprotalfinal.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.models.GridViewModel;


import java.util.ArrayList;

public class GridViewCustomAdapter extends ArrayAdapter<GridViewModel> {
    public GridViewCustomAdapter(@NonNull Context context, ArrayList<GridViewModel> gridViewModelArrayList) {
        super(context, 0, gridViewModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listedView = convertView;
        if (listedView == null) {
            listedView = LayoutInflater.from(getContext()).inflate(R.layout.layout_gridview, parent, false);
        }
        GridViewModel gridViewModel = getItem(position);
        TextView text = listedView.findViewById(R.id.idTxt);
        ImageView image = listedView.findViewById(R.id.idImgView);

        text.setText(gridViewModel.getName());
        image.setImageResource(gridViewModel.getImgId());
        return listedView;
    }
}
