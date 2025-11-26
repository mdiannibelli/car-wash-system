package com.example.car_wash.ui.car;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;
import com.example.car_wash.data.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars = new ArrayList<>();

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car currentCar = cars.get(position);

        holder.tvCarPlate.setText(currentCar.getPlate());
        holder.tvCarBrandModel.setText(String.format("%s %s", currentCar.getBrand(), currentCar.getModel()));
        holder.tvCarColor.setText(String.format("Color: %s", currentCar.getColor()));
        
        // Safe-check for owner
        if (currentCar.getOwnerName() != null) {
            holder.tvCarOwner.setText(String.format("Owner: %s", currentCar.getOwnerName()));
        } else {
            holder.tvCarOwner.setText("Owner: Not assigned");
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCarPlate;
        private final TextView tvCarBrandModel;
        private final TextView tvCarColor;
        private final TextView tvCarOwner;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarPlate = itemView.findViewById(R.id.tvCarPlate);
            tvCarBrandModel = itemView.findViewById(R.id.tvCarBrandModel);
            tvCarColor = itemView.findViewById(R.id.tvCarColor);
            tvCarOwner = itemView.findViewById(R.id.tvCarOwner);
        }
    }
}
