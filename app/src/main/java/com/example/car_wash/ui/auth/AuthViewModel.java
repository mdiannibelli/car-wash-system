package com.example.car_wash.ui.auth;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.User;
import com.example.car_wash.data.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public LiveData<User> getUser() { return userLiveData; }
    public LiveData<String> getError() { return errorLiveData; }
    public LiveData<Boolean> isLoading() { return loadingLiveData; }

    public void login(String email, String password) {
        loadingLiveData.setValue(true);
        authRepository.login(email, password,
                user -> {
                    loadingLiveData.postValue(false);
                    userLiveData.postValue(user);
                },
                error -> {
                    loadingLiveData.postValue(false);
                    errorLiveData.postValue(error);
                });
    }

    public void register(String name, String phone, String email, String password) {
        loadingLiveData.setValue(true);
        authRepository.register(name, phone, email, password,
                user -> {
                    loadingLiveData.postValue(false);
                    userLiveData.postValue(user);
                },
                error -> {
                    loadingLiveData.postValue(false);
                    errorLiveData.postValue(error);
                });
    }
}
