package com.example.car_wash.ui.tickets;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.car_wash.R;

public class TicketDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TICKET_ID = "ticket_id";

    private TicketDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        viewModel = new ViewModelProvider(this).get(TicketDetailViewModel.class);

        TextView textCar = findViewById(R.id.txtCar);
        TextView textDate = findViewById(R.id.txtDate);
        TextView textPrice = findViewById(R.id.txtPrice);

        String ticketId = getIntent().getStringExtra(EXTRA_TICKET_ID);

        viewModel.loadTicket(ticketId);

        viewModel.getTicket().observe(this, ticket -> {
            if (ticket != null) {
                textCar.setText(ticket.getCarId());
                textDate.setText(ticket.getDate());
                textPrice.setText("$" + ticket.getPrice());
            }
        });

        viewModel.getError().observe(this, err ->
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show());
    }
}
