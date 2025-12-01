package com.example.bil_projekt.Repository;

import Deniz.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public Car findByReg(String registration) {
        String sql = "SELECT * FROM car WHERE registration_number = ?";
        return jdbc.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Car.class),
                registration
        );
    }

    public void updateStatus(int carId, String status) {
        String sql = "UPDATE car SET status = ? WHERE car_id = ?";
        jdbc.update(sql, status, carId);
    }

    public List<Car> findAllCars() {
        String sql = "SELECT * FROM car";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Car.class));
    }
}
