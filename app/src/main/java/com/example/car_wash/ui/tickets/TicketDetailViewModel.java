package com.example.car_wash.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.car_wash.data.model.Ticket;
import com.example.car_wash.data.repository.TicketRepository;

public class TicketDetailViewModel extends ViewModel {

    private final TicketRepository ticketRepository = new TicketRepository();

    private final MutableLiveData<Ticket> ticketLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<Ticket> getTicket() { return ticketLiveData; }
    public LiveData<String> getError() { return errorLiveData; }

    public void loadTicket(String ticketId) {
        ticketRepository.getTicketById(ticketId,
                ticketLiveData::postValue,
                errorLiveData::postValue);
    }
}
