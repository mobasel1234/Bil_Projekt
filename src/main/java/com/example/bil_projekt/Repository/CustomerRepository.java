package com.example.bil_projekt.Repository;

import com.example.bil_projekt.CustomerInfo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public Customer findCustomerById(int id) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Customer c = new Customer();
            c.setCustomer_id(rs.getInt("customer_id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            c.setAddress(rs.getString("address"));
            return c;
        });
    }

    public boolean exists(int id) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE customer_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
