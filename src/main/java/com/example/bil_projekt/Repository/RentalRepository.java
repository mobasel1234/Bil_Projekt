package com.example.bil_projekt.Repository;

import Deniz.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void createRental(RentalAgreement r) {
        String sql = """
        INSERT INTO rental_agreement
        (car_id, customer_id, start_date, end_date, first_payment_paid, pickup_location)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                r.getCar_id(),
                r.getCustomer_id(),
                r.getStart_date(),
                r.getEnd_date(),
                r.isFirst_payment_paid(),
                r.getPickup_location()
        );
    }
}
