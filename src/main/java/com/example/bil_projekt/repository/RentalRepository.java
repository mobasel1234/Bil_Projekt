package com.example.bil_projekt.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // true = bilen er ledig
    public boolean isCarAvailable(int carId) {
        String sql = """
            SELECT COUNT(*) 
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);

        // hvis count == 0, er der ingen aktiv aftale => bilen er ledig
        return count == 0;
    }
}

