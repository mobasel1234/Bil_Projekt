package com.example.bil_projekt.repository;

import com.example.bil_projekt.model.DamageReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcDamageRepository implements DamageRepository {

    private final JdbcTemplate jdbc;

    public JdbcDamageRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(DamageReport d) {
        String sql = """
                    INSERT INTO DamageReport
                    (inspection_id, description, severity, cost_estimate, damage_type)
                    VALUES (?, ?, ?, ?, ?)
                """;

        jdbc.update(
                sql,
                d.getInspectionId(),
                d.getDescription(),
                d.getSeverity(),
                d.getCostEstimate(),
                d.getDamageType()
        );
    }

    @Override
    public List<DamageReport> findByInspectionId(int inspectionId) {
        String sql = """
                    SELECT damage_id, inspection_id, description, severity, cost_estimate, damage_type
                    FROM DamageReport
                    WHERE inspection_id = ?
                """;

        return jdbc.query(sql, (rs, rowNum) ->
                new DamageReport(
                        rs.getInt("damage_id"),
                        rs.getInt("inspection_id"),
                        rs.getString("description"),
                        rs.getString("severity"),
                        rs.getDouble("cost_estimate"),
                        rs.getString("damage_type")
                ), inspectionId
        );
    }
}
