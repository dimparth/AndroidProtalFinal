package com.example.androidprotalfinal.models;
import com.google.gson.annotations.SerializedName;


public class JWTTokenResponse {
    @SerializedName("token")
    public String Token;
    @SerializedName("username")
    public String Username;
    @SerializedName("role")
    public String Role;
}
