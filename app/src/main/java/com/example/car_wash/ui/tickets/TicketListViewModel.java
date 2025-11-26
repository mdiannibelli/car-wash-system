package com.example.car_wash.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.Ticket;
import com.example.car_wash.data.repository.TicketRepository;

import java.util.List;

public class TicketListViewModel extends ViewModel {

    private final TicketRepository ticketRepository = new TicketRepository();

    private final MutableLiveData<List<Ticket>> ticketsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public LiveData<List<Ticket>> getTickets() { return ticketsLiveData; }
    public LiveData<String> getError() { return errorLiveData; }
    public LiveData<Boolean> isLoading() { return loadingLiveData; }

    public void loadTickets() {
        loadingLiveData.setValue(true);
        ticketRepository.getAllTickets(
                tickets -> {
                    loadingLiveData.postValue(false);
                    ticketsLiveData.postValue(tickets);
                },
                error -> {
                    loadingLiveData.postValue(false);
                    errorLiveData.postValue(error);
                }
        );
    }

    public LiveData<Boolean> deleteTicket(String ticketId) {
        return ticketRepository.deleteTicket(ticketId);
    }
}

