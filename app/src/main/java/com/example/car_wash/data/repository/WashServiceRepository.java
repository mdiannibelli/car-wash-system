package com.example.car_wash.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car_wash.data.firebase.FirebaseDataSource;
import com.example.car_wash.data.model.WashService;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WashServiceRepository {

    private final FirebaseFirestore db;
    private final String COLLECTION = "services";

    public WashServiceRepository() {
        this.db = FirebaseDataSource.getFirestore();
    }

    public LiveData<List<WashService>> getAllServices() {
        MutableLiveData<List<WashService>> servicesLiveData = new MutableLiveData<>();
        db.collection(COLLECTION).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<WashService> services = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    services.add(document.toObject(WashService.class));
                }
                servicesLiveData.setValue(services);
            } else {
                servicesLiveData.setValue(null);
            }
        });
        return servicesLiveData;
    }
}
