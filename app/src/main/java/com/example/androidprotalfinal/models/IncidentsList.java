package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentsList {
    @SerializedName("users")
    public List<IncidentResponse> users;
}
