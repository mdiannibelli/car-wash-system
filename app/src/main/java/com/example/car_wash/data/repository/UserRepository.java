package com.example.car_wash.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car_wash.data.firebase.FirebaseDataSource;
import com.example.car_wash.data.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final FirebaseFirestore db;
    private final String COLLECTION = "users";

    public UserRepository() {
        db = FirebaseDataSource.getFirestore();
    }

    public LiveData<List<User>> getAllUsers() {
        MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
        db.collection(COLLECTION).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    users.add(document.toObject(User.class));
                }
                usersLiveData.setValue(users);
            } else {
                // Handle the error, e.g., post null or an empty list
                usersLiveData.setValue(null);
            }
        });
        return usersLiveData;
    }

    public LiveData<Boolean> saveUser(User user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection(COLLECTION)
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    public LiveData<User> getUser(String uid) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();

        db.collection(COLLECTION).document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        userLiveData.setValue(snapshot.toObject(User.class));
                    } else {
                        userLiveData.setValue(null);
                    }
                });

        return userLiveData;
    }
}
