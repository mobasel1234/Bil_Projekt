package com.example.bil_projekt.model;


public class Car {
    private int carId;
    private String steelNumber;
    private String brand;
    private String model;
    private String trimLevel;
    private double steelPrice;
    private double registrationTax;
    private String status;


    public Car(int carId, String steelNumber, String brand, String model, String trimLevel,
               double steelPrice, double registrationTax, String status) {
        this.carId = carId;
        this.steelNumber = steelNumber;
        this.brand = brand;
        this.model = model;
        this.trimLevel = trimLevel;
        this.steelPrice = steelPrice;
        this.registrationTax = registrationTax;
        this.status = status;
    }

    public int getCarId() {
        return carId;
    }

    public String getSteelNumber() {
        return steelNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getTrimLevel() {
        return trimLevel;
    }

    public double getSteelPrice() {
        return steelPrice;
    }

    public double getRegistrationTax() {
        return registrationTax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

