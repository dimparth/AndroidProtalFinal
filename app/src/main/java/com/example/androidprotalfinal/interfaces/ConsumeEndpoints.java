package com.example.androidprotalfinal.interfaces;

import com.example.androidprotalfinal.models.CriticalResponse;
import com.example.androidprotalfinal.models.IncidentsList;
import com.example.androidprotalfinal.models.JWTTokenResponse;
import com.example.androidprotalfinal.models.StatisticsResponse;
import com.example.androidprotalfinal.models.UsersListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ConsumeEndpoints {

    @FormUrlEncoded
    @POST("users/authenticate")
    Call<JWTTokenResponse> getToken(@Field("Username") String username, @Field("Pass") String pass);


    @FormUrlEncoded
    @POST("users")
    Call<String> addUser(@Field("Username") String username, @Field("Pass") String pass,@Field("role") String role, @Field("FcmToken") String fcmToken);

    @GET("users")
    Call<UsersListResponse> getUsers(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("danger")
    Call<String> registerIncident(@Header("Authorization") String token,
                                  @Field("Username") String username, @Field("Comments") String comments,
                                  @Field("Latitude") String latitude, @Field("Longitude") String longitude,
                                  @Field("DangerTimestamp") String timestamp, @Field("ImagePath") String imagePath,
                                  @Field("DangerStatus") String status, @Field("Category") String category,
                                  @Field("Locality") String locality);
    @FormUrlEncoded
    @POST("danger/notify")
    Call<String> notifyUsers(@Header("Authorization") String token, @Field("Category") String category, @Field("Locality") String locality);

    @FormUrlEncoded
    @POST("users/update")
    Call<String> updateUserRole(@Header("Authorization") String token,@Field("username") String username);

    @GET("danger/critical")
    Call<List<CriticalResponse>> getDanger(@Header("Authorization") String token);

    @GET("danger")
    Call<IncidentsList> getAllIncidents(@Header("Authorization") String token);
    @GET("danger/statistics")
    Call<StatisticsResponse> getStatistics(@Header("Authorization") String token);
}
