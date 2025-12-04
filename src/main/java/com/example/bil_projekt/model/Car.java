package com.example.bil_projekt.model;

public class Car {

    private int car_id;
    private String steelNummer;
    private String brand;
    private String model;
    private String trim_level;
    private double steel_price;
    private double registration_tax;
    private String status;

    public Car() {}

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getSteelNummer() {
        return steelNummer;
    }

    public void setSteelNummer(String steelNummer) {
        this.steelNummer = steelNummer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrim_level() {
        return trim_level;
    }

    public void setTrim_level(String trim_level) {
        this.trim_level = trim_level;
    }

    public double getSteel_price() {
        return steel_price;
    }

    public void setSteel_price(double steel_price) {
        this.steel_price = steel_price;
    }

    public double getRegistration_tax() {
        return registration_tax;
    }

    public void setRegistration_tax(double registration_tax) {
        this.registration_tax = registration_tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
