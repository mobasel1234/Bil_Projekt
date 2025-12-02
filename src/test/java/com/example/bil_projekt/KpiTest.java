package com.example.bil_projekt;

import com.example.bil_projekt.Repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KpiTest {

    @Autowired
    private CarRepository carRepo;

    @Test
    void testCountRentedCars() {
        int result = carRepo.countRentedCars();
        System.out.println("Antal udlejede biler: " + result);
        assertTrue(result >= 0); // tallet kan ikke være negativt
    }
}
