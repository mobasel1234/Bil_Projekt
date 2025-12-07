package com.example.bil_projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class BilProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilProjektApplication.class, args);

        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "Politi123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("Query virker! Database OK.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


