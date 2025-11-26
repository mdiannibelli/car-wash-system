package com.example.car_wash.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car_wash.data.firebase.FirebaseDataSource;
import com.example.car_wash.data.model.Car;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CarRepository {

    private final FirebaseFirestore db;
    private final String COLLECTION = "cars";

    public CarRepository() {
        this.db = FirebaseDataSource.getFirestore();
    }

    public void getAllCars(Consumer<List<Car>> onSuccess, Consumer<String> onError) {
        db.collection(COLLECTION).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Car> cars = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    cars.add(document.toObject(Car.class));
                }
                onSuccess.accept(cars);
            } else {
                onError.accept(task.getException() != null ? task.getException().getMessage() : "Failed to load cars");
            }
        });
    }

    public LiveData<Boolean> addCar(Car car) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        db.collection(COLLECTION).add(car)
                .addOnSuccessListener(documentReference -> {
                    car.setId(documentReference.getId());
                    db.collection(COLLECTION).document(documentReference.getId()).set(car);
                    result.setValue(true);
                })
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}
