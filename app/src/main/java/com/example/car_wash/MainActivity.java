package com.example.car_wash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.car_wash.ui.auth.LoginActivity;
import com.example.car_wash.ui.car.CarListActivity;
import com.example.car_wash.ui.tickets.TicketListActivity;
import com.example.car_wash.ui.user.UserListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCars = findViewById(R.id.btnCars);
        Button btnUsers = findViewById(R.id.btnUsers);
        Button btnTickets = findViewById(R.id.btnTickets);

        btnCars.setOnClickListener(v -> startActivity(new Intent(this, CarListActivity.class)));
        btnUsers.setOnClickListener(v -> startActivity(new Intent(this, UserListActivity.class)));
        btnTickets.setOnClickListener(v -> startActivity(new Intent(this, TicketListActivity.class)));

        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
