package com.example.bil_projekt.Repository;


import com.example.bil_projekt.Deniz.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class RentalRepository {

    @Autowired
    public JdbcTemplate jdbc;



        public void createRental(RentalAgreement r) {
            String sql = """
        INSERT INTO RentalAgreement 
        (rental_id, car_id, customer_id, start_date, end_date, first_payment_paid, pickup_location)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

            jdbc.update(sql,
                    r.getRental_id(),
                    r.getCar_id(),
                    r.getCustomer_id(),
                    r.getStart_date(),
                    r.getEnd_date(),
                    r.isFirst_payment_paid(),
                    r.getPickup_location()
            );
        }

    // true = bilen er ledig
    public boolean IsCarAvailable(int carId) {
        String sql = """
            SELECT COUNT(*) 
            FROM RentalAgreement
            WHERE car_id = ?
            AND CURDATE() BETWEEN start_date AND end_date
        """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);

        // hvis count == 0, er der ingen aktiv aftale => bilen er ledig
        return count == 0;
    }


    public boolean HasActiveAgreement(int carId) {

        String sql = """
        SELECT COUNT(*)
        FROM RentalAgreement
        WHERE car_id = ?
        AND CURDATE() BETWEEN start_date AND end_date
    """;

        Integer count = jdbc.queryForObject(sql, Integer.class, carId);

        return count > 0;  // true hvis der ER en aktiv aftale
    }

}



