package com.example.car_wash.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.car_wash.MainActivity;
import com.example.car_wash.R;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        Button loginBtn = findViewById(R.id.btnLogin);
        Button goToRegister = findViewById(R.id.btnGoRegister);

        loginBtn.setOnClickListener(v -> {
            viewModel.login(
                    email.getText().toString(),
                    password.getText().toString()
            );
        });

        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        viewModel.getError().observe(this, err ->
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
        );
    }
}
