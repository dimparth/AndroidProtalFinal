package com.example.androidprotalfinal.implementation;

public class FirebaseInstance {
    private static FirebaseInstance instance;
    public FirebaseOperations firebaseOperations;

    private FirebaseInstance() {
        firebaseOperations = new FirebaseOperations();
    }
    public static FirebaseInstance getInstance(){
        if (instance == null)
            instance = new FirebaseInstance();

        return instance;
    }
}
