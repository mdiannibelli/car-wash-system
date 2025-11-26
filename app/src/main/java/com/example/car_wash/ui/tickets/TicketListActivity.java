package com.example.car_wash.ui.tickets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Ticket;

public class TicketListActivity extends AppCompatActivity implements TicketAdapter.OnTicketDeleteListener {

    private TicketListViewModel viewModel;
    private TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        Button btnAddTicket = findViewById(R.id.btnAddTicket);
        btnAddTicket.setOnClickListener(v -> startActivity(new Intent(this, AddTicketActivity.class)));

        // Setup RecyclerView
        RecyclerView recycler = findViewById(R.id.recyclerTickets);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketAdapter(this); // Pass listener
        recycler.setAdapter(ticketAdapter);

        viewModel = new ViewModelProvider(this).get(TicketListViewModel.class);

        viewModel.getTickets().observe(this, tickets -> {
            if (tickets != null) {
                ticketAdapter.setTickets(tickets);
            }
        });

        viewModel.getError().observe(this, err ->
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadTickets();
    }

    @Override
    public void onTicketDelete(Ticket ticket) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Ticket")
                .setMessage("Are you sure you want to delete this ticket?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteTicket(ticket.getId()).observe(this, success -> {
                        if (success) {
                            Toast.makeText(this, "Ticket deleted successfully", Toast.LENGTH_SHORT).show();
                            viewModel.loadTickets(); // Refresh the list
                        } else {
                            Toast.makeText(this, "Failed to delete ticket", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
