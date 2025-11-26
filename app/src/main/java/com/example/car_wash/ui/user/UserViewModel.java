package com.example.car_wash.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.User;
import com.example.car_wash.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = userRepository.getAllUsers();
        }
        return users;
    }
}
