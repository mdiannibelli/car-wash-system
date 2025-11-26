package com.example.car_wash.data.repository;

import com.example.car_wash.data.firebase.FirebaseDataSource;
import com.example.car_wash.data.model.Role;
import com.example.car_wash.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.function.Consumer;

public class AuthRepository {

    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public AuthRepository() {
        auth = FirebaseDataSource.getAuth();
        db = FirebaseDataSource.getFirestore();
    }

    public void login(String email, String password, Consumer<User> onSuccess, Consumer<String> onError) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && auth.getCurrentUser() != null) {
                        fetchUserData(auth.getCurrentUser().getUid(), onSuccess, onError);
                    } else {
                        onError.accept(task.getException() != null ? task.getException().getMessage() : "Login failed");
                    }
                });
    }

    public void register(String name, String phone, String email, String password, Consumer<User> onSuccess, Consumer<String> onError) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && auth.getCurrentUser() != null) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        db.collection("roles").document("employee").get()
                                .addOnSuccessListener(roleDocument -> {
                                    if (roleDocument.exists()) {
                                        String roleName = roleDocument.getString("roleName");

                                        if (roleName != null && !roleName.isEmpty()) {
                                            User newUser = new User(firebaseUser.getUid(), name, email, roleName, phone);

                                            db.collection("users").document(firebaseUser.getUid())
                                                    .set(newUser)
                                                    .addOnSuccessListener(aVoid -> onSuccess.accept(newUser))
                                                    .addOnFailureListener(e -> onError.accept("Failed to save user data: " + e.getMessage()));
                                        } else {
                                            onError.accept("Role name field is missing or empty in the 'employee' role document.");
                                        }
                                    } else {
                                        onError.accept("Role 'employee' not found in the database.");
                                    }
                                })
                                .addOnFailureListener(e -> onError.accept("Failed to fetch role data: " + e.getMessage()));
                    } else {
                        onError.accept(task.getException() != null ? task.getException().getMessage() : "Registration failed");
                    }
                });
    }

    private void fetchUserData(String uid, Consumer<User> onSuccess, Consumer<String> onError) {
        db.collection("users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            User user = document.toObject(User.class);
                            onSuccess.accept(user);
                        } else {
                            onError.accept("User data not found in database.");
                        }
                    } else {
                        onError.accept("Failed to fetch user data: " + task.getException().getMessage());
                    }
                });
    }

    public void logout() {
        auth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
