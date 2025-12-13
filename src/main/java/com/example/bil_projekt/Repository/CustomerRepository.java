package com.example.bil_projekt.Repository;

import com.example.bil_projekt.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public int createCustomer(String name, String email, String phone, String address) {
        String sql = "INSERT INTO Customer (name, email, phone, address) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, name, email, phone, address);
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }


    public boolean exists(int id) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE customer_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    // Bruges i RentalTest
    public void addCustomer(int id, String name, String email, String phone, String address) {
        String sql = "INSERT INTO Customer (customer_id, name, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, id, name, email, phone, address);
    }
    
    public List<Customer> findAll() {
        String sql = "SELECT * FROM Customer";
        return jdbc.query(sql, (rs, rowNum) -> {
            Customer c = new Customer();
            c.setCustomer_id(rs.getInt("customer_id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            c.setAddress(rs.getString("address"));
            return c;
        });
    }


}

