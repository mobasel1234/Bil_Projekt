package com.example.bil_projekt.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AlarmRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // ✅ Find biler der har været Skadet i over 3 dage
    public List<Integer> findCarsInDamageOver3Days() {
        String sql = """
            SELECT DISTINCT c.car_id
            FROM Car c
            JOIN InventoryEvent i ON c.car_id = i.car_id
            WHERE c.status = 'Skadet'
              AND i.status_change = 'Skadet'
              AND DATEDIFF(NOW(), i.changed_at) > 3
        """;

        return jdbc.queryForList(sql, Integer.class);
    }

    // ✅ Tæl hvor mange DS3 biler der er i status "Klar"
    public int countDS3Cars() {
        String sql = """
            SELECT COUNT(*)
            FROM Car
            WHERE model = 'DS3'
              AND status = 'Klar'
        """;

        return jdbc.queryForObject(sql, Integer.class);
    }

    // ✅ Undgå dublet-alarmer
    public boolean alarmExists(int carId, String type) {
        String sql = """
            SELECT COUNT(*)
            FROM Alarm
            WHERE car_id = ? AND type = ? AND resolved = false
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId, type);
        return count != null && count > 0;
    }

    // ✅ Opret GENEREL alarm
    public void createAlarm(int carId, String type) {

        if (alarmExists(carId, type)) {
            return; // undgå dublet
        }

        String idSql = "SELECT IFNULL(MAX(alarm_id),0)+1 FROM Alarm";
        int newId = jdbc.queryForObject(idSql, Integer.class);

        String insertSql = """
            INSERT INTO Alarm (alarm_id, car_id, type, triggered_at, resolved)
            VALUES (?, ?, ?, NOW(), false)
        """;

        jdbc.update(insertSql, newId, carId, type);
    }

    // ✅ Opret DS3 alarm
    public void createDS3Alarm() {
        createAlarm(0, "DS3 UNDER 5");
    }
    public List<Map<String, Object>> getAllActiveAlarms() {
        String sql = "SELECT * FROM Alarm WHERE resolved = false";
        return jdbc.queryForList(sql);
    }

}
