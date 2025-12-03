package com.example.bil_projekt.CarBuisness;

import java.time.LocalDate;


public class RentalAgreement {

    private int rental_id;
    private int car_id;
    private int customer_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private boolean first_payment_paid;
    private String pickup_location;

    public RentalAgreement() {}

    public RentalAgreement(int rental_id, int car_id, int customer_id,
                           LocalDate start_date, LocalDate end_date,
                           boolean first_payment_paid, String pickup_location) {
        this.rental_id = rental_id;
        this.car_id = car_id;
        this.customer_id = customer_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.first_payment_paid = first_payment_paid;
        this.pickup_location = pickup_location;
    }

    public int getRental_id() { return rental_id; }
    public void setRental_id(int rental_id) { this.rental_id = rental_id; }

    public int getCar_id() { return car_id; }
    public void setCar_id(int car_id) { this.car_id = car_id; }

    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public LocalDate getStart_date() { return start_date; }
    public void setStart_date(LocalDate start_date) { this.start_date = start_date; }

    public LocalDate getEnd_date() { return end_date; }
    public void setEnd_date(LocalDate end_date) { this.end_date = end_date; }

    public boolean isFirst_payment_paid() { return first_payment_paid; }
    public void setFirst_payment_paid(boolean first_payment_paid) { this.first_payment_paid = first_payment_paid; }

    public String getPickup_location() { return pickup_location; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }
}

