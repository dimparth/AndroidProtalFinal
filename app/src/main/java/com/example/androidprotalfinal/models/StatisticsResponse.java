package com.example.androidprotalfinal.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticsResponse {
    @SerializedName("totalCount")
    public String totalCount;
    @SerializedName("usersCount")
    public String usersCount;
    @SerializedName("incidentsCategories")
    public List<IncidentsCategoryResponse> incidentsByCategory;
}
