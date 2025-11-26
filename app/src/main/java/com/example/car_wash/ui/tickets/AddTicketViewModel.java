package com.example.car_wash.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.model.Ticket;
import com.example.car_wash.data.model.WashService;
import com.example.car_wash.data.repository.CarRepository;
import com.example.car_wash.data.repository.TicketRepository;
import com.example.car_wash.data.repository.WashServiceRepository;

import java.util.List;

public class AddTicketViewModel extends ViewModel {

    private final CarRepository carRepository;
    private final WashServiceRepository washServiceRepository;
    private final TicketRepository ticketRepository;

    private final MutableLiveData<List<Car>> carsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public AddTicketViewModel() {
        this.carRepository = new CarRepository();
        this.washServiceRepository = new WashServiceRepository();
        this.ticketRepository = new TicketRepository();
        loadCars();
    }

    private void loadCars() {
        carRepository.getAllCars(
                cars -> carsLiveData.postValue(cars),
                error -> errorLiveData.postValue(error)
        );
    }

    public LiveData<List<Car>> getAllCars() {
        return carsLiveData;
    }

    public LiveData<List<WashService>> getAllServices() {
        return washServiceRepository.getAllServices();
    }

    public LiveData<Boolean> addTicket(Ticket ticket) {
        return ticketRepository.addTicket(ticket);
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}
