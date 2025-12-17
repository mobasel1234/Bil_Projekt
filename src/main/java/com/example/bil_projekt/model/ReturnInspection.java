package com.example.bil_projekt.model;


import java.time.LocalDate;

public class ReturnInspection {
    private int inspectionId;
    private int carId;
    private int rentalId;
    private LocalDate returnDate;
    private Integer kmReturned;
    private String reportSource;
    private String notes;

    public ReturnInspection(int inspectionId, int carId, int rentalId, LocalDate returnDate,
                            Integer kmReturned, String reportSource, String notes) {
        this.inspectionId = inspectionId;
        this.carId = carId;
        this.rentalId = rentalId;
        this.returnDate = returnDate;
        this.kmReturned = kmReturned;
        this.reportSource = reportSource;
        this.notes = notes;
    }

    public int getInspectionId() {
        return inspectionId;
    }

    public int getCarId() {
        return carId;
    }

    public int getRentalId() {
        return rentalId;
    }
}

