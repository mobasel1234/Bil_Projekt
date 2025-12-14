package com.example.bil_projekt.Repository.car;

import com.example.bil_projekt.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car c = new Car();
        c.setCar_id(rs.getInt("car_id"));
        c.setSteelNumber(rs.getString("steelNumber"));  // ← RETTET
        c.setBrand(rs.getString("brand"));
        c.setModel(rs.getString("model"));
        c.setTrim_level(rs.getString("trim_level"));
        c.setSteel_price(rs.getDouble("steel_price"));
        c.setRegistration_tax(rs.getDouble("registration_tax"));
        c.setStatus(rs.getString("status"));
        return c;
    }
}
