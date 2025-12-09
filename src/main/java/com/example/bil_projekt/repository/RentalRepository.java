package com.example.bil_projekt.Repository;


import com.example.bil_projekt.model.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Bruges kun i tests til manuel injection
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Opret en ny lejeaftale
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

    // Tjek om bilen er ledig i perioden
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

    // Tjek om bilen HAR en aktiv aftale
    public boolean hasActiveAgreement(int carId) {
        String sql = """
            SELECT COUNT(*) 
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);
        return count != null && count > 0;
    }

    // Find den aktive rental for en bil (bruges til return-flow)
    public RentalAgreement findActiveRental(int carId) {
        String sql = """
            SELECT * FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
            LIMIT 1
        """;

        return jdbc.queryForObject(sql,
                new BeanPropertyRowMapper<>(RentalAgreement.class),
                carId
        );
    }

    // Opdater slutdato (bruges ved returnering)
    public void updateEndDate(int rentalId, java.time.LocalDate newEndDate) {
        String sql = """
            UPDATE RentalAgreement
            SET end_date = ?
            WHERE rental_id = ?
        """;

        jdbc.update(sql, newEndDate, rentalId);
    }

    public Double sumActiveRentalPrices() {
        String sql = "SELECT SUM(monthly_price) FROM RentalAgreement WHERE end_date IS NULL";
        Double result = jdbc.queryForObject(sql, Double.class);
        return result != null ? result : 0.0;
    }

}

