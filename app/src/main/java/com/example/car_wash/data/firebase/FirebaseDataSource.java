package com.example.car_wash.data.firebase;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDataSource {
    private static FirebaseAuth authInstance;
    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firestoreInstance;

    public static FirebaseAuth getAuth() {
        if (authInstance == null) {
            authInstance = FirebaseAuth.getInstance();
        }
        return authInstance;
    }

    public static FirebaseFirestore getFirestore() {
        if (firestoreInstance == null) {
            firestoreInstance = FirebaseFirestore.getInstance();
        }
        return firestoreInstance;
    }
}

