package com.example.bil_projekt;

import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Service.KpiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KpiTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private KpiService kpiService;

    /**
     * KPI 1:
     * Tester antal udlejede biler
     */
    @Test
    void testCountRentedCars() {
        int rentedCars = carRepository.countRentedCars();
        System.out.println("Antal udlejede biler: " + rentedCars);

        // Kan aldrig være negativt
        assertTrue(rentedCars >= 0);
    }

    /**
     * KPI 2:
     * Tester samlet pris på aktive lejeaftaler
     */
    @Test
    void testSumActiveRentalPrices() {
        double totalPrice = kpiService.getActiveRentalTotalPrice();
        System.out.println("Samlet pris aktive aftaler: " + totalPrice);

        // Kan aldrig være negativt
        assertTrue(totalPrice >= 0);
    }

    /**
     * Ekstra sanity-check:
     * Tester at KPI-service returnerer noget fornuftigt
     */
    @Test
    void testKpiServiceActiveRentalPrice() {
        double result = kpiService.getActiveRentalTotalPrice();
        System.out.println("KPI-service: Samlet aktiv pris = " + result);

        assertTrue(result >= 0);
    }
}
