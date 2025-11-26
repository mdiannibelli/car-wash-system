package com.example.car_wash.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.car_wash.data.firebase.FirebaseDataSource;
import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.model.Ticket;
import com.example.car_wash.data.model.WashService;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TicketRepository {

    private final FirebaseFirestore db;
    private final String COLLECTION = "tickets";
    private final String CARS_COLLECTION = "cars";
    private final String SERVICES_COLLECTION = "services"; // Corrected collection name

    public TicketRepository() {
        db = FirebaseDataSource.getFirestore();
    }

    public LiveData<Boolean> addTicket(Ticket ticket) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        String id = db.collection(COLLECTION).document().getId();
        ticket.setId(id);

        db.collection(COLLECTION)
                .document(id)
                .set(ticket)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }

    public LiveData<Boolean> deleteTicket(String ticketId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        db.collection(COLLECTION).document(ticketId).delete()
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }

    public void getAllTickets(Consumer<List<Ticket>> onSuccess, Consumer<String> onError) {
        db.collection(COLLECTION)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        onError.accept("Error fetching tickets: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"));
                        return;
                    }

                    List<Ticket> tickets = task.getResult().toObjects(Ticket.class);
                    if (tickets.isEmpty()) {
                        onSuccess.accept(new ArrayList<>());
                        return;
                    }

                    List<Task<?>> enrichmentTasks = new ArrayList<>();
                    for (Ticket ticket : tickets) {
                        // Null-safe check for carId
                        if (ticket.getCarId() != null && !ticket.getCarId().isEmpty()) {
                            Task<DocumentSnapshot> carTask = db.collection(CARS_COLLECTION).document(ticket.getCarId()).get()
                                    .addOnSuccessListener(carDoc -> {
                                        if (carDoc.exists()) ticket.setCar(carDoc.toObject(Car.class));
                                    });
                            enrichmentTasks.add(carTask);
                        }

                        // Null-safe check for serviceId
                        if (ticket.getServiceId() != null && !ticket.getServiceId().isEmpty()) {
                            Task<DocumentSnapshot> serviceTask = db.collection(SERVICES_COLLECTION).document(ticket.getServiceId()).get()
                                    .addOnSuccessListener(serviceDoc -> {
                                        if (serviceDoc.exists()) ticket.setWashService(serviceDoc.toObject(WashService.class));
                                    });
                            enrichmentTasks.add(serviceTask);
                        }
                    }

                    Tasks.whenAll(enrichmentTasks)
                            .addOnSuccessListener(aVoid -> onSuccess.accept(tickets))
                            .addOnFailureListener(e -> onError.accept("Error enriching tickets: " + e.getMessage()));
                });
    }

    public void getTicketById(String ticketId, Consumer<Ticket> onSuccess, Consumer<String> onError) {
        db.collection(COLLECTION).document(ticketId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot == null || !documentSnapshot.exists()) {
                        onError.accept("Ticket not found.");
                        return;
                    }
                    Ticket ticket = documentSnapshot.toObject(Ticket.class);
                    if (ticket == null) {
                        onError.accept("Failed to parse ticket data.");
                        return;
                    }

                    // Null-safe enrichment
                    Task<DocumentSnapshot> carTask = Tasks.forResult(null);
                    if (ticket.getCarId() != null && !ticket.getCarId().isEmpty()) {
                        carTask = db.collection(CARS_COLLECTION).document(ticket.getCarId()).get();
                    }

                    Task<DocumentSnapshot> serviceTask = Tasks.forResult(null);
                    if (ticket.getServiceId() != null && !ticket.getServiceId().isEmpty()) {
                        serviceTask = db.collection(SERVICES_COLLECTION).document(ticket.getServiceId()).get();
                    }

                    Tasks.whenAllSuccess(carTask, serviceTask).continueWith(resultTask -> {
                        DocumentSnapshot carDoc = (DocumentSnapshot) resultTask.getResult().get(0);
                        DocumentSnapshot serviceDoc = (DocumentSnapshot) resultTask.getResult().get(1);

                        if (carDoc != null && carDoc.exists()) ticket.setCar(carDoc.toObject(Car.class));
                        if (serviceDoc != null && serviceDoc.exists()) ticket.setWashService(serviceDoc.toObject(WashService.class));
                        
                        onSuccess.accept(ticket);
                        return null;
                    }).addOnFailureListener(e -> onError.accept("Failed to enrich ticket data: " + e.getMessage()));
                })
                .addOnFailureListener(e -> onError.accept("Error fetching ticket: " + e.getMessage()));
    }

    public LiveData<Boolean> updateTicketStatus(String id, String status) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        db.collection(COLLECTION)
                .document(id)
                .update("status", status)
                .addOnSuccessListener(aVoid -> result.setValue(true))
                .addOnFailureListener(e -> result.setValue(false));
        return result;
    }
}
