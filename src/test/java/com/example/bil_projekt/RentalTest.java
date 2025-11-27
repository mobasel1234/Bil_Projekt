package com.example.bil_projekt;

import Deniz.RentalAgreement;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
public class RentalTest {


    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    @Test
    void testCreateRental() {

        System.out.println("===== UC1 TEST STARTER =====");

        // 1. Find bil
        var car = carRepo.findByReg("AB12345");
        System.out.println("Fundet bil: " + car.getRegistration_number() +
                " - Status: " + car.getStatus());

        // 2. Check om bil er udlejet
        if (car.getStatus().equals("Udlejet")) {
            System.out.println("❌ Bilen er allerede udlejet");
            return;
        }

        // 3. Lav en test-aftale
        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(1);
        r.setStart_date(LocalDate.now());
        r.setEnd_date(LocalDate.now().plusMonths(5));
        r.setFirst_payment_paid(true);
        r.setPickup_location("Test Lokation");

        // 4. Gem aftale
        rentalRepo.createRental(r);
        System.out.println("✔ Aftale oprettet i databasen!");

        // 5. Opdater bil status
        carRepo.updateStatus(car.getCar_id(), "Udlejet");
        System.out.println("✔ Bil sat til 'Udlejet'");

        System.out.println("===== UC1 TEST FÆRDIG =====");
    }
}

