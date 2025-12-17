package com.example.bil_projekt.service;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.Customer;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.RentalDetailsDTO;
import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.repository.CustomerRepository;
import com.example.bil_projekt.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RentalService {

    // Vi bruger Service-laget til at samle forretningslogik,
    // så controllerne holdes "tynde" og databasedetaljer bliver i repositories.
    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public int createRental(String steelNumber,
                            String name,
                            String email,
                            String phone,
                            String address,
                            double monthlyPrice) {

        // Vi finder bilen på stelnummer (primær nøgle i vores interne flow).
        Car car = carRepo.findBySteelNumber(steelNumber);
        int carId = car.getCarId();

        // Simpel inputvalidering (exception flow).
        if (monthlyPrice < 0) {
            throw new IllegalArgumentException("Månedlig pris må ikke være negativ");
        }

        // Forretningsregel: en bil skal være 'Ledig' før den kan udlejes.
        if (!"Ledig".equalsIgnoreCase(car.getStatus())) {
            throw new IllegalStateException("Bilen er ikke ledig");
        }

        // Vi genbruger kunde hvis email findes – ellers opretter vi ny kunde.
        // Det matcher use casen "opret leje (med opret-kunde)".
        int customerId;
        try {
            Customer customer = customerRepo.findByEmail(email);
            customerId = customer.getCustomerId();
        } catch (EmptyResultDataAccessException e) {
            Customer newCustomer = new Customer(0, name, email, phone, address);
            customerId = customerRepo.create(newCustomer);
        }

        // Vi opretter en aktiv leje (end_date = NULL).
        RentalAgreement rental = new RentalAgreement(
                0,
                carId,
                customerId,
                LocalDate.now(),
                null,
                false,
                "København",
                monthlyPrice
        );

        int rentalId = rentalRepo.create(rental);

        // Status ændres som en del af samme use case, så lageroversigten altid er korrekt.
        carRepo.updateStatus(carId, "Udlejet");

        return rentalId;
    }

    public RentalDetailsDTO getRentalDetails(String steelNumber) {
        // Vi bruger JOIN i repository-laget for at hente samlede detaljer i ét databasekald.
        return rentalRepo.findActiveRentalDetailsBySteelNumber(steelNumber);
    }
}
