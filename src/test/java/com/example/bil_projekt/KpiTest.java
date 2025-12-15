package com.example.bil_projekt;

import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import com.example.bil_projekt.Service.KpiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KpiTest {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private KpiService kpiService;

    // --- KPI 1: Antal udlejede biler ---
    @Test
    void testCountRentedCars() {
        int result = carRepo.countRentedCars();
        System.out.println("Antal udlejede biler: " + result);
        assertTrue(result >= 0);
    }

    // --- KPI 2: Samlet pris på aktive aftaler ---
    @Test
    void testSumActiveRentalPrices() {
        double result = rentalRepo.sumActiveRentalPrices();
        System.out.println("Samlet pris aktive aftaler: " + result);

        // SUM må aldrig være negativ
        assertTrue(result >= 0);
    }

    // --- KPI 2 test via service-laget ---
    @Test
    void testKpiServiceActiveRentalPrice() {
        double result = kpiService.getActiveRentalTotalPrice();
        System.out.println("KPI-service: Samlet aktiv pris = " + result);

        assertTrue(result >= 0);
    }
}
