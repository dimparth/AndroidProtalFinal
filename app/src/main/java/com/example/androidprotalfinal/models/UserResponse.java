package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String pass;
    @SerializedName("role")
    public String role;
}
