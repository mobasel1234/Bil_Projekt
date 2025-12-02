package com.example.bil_projekt.Repository;

import com.example.bil_projekt.Deniz.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public Car findById(int id) {
        String sql = "SELECT * FROM Car WHERE car_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Car.class), id);
    }

    public Car findByReg(String reg) {
        String sql = "SELECT * FROM Car WHERE vin = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Car.class), reg);
    }

    public void updateStatus(int carId, String status) {
        String sql = "UPDATE Car SET status = ? WHERE car_id = ?";
        jdbc.update(sql, status, carId);
    }

    // KPI: tæller antal biler som har status Udlejet
    public int countRentedCars() {
        String sql = "SELECT COUNT(*) FROM Car WHERE status = 'Udlejet'";
        return jdbc.queryForObject(sql, Integer.class);
    }

}
