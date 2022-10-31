package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersListResponse {
    @SerializedName("users")
    public List<UserResponse> users;
}
