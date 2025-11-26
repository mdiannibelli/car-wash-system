package com.example.car_wash.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;

public class CarListActivity extends AppCompatActivity {

    private CarViewModel viewModel;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        Button btnAddCar = findViewById(R.id.btnAddCar);
        btnAddCar.setOnClickListener(v -> startActivity(new Intent(this, AddCarActivity.class)));

        RecyclerView recycler = findViewById(R.id.recyclerCars);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter();
        recycler.setAdapter(carAdapter);

        viewModel = new ViewModelProvider(this).get(CarViewModel.class);

        viewModel.getCars().observe(this, cars -> {
            if (cars != null) {
                carAdapter.setCars(cars);
            }
        });

        viewModel.getError().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadCars();
    }
}
