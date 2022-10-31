package com.example.androidprotalfinal.implementation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidprotalfinal.R;
import com.example.androidprotalfinal.models.IncidentResponse;
import com.example.androidprotalfinal.models.IncidentsList;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InformationWindow implements GoogleMap.InfoWindowAdapter{
    private final View mWindow;
    private Context mContext;
    private final List<IncidentResponse> informationList;


    public InformationWindow(Context context, List<IncidentResponse> informationList) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.place_information, null);
        this.informationList = informationList;
    }

    private void DisplayInfo(final Marker marker, final View view) {
        TextView title = view.findViewById(R.id.title);
        TextView textView = view.findViewById(R.id.info);
        title.setText(marker.getTitle());
        textView.setText(marker.getSnippet());
        String lat = String.valueOf(marker.getPosition().latitude);
        informationList.forEach(information -> {
            try {
                if (lat.equals(information.latitude)) {
                    FirebaseStorage.getInstance().getReference().child("Photos/" + information.imagePath).getDownloadUrl().addOnSuccessListener(uri -> {
                        ImageView imgView = view.findViewById(R.id.place_image);
                        System.out.println(uri.getPath());
                        Picasso.get().load(uri).resize(300, 300).into(imgView);
                    }).addOnFailureListener(exception -> Extensions.show(mContext, exception.getMessage()));
                }
            }catch (Exception e){e.printStackTrace();}
        });

    }

    @Override
    public View getInfoWindow(Marker marker) {
        DisplayInfo(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        DisplayInfo(marker, mWindow);
        return mWindow;
    }
}
