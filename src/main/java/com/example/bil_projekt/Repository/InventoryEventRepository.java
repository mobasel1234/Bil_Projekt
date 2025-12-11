package com.example.bil_projekt.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryEventRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void addEvent(int carId, String statusChange) {

        String sqlId = "SELECT IFNULL(MAX(event_id), 0) + 1 FROM InventoryEvent";
        int newId = jdbc.queryForObject(sqlId, Integer.class);

        String sql = """
            INSERT INTO InventoryEvent (event_id, car_id, status_change, changed_at)
            VALUES (?, ?, ?, NOW())
        """;

        jdbc.update(sql, newId, carId, statusChange);
    }
}
