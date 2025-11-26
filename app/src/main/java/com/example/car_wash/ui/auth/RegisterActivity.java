package com.example.car_wash.ui.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.car_wash.R;

public class RegisterActivity extends AppCompatActivity {

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        EditText name = findViewById(R.id.inputName);
        EditText phone = findViewById(R.id.inputPhone);
        EditText email = findViewById(R.id.inputEmail);
        EditText password = findViewById(R.id.inputPassword);
        Button registerBtn = findViewById(R.id.btnRegister);

        registerBtn.setOnClickListener(v ->
                viewModel.register(
                        name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                )
        );

        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getError().observe(this, err ->
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show());
    }
}
