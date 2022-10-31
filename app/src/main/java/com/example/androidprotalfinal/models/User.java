package com.example.androidprotalfinal.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private String username;
    private String password;
    private String role;
    private String fcmToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String role, String fcmToken) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fcmToken = fcmToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
