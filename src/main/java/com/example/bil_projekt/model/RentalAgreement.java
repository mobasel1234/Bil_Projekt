package com.example.bil_projekt.model;

import java.time.LocalDate;

public class RentalAgreement {
    private int rentalId;
    private int carId;
    private int customerId;
    private LocalDate startDate;
    private LocalDate endDate; // null = aktiv
    private boolean firstPaymentPaid;
    private String pickupLocation;
    private double monthlyPrice;

    public RentalAgreement(int rentalId, int carId, int customerId, LocalDate startDate, LocalDate endDate,
                           boolean firstPaymentPaid, String pickupLocation, double monthlyPrice) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.firstPaymentPaid = firstPaymentPaid;
        this.pickupLocation = pickupLocation;
        this.monthlyPrice = monthlyPrice;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }
}
