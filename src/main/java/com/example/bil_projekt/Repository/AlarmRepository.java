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

    // =====================================
    // 🚨 ALARM 1: Bil skadet i over 3 dage
    // =====================================
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

    // =====================================
    // 🚨 ALARM 2: DS3 under 5 biler
    // =====================================
    public int countDS3Cars() {
        String sql = """
            SELECT COUNT(*)
            FROM Car
            WHERE model = 'DS3'
              AND status = 'Klar'
        """;

        return jdbc.queryForObject(sql, Integer.class);
    }

    // =====================================
    // 🔍 Find en RIGTIG DS3-bil (vigtigt!)
    // =====================================
    public Integer findAnyDS3CarId() {
        String sql = """
            SELECT car_id
            FROM Car
            WHERE model = 'DS3'
            LIMIT 1
        """;

        List<Integer> ids = jdbc.queryForList(sql, Integer.class);
        return ids.isEmpty() ? null : ids.get(0);
    }

    // =====================================
    // ❌ Undgå dublet-alarmer
    // =====================================
    public boolean alarmExists(int carId, String type) {
        String sql = """
            SELECT COUNT(*)
            FROM Alarm
            WHERE car_id = ? AND type = ? AND resolved = false
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId, type);
        return count != null && count > 0;
    }

    // =====================================
    // ➕ Opret alarm
    // =====================================
    public void createAlarm(int carId, String type) {

        if (alarmExists(carId, type)) {
            return; // undgå dublet
        }

        String insertSql = """
            INSERT INTO Alarm (car_id, type, triggered_at, resolved)
            VALUES (?, ?, NOW(), false)
        """;

        jdbc.update(insertSql, carId, type);
    }

    // =====================================
    // 🚨 DS3-alarm (KORREKT VERSION)
    // =====================================
    public void createDS3Alarm() {

        Integer carId = findAnyDS3CarId();

        if (carId == null) {
            // Ingen DS3 biler → ingen alarm
            return;
        }

        createAlarm(carId, "DS3 UNDER 5");
    }

    // =====================================
    // 📋 Hent aktive alarmer
    // =====================================
    public List<Map<String, Object>> getAllActiveAlarms() {
        String sql = "SELECT * FROM Alarm WHERE resolved = false";
        return jdbc.queryForList(sql);
    }
}
