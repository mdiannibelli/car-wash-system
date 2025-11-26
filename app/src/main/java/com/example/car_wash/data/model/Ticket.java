package com.example.car_wash.data.model;

import com.google.firebase.firestore.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ticket {

    private String id;
    private String carId;
    private String serviceId;
    private String employeeId;
    private Date timestamp;
    private double finalPrice;
    private String status;

    private Car car;
    private WashService washService;

    public Ticket() {}

    public Ticket(String id, String carId, String serviceId, String employeeId,
                  Date timestamp, double finalPrice, String status) {
        this.id = id;
        this.carId = carId;
        this.serviceId = serviceId;
        this.employeeId = employeeId;
        this.timestamp = timestamp;
        this.finalPrice = finalPrice;
        this.status = status;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }


    public String getDate() {
        if (timestamp == null) {
            return "N/A";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(timestamp);
    }


    @Exclude
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    @Exclude
    public WashService getWashService() { return washService; }
    public void setWashService(WashService washService) { this.washService = washService; }


    @Exclude
    public String getCarPlate() {
        return (car != null) ? car.getPlate() : "N/A";
    }

    @Exclude
    public String getServiceType() {
        return (washService != null) ? washService.getName() : "N/A";
    }

    @Exclude
    public double getPrice() {
        return finalPrice;
    }
}
