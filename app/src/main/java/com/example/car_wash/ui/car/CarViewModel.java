package com.example.car_wash.ui.car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.repository.CarRepository;

import java.util.List;

public class CarViewModel extends ViewModel {

    private final CarRepository carRepository = new CarRepository();

    private final MutableLiveData<List<Car>> carsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<List<Car>> getCars() { return carsLiveData; }
    public LiveData<String> getError() { return errorLiveData; }

    public void loadCars() {
        carRepository.getAllCars(
                carsLiveData::postValue,
                errorLiveData::postValue
        );
    }
}
