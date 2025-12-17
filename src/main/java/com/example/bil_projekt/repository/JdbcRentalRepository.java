package com.example.bil_projekt.repository;


import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.RentalDetailsDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcRentalRepository implements RentalRepository {
    private final JdbcTemplate jdbc;

    public JdbcRentalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int create(RentalAgreement r) {
        String sql = """
                INSERT INTO RentalAgreement (car_id, customer_id, start_date, end_date,
                first_payment_paid, pickup_location, monthly_price)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, r.getCarId());
            ps.setInt(2, r.getCustomerId());
            ps.setDate(3, Date.valueOf(r.getStartDate()));
            ps.setDate(4, null); // aktiv
            ps.setBoolean(5, false);
            ps.setString(6, "København"); // simpelt default, kan styres fra form
            ps.setDouble(7, r.getMonthlyPrice());
            return ps;
        }, kh);

        return kh.getKey().intValue();
    }

    @Override
    public RentalAgreement findActiveByCarId(int carId) {
        String sql = """
                SELECT rental_id, car_id, customer_id, start_date, end_date, first_payment_paid, pickup_location, monthly_price
                FROM RentalAgreement
                WHERE car_id = ? AND end_date IS NULL
                """;
        List<RentalAgreement> list = jdbc.query(sql, (rs, rowNum) ->
                new RentalAgreement(
                        rs.getInt("rental_id"),
                        rs.getInt("car_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("start_date").toLocalDate(),
                        null,
                        rs.getBoolean("first_payment_paid"),
                        rs.getString("pickup_location"),
                        rs.getDouble("monthly_price")
                ), carId
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<RentalAgreement> findActiveRentals() {
        String sql = """
                SELECT rental_id, car_id, customer_id, start_date, end_date, first_payment_paid, pickup_location, monthly_price
                FROM RentalAgreement
                WHERE end_date IS NULL
                """;
        return jdbc.query(sql, (rs, rowNum) ->
                new RentalAgreement(
                        rs.getInt("rental_id"),
                        rs.getInt("car_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("start_date").toLocalDate(),
                        null,
                        rs.getBoolean("first_payment_paid"),
                        rs.getString("pickup_location"),
                        rs.getDouble("monthly_price")
                )
        );
    }

    @Override
    public void closeRental(int rentalId) {
        jdbc.update("UPDATE RentalAgreement SET end_date=? WHERE rental_id=?",
                Date.valueOf(LocalDate.now()), rentalId);
    }

    @Override
    public RentalDetailsDTO findActiveRentalDetailsBySteelNumber(String steelNumber) {

        String sql = """
                    SELECT
                        c.steelNumber,
                        c.brand,
                        c.model,
                        cu.name AS customerName,
                        cu.email AS customerEmail,
                        cu.phone AS customerPhone,
                        r.monthly_price
                    FROM Car c
                    JOIN RentalAgreement r ON c.car_id = r.car_id
                    JOIN Customer cu ON r.customer_id = cu.customer_id
                    WHERE c.steelNumber = ?
                """;

        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            RentalDetailsDTO dto = new RentalDetailsDTO();
            dto.setSteelNumber(rs.getString("steelNumber"));
            dto.setBrand(rs.getString("brand"));
            dto.setModel(rs.getString("model"));
            dto.setCustomerName(rs.getString("customerName"));
            dto.setCustomerEmail(rs.getString("customerEmail"));
            dto.setCustomerPhone(rs.getString("customerPhone"));
            dto.setMonthlyPrice(rs.getDouble("monthly_price"));
            return dto;
        }, steelNumber);
    }


}

