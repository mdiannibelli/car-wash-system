package com.example.car_wash.ui.adapters;

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
    private OnCarClickListener listener;

    public interface OnCarClickListener {
        void onCarClick(Car car);
    }

    public CarAdapter(OnCarClickListener listener) {
        this.listener = listener;
    }

    public void setCars(List<Car> newCars) {
        this.cars = newCars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.bind(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tvBrand, tvModel, tvPlate, tvCarOwner;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvPlate = itemView.findViewById(R.id.tvPlate);
            tvCarOwner = itemView.findViewById(R.id.tvCarOwner);
        }

        void bind(Car car) {
            tvBrand.setText(car.getBrand());
            tvModel.setText(car.getModel());
            tvPlate.setText(car.getPlate());
            tvCarOwner.setText(car.getOwnerName());

            itemView.setOnClickListener(v -> listener.onCarClick(car));
        }
    }
}
