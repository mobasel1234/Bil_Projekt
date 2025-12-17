package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.ReturnInspection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JdbcReturnInspectionRepository implements ReturnInspectionRepository {
    private final JdbcTemplate jdbc;

    public JdbcReturnInspectionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int create(ReturnInspection i) {
        String sql = """
                INSERT INTO ReturnInspection (car_id, rental_id, return_date, km_returned, report_source, notes)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, i.getCarId());
            ps.setInt(2, i.getRentalId());
            ps.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            ps.setObject(4, null);
            ps.setString(5, "Bilabonnement");
            ps.setString(6, "");
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
}
