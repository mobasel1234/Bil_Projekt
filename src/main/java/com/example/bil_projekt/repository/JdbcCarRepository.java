package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCarRepository implements CarRepository {
    private final JdbcTemplate jdbc;

    public JdbcCarRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT car_id, steelNumber, brand, model, trim_level, steel_price, registration_tax, status FROM Car";
        return jdbc.query(sql, (rs, rowNum) ->
                new Car(
                        rs.getInt("car_id"),
                        rs.getString("steelNumber"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("trim_level"),
                        rs.getDouble("steel_price"),
                        rs.getDouble("registration_tax"),
                        rs.getString("status")
                )
        );
    }

    @Override
    public Car findById(int carId) {
        String sql = "SELECT car_id, steelNumber, brand, model, trim_level, steel_price, registration_tax, status FROM Car WHERE car_id=?";
        return jdbc.queryForObject(sql, (rs, rowNum) ->
                new Car(
                        rs.getInt("car_id"),
                        rs.getString("steelNumber"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("trim_level"),
                        rs.getDouble("steel_price"),
                        rs.getDouble("registration_tax"),
                        rs.getString("status")
                ), carId
        );
    }

    @Override
    public Car findBySteelNumber(String steelNumber) {
        String sql = """
                SELECT car_id, steelNumber, brand, model, trim_level,
                       steel_price, registration_tax, status
                FROM Car
                WHERE steelNumber = ?
                """;

        return jdbc.queryForObject(sql, (rs, rowNum) ->
                new Car(
                        rs.getInt("car_id"),
                        rs.getString("steelNumber"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("trim_level"),
                        rs.getDouble("steel_price"),
                        rs.getDouble("registration_tax"),
                        rs.getString("status")
                ), steelNumber
        );
    }


    @Override
    public void updateStatus(int carId, String status) {
        jdbc.update("UPDATE Car SET status=? WHERE car_id=?", status, carId);
    }
}

