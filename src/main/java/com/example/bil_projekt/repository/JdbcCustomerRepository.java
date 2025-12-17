package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final JdbcTemplate jdbc;

    public JdbcCustomerRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT customer_id, name, email, phone, address FROM Customer";
        return jdbc.query(sql, (rs, rowNum) ->
                new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                )
        );
    }

    @Override
    public Customer findByEmail(String email) {
        String sql = "SELECT customer_id, name, email, phone, address FROM Customer WHERE email = ?";
        return jdbc.queryForObject(sql, (rs, rowNum) ->
                new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                ), email
        );
    }


    @Override
    public int create(Customer c) {
        String sql = """
                    INSERT INTO Customer (name, email, phone, address)
                    VALUES (?, ?, ?, ?)
                """;

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            return ps;
        }, kh);

        return kh.getKey().intValue();
    }


}

