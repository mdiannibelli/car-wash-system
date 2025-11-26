package com.example.car_wash.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_wash.R;

public class UserListActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        RecyclerView rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        rvUsers.setAdapter(userAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, users -> {
            if (users != null) {
                userAdapter.setUsers(users);
            }
        });
    }
}
