package com.example.car_wash.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.car_wash.R;
import com.example.car_wash.ui.car.CarListActivity;
import com.example.car_wash.ui.tickets.TicketListActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button goCars = findViewById(R.id.btnGoCars);
        Button goTickets = findViewById(R.id.btnGoTickets);

        goCars.setOnClickListener(v -> startActivity(new Intent(this, CarListActivity.class)));
        goTickets.setOnClickListener(v -> startActivity(new Intent(this, TicketListActivity.class)));
    }
}
