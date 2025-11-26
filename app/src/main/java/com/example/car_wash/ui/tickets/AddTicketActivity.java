package com.example.car_wash.ui.tickets;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.model.Ticket;
import com.example.car_wash.data.model.WashService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTicketActivity extends AppCompatActivity {

    private AddTicketViewModel viewModel;
    private Spinner spinnerCar, spinnerService;
    private TextView tvPrice;
    private Button btnSaveTicket;

    private List<Car> carList = new ArrayList<>();
    private List<WashService> serviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        spinnerCar = findViewById(R.id.spinnerCar);
        spinnerService = findViewById(R.id.spinnerService);
        tvPrice = findViewById(R.id.tvServicePrice);
        btnSaveTicket = findViewById(R.id.btnSaveTicket);

        viewModel = new ViewModelProvider(this).get(AddTicketViewModel.class);

        setupCarSpinner();
        setupServiceSpinner();

        btnSaveTicket.setOnClickListener(v -> saveTicket());
    }

    private void setupCarSpinner() {
        viewModel.getAllCars().observe(this, cars -> {
            if (cars != null) {
                carList = cars;
                List<String> carPlates = new ArrayList<>();
                for (Car car : cars) {
                    carPlates.add(car.getPlate());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carPlates);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCar.setAdapter(adapter);
            }
        });
    }

    private void setupServiceSpinner() {
        viewModel.getAllServices().observe(this, services -> {
            if (services != null) {
                serviceList = services;
                List<String> serviceNames = new ArrayList<>();
                for (WashService service : services) {
                    serviceNames.add(service.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerService.setAdapter(adapter);
            }
        });

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!serviceList.isEmpty()) {
                    WashService selectedService = serviceList.get(position);
                    tvPrice.setText(String.format(Locale.US, "Price: $%.2f", selectedService.getPrice()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvPrice.setText("Price: $0.00");
            }
        });
    }

    private void saveTicket() {
        if (carList.isEmpty() || serviceList.isEmpty()) {
            Toast.makeText(this, "Please wait for data to load.", Toast.LENGTH_SHORT).show();
            return;
        }

        Car selectedCar = carList.get(spinnerCar.getSelectedItemPosition());
        WashService selectedService = serviceList.get(spinnerService.getSelectedItemPosition());
        String employeeId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Ticket newTicket = new Ticket(
                null, // ID is set by the repository
                selectedCar.getId(),
                selectedService.getId(),
                employeeId,
                new Date(), // Current timestamp
                selectedService.getPrice(),
                "Pending"
        );

        viewModel.addTicket(newTicket).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Ticket created successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to create ticket.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
