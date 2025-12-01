package Deniz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {

    @Autowired
    public JdbcTemplate jdbc;

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



