package com.example.bil_projekt.model;


public class RentalDetailsDTO {

    private String steelNumber;
    private String brand;
    private String model;

    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private double monthlyPrice;

    // getters & setters

    public String getSteelNumber() {
        return steelNumber;
    }

    public void setSteelNumber(String steelNumber) {
        this.steelNumber = steelNumber;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
}
