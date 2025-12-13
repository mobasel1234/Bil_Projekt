package com.example.bil_projekt.Repository;


import com.example.bil_projekt.model.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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



    public List<Map<String, Object>> findActiveAgreements() {
        String sql = """
        SELECT r.rental_id,
               r.start_date,
               r.end_date,
               r.pickup_location,
               c.brand,
               c.model,
               c.steelNumber,
               cust.name AS customer_name
        FROM RentalAgreement r
        JOIN Car c ON r.car_id = c.car_id
        JOIN Customer cust ON r.customer_id = cust.customer_id
        WHERE r.end_date IS NULL OR r.end_date >= CURDATE()
    """;

        return jdbc.queryForList(sql);
    }



    public Map<String, Object> findCarDetails(int carId) {
        String sql = """
        SELECT 
            c.car_id,
            c.steelNumber,
            c.brand,
            c.model,
            c.trim_level,
            c.status,
            r.start_date,
            r.end_date,
            r.pickup_location,
            cust.name AS customer_name,
            cust.email AS customer_email,
            cust.phone AS customer_phone
        FROM Car c
        JOIN RentalAgreement r ON c.car_id = r.car_id
        JOIN Customer cust ON cust.customer_id = r.customer_id
        WHERE c.car_id = ?
    """;

        return jdbc.queryForMap(sql, carId);
    }



    public List<Map<String, Object>> findRentedCars() {
        String sql = """
        SELECT 
            c.car_id,
            c.steelNumber,
            cust.name AS customer_name
        FROM Car c
        JOIN RentalAgreement r ON c.car_id = r.car_id
        JOIN Customer cust ON cust.customer_id = r.customer_id
        WHERE c.status = 'Udlejet'
        AND (r.end_date IS NULL OR r.end_date >= CURDATE())
    """;

        return jdbc.queryForList(sql);
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





    public List<RentalAgreement> findReturnsToday() {
        String sql = """
        SELECT *
        FROM RentalAgreement
        WHERE end_date = CURDATE()
    """;

        return jdbc.query(sql, new BeanPropertyRowMapper<>(RentalAgreement.class));
    }



    public Double sumActiveRentalPrices() {
        String sql = """
        SELECT SUM(c.steel_price + c.registration_tax)
        FROM RentalAgreement r
        JOIN Car c ON r.car_id = c.car_id
        WHERE r.end_date IS NULL OR r.end_date >= CURDATE()
    """;

        Double result = jdbc.queryForObject(sql, Double.class);
        return result != null ? result : 0.0;
    }





}

