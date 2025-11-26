package com.example.car_wash.data.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

public class Car {

    private String id;
    private String plate;
    private String brand;
    private String model;
    private String color;

    // This property is for the new data structure (an object)
    private User owner;

    // This property is for backward compatibility with the old data structure (a string)
    @PropertyName("ownerName")
    private String ownerNameString;

    public Car() {}

    public Car(String id, String plate, String brand, String model, String color,
               User owner) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.owner = owner;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPlate() { return plate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getColor() { return color; }
    public User getOwner() { return owner; }

    // Getter for the old ownerName string field
    @PropertyName("ownerName")
    public String getOwnerNameString() { return ownerNameString; }

    @PropertyName("ownerName")
    public void setOwnerNameString(String ownerNameString) { this.ownerNameString = ownerNameString; }

    // This is the main getter the UI will use. It's now excluded from Firebase mapping.
    @Exclude
    public String getOwnerName() {
        if (owner != null && owner.getName() != null) {
            return owner.getName();
        }
        if (ownerNameString != null && !ownerNameString.isEmpty()) {
            return ownerNameString;
        }
        return "Not Assigned";
    }

    // This method is also excluded to be safe.
    @Exclude
    public String getOwnerPhone() {
        if (owner != null && owner.getPhone() != null) {
            return owner.getPhone();
        }
        return "N/A";
    }
}
