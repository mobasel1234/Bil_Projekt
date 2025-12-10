package com.example.bil_projekt.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlarmRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Find biler i status "Skadet" i mere end 3 dage
    public List<Integer> findCarsInDamageOver3Days() {
        String sql = """
            SELECT c.car_id
            FROM Car c
            JOIN InventoryEvent i ON c.car_id = i.car_id
            WHERE c.status = 'Skadet'
              AND i.status_change = 'Skadet'
              AND DATEDIFF(CURDATE(), i.changed_at) > 3
        """;

        return jdbc.queryForList(sql, Integer.class);
    }

    // Opret alarm
    public void createAlarm(int carId) {
        String idSql = "SELECT IFNULL(MAX(alarm_id),0)+1 FROM Alarm";
        int newId = jdbc.queryForObject(idSql, Integer.class);

        String insertSql = """
            INSERT INTO Alarm (alarm_id, car_id, type, triggered_at, resolved)
            VALUES (?, ?, 'SKADE OVER 3 DAGE', NOW(), false)
        """;

        jdbc.update(insertSql, newId, carId);
    }
}
