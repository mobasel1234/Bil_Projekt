package com.example.bil_projekt.Repository;

import Deniz.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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

    public RentalAgreement findActiveRental(int carId) {
        String sql = "SELECT * FROM rental_agreement WHERE car_id = ? AND end_date IS NULL";

        try {
            return jdbc.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(RentalAgreement.class),
                    carId
            );
        } catch (Exception e) {
            return null;
        }
    }

    public void updateEndDate(int rentalId, LocalDate endDate) {
        String sql = "UPDATE rental_agreement SET end_date = ? WHERE rental_id = ?";
        jdbc.update(sql, endDate, rentalId);
    }
}
