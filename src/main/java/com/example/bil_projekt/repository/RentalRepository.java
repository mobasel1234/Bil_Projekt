package com.example.bil_projekt.Repository;

import com.example.bil_projekt.CarBuisness.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {

    @Autowired
    private JdbcTemplate jdbc;

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

        return count == 0;  // bilen er ledig hvis count = 0
    }

    public boolean hasActiveAgreement(int carId) {
        String sql = """
            SELECT COUNT(*)
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);

        return count > 0; // true hvis der er en aktiv aftale
    }
}


