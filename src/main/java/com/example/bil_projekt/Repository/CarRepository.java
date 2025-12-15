package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public Car findByReg(String steelNumber) {
        String sql = "SELECT * FROM Car WHERE steelNumber = ?";
        return jdbc.queryForObject(sql, new CarRowMapper(), steelNumber);
    }

    // Henter kun biler med status 'Ledig' til brug i lageroversigten
    public List<Car> findAvailableCars() {
        String sql = "SELECT * FROM Car WHERE status = 'Ledig'";
        return jdbc.query(sql, new CarRowMapper());
    }


    public List<Car> findAll() {
        String sql = "SELECT * FROM Car";
        return jdbc.query(sql, new CarRowMapper());
    }




    public void updateStatus(int carId, String status) {
        String sql = "UPDATE Car SET status = ? WHERE car_id = ?";
        jdbc.update(sql, status, carId);
    }




    public void addCar(String steelNumber, String brand, String model, String status) {
        String sql = "INSERT INTO Car (steelNumber, brand, model, status) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, steelNumber, brand, model, status);
    }




    //  KPI: antal udlejede biler
    public int countRentedCars() {
        String sql = "SELECT COUNT(*) FROM Car WHERE status = 'Udlejet'";
        Integer count = jdbc.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }



    //  Find car_id ud fra et ReturnInspection-id (bruges i DamageService)
    public int findCarIdByInspectionId(int inspectionId) {
        String sql = "SELECT car_id FROM ReturnInspection WHERE inspection_id = ?";
        Integer id = jdbc.queryForObject(sql, Integer.class, inspectionId);
        if (id == null) {
            throw new IllegalStateException("Ingen bil fundet for inspection_id=" + inspectionId);
        }
        return id;
    }

}




