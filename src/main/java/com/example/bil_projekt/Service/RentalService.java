package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Repository.InventoryEventRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import com.example.bil_projekt.model.Customer;
import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RentalService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private InventoryEventRepository inventoryRepo;


    // ------ TEST SETTERS ------
    public void setCustomerRepo(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public void setCarRepo(CarRepository repo) {
        this.carRepo = repo;
    }

    public void setRentalRepo(RentalRepository repo) {
        this.rentalRepo = repo;
    }
    // ------ TEST SETTERS ------



    // Opretter en lejeaftale for en bil og en eksisterende kunde

    public void createRental(
            String regNum,
            int customerId,
            String startDateStr,
            String endDateStr,
            boolean firstPayment,
            String pickup
    ) {
        // Slår bilen op på stelnummer
        Car car = carRepo.findByReg(regNum);
        if (car == null)
            throw new IllegalArgumentException("Bilen findes ikke");


        // Tjekker om bilen allerede er udlejet
        if (car.getStatus().equals("Udlejet")) {
            throw new IllegalStateException("Bilen er allerede udlejet");
        }

        // Validering at kunden findes og at data er gyldig
        validateCustomer(customerId);


        // Konverterer datoer fra tekst til LocalDate
        LocalDate start = LocalDate.parse(startDateStr);
        LocalDate end = (endDateStr == null || endDateStr.isBlank())
                ? start.plusMonths(5)
                : LocalDate.parse(endDateStr);


        // Opretter et RentalAgreement-objekt klar til at blive gemt
        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(customerId);
        r.setStart_date(start);
        r.setEnd_date(end);
        r.setFirst_payment_paid(firstPayment);
        r.setPickup_location(pickup);


        // GEM I DB
        rentalRepo.createRental(r);

        // Opdater bills status
        carRepo.updateStatus(car.getCar_id(), "Udlejet");

        // Status ændring i InventoryEvent
        inventoryRepo.addEvent(car.getCar_id(), "Udlejet");

    }

    // Tjekker at kunden findes og at navn, email og telefon er gyldige
    private void validateCustomer(int customerId) {
        if (!customerRepo.exists(customerId)) {
            throw new IllegalArgumentException("Kunde findes ikke");
        }

        Customer c = customerRepo.findCustomerById(customerId);

        if (c.getName() == null || c.getName().isEmpty())
            throw new IllegalArgumentException("Navn er påkrævet");

        if (!c.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("Ugyldig email");

        if (!c.getPhone().matches("\\d{8}"))
            throw new IllegalArgumentException("Telefonnummer skal være 8 cifre");
    }
}

