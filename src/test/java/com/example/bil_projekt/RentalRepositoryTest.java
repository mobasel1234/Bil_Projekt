package com.example.bil_projekt;

import com.example.bil_projekt.Repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalRepositoryTest {

    @Test
    void testCarHasActiveAgreement() {
        // Mock database
        JdbcTemplate mockJdbc = Mockito.mock(JdbcTemplate.class);

        // Mock resultat: bilen HAR en aktiv aftale (count = 1)
        Mockito.when(mockJdbc.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class),
                Mockito.anyInt()
        )).thenReturn(1);

        RentalRepository repo = new RentalRepository();
        repo.jdbc = mockJdbc; // injicer mock

        boolean result = repo.HasActiveAgreement(5);

        assertTrue(result);   // happy flow
    }

    @Test
    void testDatabaseThrowsException() {
        JdbcTemplate mockJdbc = Mockito.mock(JdbcTemplate.class);

        // Når database kaldes → kast exception
        Mockito.when(mockJdbc.queryForObject(
                Mockito.anyString(),
                Mockito.eq(Integer.class),
                Mockito.anyInt()
        )).thenThrow(new RuntimeException("DB error"));

        RentalRepository repo = new RentalRepository();
        repo.jdbc = mockJdbc;

        assertThrows(RuntimeException.class, () -> {
            repo.HasActiveAgreement(5);
        });
    }

}
