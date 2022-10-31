package com.example.androidprotalfinal.implementation;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseOperations {
    public Task<String> getToken(){
        return FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                }).addOnFailureListener(task->{
                    Log.d("TAG ",task.getMessage());
                });
    }

    public void getImagePath(Uri selectedImageUri){
        FirebaseStorage.getInstance().getReference()
                .child("Photos/" + selectedImageUri.getLastPathSegment()).putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                });
    }
}
