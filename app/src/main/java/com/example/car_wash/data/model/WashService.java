package com.example.car_wash.data.model;

public class WashService {
    private String id;
    private String name;
    private double price;
    private int duration;

    public WashService() {}
    public WashService(String id, String name, double price, int duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }
}
