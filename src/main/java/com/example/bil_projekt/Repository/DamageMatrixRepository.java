package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.DamageMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DamageMatrixRepository {

    @Autowired
    private JdbcTemplate jdbc;


    public DamageMatrix findByType(String type) {
        String sql = "SELECT * FROM DamageMatrix WHERE damage_type = ?";

        List<DamageMatrix> result = jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(DamageMatrix.class),
                type
        );

        if (result.isEmpty()) {
            return null; // 👈 VIGTIGT
        }

        return result.get(0);
    }

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM DamageMatrix";
        return jdbc.queryForList(sql);
    }

    public DamageMatrix findById(int id) {
        String sql = "SELECT * FROM DamageMatrix WHERE matrix_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(DamageMatrix.class), id);
    }


}

