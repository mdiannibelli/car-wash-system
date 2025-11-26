package com.example.car_wash.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> tickets = new ArrayList<>();
    private OnTicketClickListener listener;

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    public TicketAdapter(OnTicketClickListener listener) {
        this.listener = listener;
    }

    public void setTickets(List<Ticket> newTickets) {
        this.tickets = newTickets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.bind(ticket);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView tvCarPlate, tvDate, tvService, tvPrice;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarPlate = itemView.findViewById(R.id.tvCarPlate);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvService = itemView.findViewById(R.id.tvService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        void bind(Ticket ticket) {
            tvCarPlate.setText(ticket.getCarPlate());
            tvDate.setText(ticket.getDate());
            tvService.setText(ticket.getServiceType());
            tvPrice.setText("$" + ticket.getPrice());

            itemView.setOnClickListener(v -> listener.onTicketClick(ticket));
        }
    }
}
