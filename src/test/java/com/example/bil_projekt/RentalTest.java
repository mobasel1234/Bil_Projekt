package com.example.bil_projekt;

import com.example.bil_projekt.model.RentalAgreement;
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

        // ---------------------------------------------
        // 1. Find bil (skal være Ledig)
        // ---------------------------------------------
        var car = carRepo.findById(1);   // car_id = 1 fra dine inserts

        System.out.println("Fundet bil: " + car.getCar_id() +
                " Status: " + car.getStatus());

        if (car.getStatus().equals("Udlejet")) {
            System.out.println("❌ Bilen er allerede udlejet");
            return;
        }

        // ---------------------------------------------
        // 2. Lav en RentalAgreement (NY DATABASE = ID KRÆVES)
        // ---------------------------------------------
        RentalAgreement r = new RentalAgreement();
        r.setRental_id(100); // DU SKAL SELV SÆTTE ID NU
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(500); // test customer
        r.setStart_date(LocalDate.now());
        r.setEnd_date(LocalDate.now().plusMonths(3)); // kan IKKE være null
        r.setFirst_payment_paid(true);
        r.setPickup_location("Aalborg");

        // ---------------------------------------------
        // 3. Gem aftalen
        // ---------------------------------------------
        rentalRepo.createRental(r);
        System.out.println("✔ Aftale oprettet i RentalAgreement");

        // ---------------------------------------------
        // 4. Opdater bil status
        // ---------------------------------------------
        carRepo.updateStatus(car.getCar_id(), "Udlejet");
        System.out.println("✔ Bil sat til Udlejet");

        System.out.println("===== UC1 TEST FÆRDIG =====");
    }
}


