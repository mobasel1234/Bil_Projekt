package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void addCar(String steelNumber, String brand, String model, String status) {
        String sql = """
        INSERT INTO Car (steelNumber, brand, model, status)
        VALUES (?, ?, ?, ?)
    """;

        jdbc.update(sql, steelNumber, brand, model, status);
    }



    public Car findById(int id) {
        String sql = "SELECT * FROM Car WHERE car_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Car.class), id);
    }
    public List<Car> findAll() {
        String sql = "SELECT * FROM Car";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Car.class));
    }


    public Car findByReg(String reg) {
        String sql = "SELECT * FROM Car WHERE steelNummer = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Car.class), reg);
    }
    public int countRentedCars() {
        String sql = "SELECT COUNT(*) FROM Car WHERE status = 'Udlejet'";
        return jdbc.queryForObject(sql, Integer.class);
    }


    public void updateStatus(int carId, String status) {
        String sql = "UPDATE Car SET status = ? WHERE car_id = ?";
        jdbc.update(sql, status, carId);
    }
    public int findCarIdByInspectionId(int inspectionId) {
        String sql = """
        SELECT car_id 
        FROM ReturnInspection
        WHERE inspection_id = ?
    """;
        return jdbc.queryForObject(sql, Integer.class, inspectionId);
    }

}
