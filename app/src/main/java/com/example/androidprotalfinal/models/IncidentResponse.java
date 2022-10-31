package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

public class IncidentResponse {
    @SerializedName("user")
    public String user;
    @SerializedName("comments")
    public String comments;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("dangerTimestamp")
    public String dangerTimestamp;
    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("dangerStatus")
    public String status;
    @SerializedName("category")
    public String category;
    @SerializedName("locality")
    public String locality;

}
