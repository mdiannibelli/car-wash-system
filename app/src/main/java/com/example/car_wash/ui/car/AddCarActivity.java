package com.example.car_wash.ui.car;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Car;
import com.example.car_wash.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private AddCarViewModel addCarViewModel;
    private EditText etPlate, etBrand, etModel, etColor;
    private Spinner spinnerOwner;
    private Button btnSaveCar;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        etPlate = findViewById(R.id.etCarPlate);
        etBrand = findViewById(R.id.etCarBrand);
        etModel = findViewById(R.id.etCarModel);
        etColor = findViewById(R.id.etCarColor);
        spinnerOwner = findViewById(R.id.spinnerOwner);
        btnSaveCar = findViewById(R.id.btnSaveCar);

        addCarViewModel = new ViewModelProvider(this).get(AddCarViewModel.class);

        addCarViewModel.getAllUsers().observe(this, users -> {
            if (users != null) {
                userList = users;
                List<String> userNames = new ArrayList<>();
                for (User user : users) {
                    userNames.add(user.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOwner.setAdapter(adapter);
            }
        });

        btnSaveCar.setOnClickListener(v -> saveCar());
    }

    private void saveCar() {
        String plate = etPlate.getText().toString().trim();
        String brand = etBrand.getText().toString().trim();
        String model = etModel.getText().toString().trim();
        String color = etColor.getText().toString().trim();

        if (plate.isEmpty() || brand.isEmpty() || model.isEmpty() || color.isEmpty() || spinnerOwner.getSelectedItemPosition() < 0) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User selectedOwner = userList.get(spinnerOwner.getSelectedItemPosition());

        Car newCar = new Car(null, plate, brand, model, color, selectedOwner);

        addCarViewModel.addCar(newCar).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Car added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add car", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
