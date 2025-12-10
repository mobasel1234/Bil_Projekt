package com.example.bil_projekt.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DeliveryRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Map<String, Object>> findCarsForDelivery() {

        String sql = """
        SELECT c.car_id, c.steelNumber, cu.name AS customerName, r.start_date
        FROM RentalAgreement r
        JOIN Car c ON r.car_id = c.car_id
        JOIN Customer cu ON r.customer_id = cu.customer_id
        WHERE CURDATE() BETWEEN r.start_date AND r.end_date
        """;

        return jdbc.queryForList(sql);
    }

}
