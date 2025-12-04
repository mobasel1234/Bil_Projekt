package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.DamageMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DamageMatrixRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public DamageMatrix findByType(String type) {
        String sql = "SELECT * FROM DamageMatrix WHERE damage_type = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(DamageMatrix.class), type);
    }
}

