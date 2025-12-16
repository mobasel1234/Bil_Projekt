package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.DamageReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> findDamagesForCar(int carId) {

        String sql = """
        SELECT
            dr.damage_type,
            dr.description,
            dr.severity,
            dr.cost_estimate
        FROM DamageReport dr
        JOIN ReturnInspection ri ON dr.inspection_id = ri.inspection_id
        WHERE ri.car_id = ?
    """;

        return jdbc.queryForList(sql, carId);
    }




    public Map<String, Object> findByInspectionId(int inspectionId) {

        String sql = """
        SELECT
            damage_id,
            description,
            severity,
            cost_estimate,
            damage_type
        FROM DamageReport
        WHERE inspection_id = ?
        LIMIT 1
    """;

        return jdbc.queryForMap(sql, inspectionId);
    }

}
