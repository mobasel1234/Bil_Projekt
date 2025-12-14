package com.example.bil_projekt;
import com.example.bil_projekt.Repository.car.CarRepository;
import com.example.bil_projekt.Service.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class RentalTest {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private CarRepository carRepo;

    @Test
    void testCreateRental_HappyFlow() {

        // 1️⃣ Opret test-bil
        carRepo.addCar("TEST123", "Peugeot", "208", "Ledig");

        // 2️⃣ Kør createRental (kunden oprettes AUTOMATISK i service)
        assertDoesNotThrow(() -> {
            rentalService.createRental(
                    "TEST123",
                    "Test Person",
                    "test@kea.dk",
                    "12345678",
                    "Testvej 1",
                    "2025-01-01",
                    "2025-05-01",
                    true,
                    "Aalborg"
            );
        });

        // Tjek at bilstatus er opdateret
        var updatedCar = carRepo.findByReg("TEST123");
        assertEquals("Udlejet", updatedCar.getStatus());
    }
}
