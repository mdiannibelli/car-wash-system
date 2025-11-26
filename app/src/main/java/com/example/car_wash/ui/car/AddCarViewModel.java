package com.example.car_wash.ui.car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.model.User;
import com.example.car_wash.data.repository.CarRepository;
import com.example.car_wash.data.repository.UserRepository;

import java.util.List;

public class AddCarViewModel extends ViewModel {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    private LiveData<List<User>> allUsers;

    public AddCarViewModel() {
        this.carRepository = new CarRepository();
        this.userRepository = new UserRepository();
        this.allUsers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<Boolean> addCar(Car car) {
        return carRepository.addCar(car);
    }
}
