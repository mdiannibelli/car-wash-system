package com.example.car_wash.ui.tickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> tickets = new ArrayList<>();
    private OnTicketDeleteListener deleteListener;

    public interface OnTicketDeleteListener {
        void onTicketDelete(Ticket ticket);
    }

    public TicketAdapter(OnTicketDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket currentTicket = tickets.get(position);
        holder.bind(currentTicket, deleteListener);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTicketTitle;
        private final TextView tvTicketDate;
        private final TextView tvTicketStatus;
        private final TextView tvTicketPrice;
        private final ImageButton btnDelete;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTicketTitle = itemView.findViewById(R.id.tvTicketTitle);
            tvTicketDate = itemView.findViewById(R.id.tvTicketDate);
            tvTicketStatus = itemView.findViewById(R.id.tvTicketStatus);
            tvTicketPrice = itemView.findViewById(R.id.tvTicketPrice);
            btnDelete = itemView.findViewById(R.id.btnDeleteTicket);
        }

        public void bind(final Ticket ticket, final OnTicketDeleteListener listener) {
            tvTicketTitle.setText(String.format("%s - %s", ticket.getCarPlate(), ticket.getServiceType()));
            tvTicketDate.setText(String.format("Date: %s", ticket.getDate()));
            tvTicketStatus.setText(ticket.getStatus());
            tvTicketPrice.setText(String.format(Locale.US, "$%.2f", ticket.getPrice()));

            btnDelete.setOnClickListener(v -> listener.onTicketDelete(ticket));
        }
    }
}
