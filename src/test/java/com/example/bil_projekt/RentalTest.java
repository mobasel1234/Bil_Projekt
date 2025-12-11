package com.example.bil_projekt;

import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Repository.RentalRepository;
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

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Test
    void testCreateRental_HappyFlow() {

        System.out.println("===== UC1 TEST STARTER =====");

        // -----------------------------
        // 1: Opret en test-kunde i DB
        // -----------------------------
        customerRepo.addCustomer(
                500,
                "Test Person",
                "test@kea.dk",
                "12345678",
                "Testvej 1"
        );

        // -----------------------------
        // 2: Opret test-bil i DB
        // -----------------------------
        carRepo.addCar("TEST123", "Peugeot", "208", "Ledig");

        var car = carRepo.findByReg("TEST123");

        // -----------------------------
        // 3: Kør createRental()
        // -----------------------------
        assertDoesNotThrow(() -> {
            rentalService.createRental(
                    "TEST123",
                    500,
                    "2025-01-01",
                    "2025-05-01",
                    true,
                    "Aalborg"
            );
        });

        // -----------------------------
        // 4: Tjek at bilstatus er opdateret
        // -----------------------------
        var updatedCar = carRepo.findByReg("TEST123");

        assertEquals("Udlejet", updatedCar.getStatus());

        System.out.println("✔ Bil status opdateret: " + updatedCar.getStatus());
        System.out.println("===== UC1 TEST FÆRDIG =====");
    }
}
