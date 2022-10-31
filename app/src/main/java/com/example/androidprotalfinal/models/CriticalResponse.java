package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

public class CriticalResponse {
    @SerializedName("category")
    public String Category;
    @SerializedName("locality")
    public String Locality;
    @SerializedName("count")
    public String DangerCount;
}
