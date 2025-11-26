package com.example.car_wash.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;
import com.example.car_wash.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> newUsers) {
        this.users = newUsers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
        }

        void bind(User user) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
        }
    }
}
