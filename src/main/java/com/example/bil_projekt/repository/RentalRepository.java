package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;



@Repository
public class RentalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // TEST SETTER
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void createRental(RentalAgreement r) {
        String sql = """
        INSERT INTO RentalAgreement 
        (rental_id, car_id, customer_id, start_date, end_date, first_payment_paid, pickup_location)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                r.getRental_id(),
                r.getCar_id(),
                r.getCustomer_id(),
                r.getStart_date(),
                r.getEnd_date(),
                r.isFirst_payment_paid(),
                r.getPickup_location()
        );
    }

    public boolean isCarAvailable(int carId) {
        String sql = """
            SELECT COUNT(*) 
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);
        return count == 0;
    }

    public boolean hasActiveAgreement(int carId) {
        String sql = """
            SELECT COUNT(*)
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);
        return count > 0;
    }

    public RentalAgreement findActiveRental(int carId) {
        String sql = "SELECT * FROM RentalAgreement WHERE car_id = ? AND end_date IS NULL";

        try {
            return jdbc.queryForObject(sql,
                    (rs, rowNum) -> {
                        RentalAgreement r = new RentalAgreement();
                        r.setRental_id(rs.getInt("rental_id"));
                        r.setCar_id(rs.getInt("car_id"));
                        r.setCustomer_id(rs.getInt("customer_id"));
                        r.setStart_date(rs.getDate("start_date").toLocalDate());
                        r.setEnd_date(rs.getDate("end_date") == null ? null : rs.getDate("end_date").toLocalDate());
                        r.setFirst_payment_paid(rs.getBoolean("first_payment_paid"));
                        r.setPickup_location(rs.getString("pickup_location"));
                        return r;
                    },
                    carId);

        } catch (Exception e) {
            return null;
        }
    }

    public void updateEndDate(int rentalId, LocalDate endDate) {
        String sql = "UPDATE RentalAgreement SET end_date = ? WHERE rental_id = ?";
        jdbc.update(sql, endDate, rentalId);
    }

}

