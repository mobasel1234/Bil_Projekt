package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DamageRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void createDamageReport(DamageReport report) {
        String sql = """
            INSERT INTO DamageReport
            (inspection_id, description, severity, cost_estimate, damage_type)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                report.getInspection_id(),
                report.getDescription(),
                report.getSeverity(),
                report.getCost_estimate(),
                report.getDamage_type()
        );
    }
    public void updateCostByInspection(int inspectionId, double price) {
        String sql = """
        UPDATE DamageReport
        SET cost_estimate = ?
        WHERE inspection_id = ?
    """;
        jdbc.update(sql, price, inspectionId);
    }


}
