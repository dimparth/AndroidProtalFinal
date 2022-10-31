package com.example.androidprotalfinal.models;

public class Information {
    private String Username;
    private String Longitude;
    private String Latitude;
    private String Comments;
    private String Timestamp;
    private String Category;
    private String ImagePath;
    private String Status;
    private String Locality;

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getStatus() {
        return "";
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Information{" +
                "DisplayName='" + Username + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", Description='" + Comments + '\'' +
                ", Timestamp='" + Timestamp + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }
}
