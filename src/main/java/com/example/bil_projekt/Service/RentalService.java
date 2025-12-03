package com.example.bil_projekt.Service;

import com.example.bil_projekt.CustomerInfo.Customer;
import com.example.bil_projekt.CarBuisness.Car;
import com.example.bil_projekt.CarBuisness.RentalAgreement;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Repository.RentalRepository;
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

    // Setter til tests
    public void setCustomerRepo(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public void setCarRepo(CarRepository repo) {
        this.carRepo = repo;
    }

    public void setRentalRepo(RentalRepository repo) {
        this.rentalRepo = repo;
    }



    public void createRental(String regNum, int customerId, String startDateStr,
                             String endDateStr, boolean firstPayment, String pickup) {

        // Finder vi bil
        Car car = getCarByReg(regNum);

        // tjekker om bil er ledig
        ensureCarIsAvailable(car);

        // Validér kunde
        validateCustomer(customerId);

        // notere datoer
        LocalDate start = LocalDate.parse(startDateStr);
        LocalDate end = (endDateStr == null || endDateStr.isBlank())
                ? null
                : LocalDate.parse(endDateStr);

        // Opret rental objekt
        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(customerId);
        r.setStart_date(start);
        r.setEnd_date(end);
        r.setFirst_payment_paid(firstPayment);
        r.setPickup_location(pickup);

        // Gem i Databasen
        rentalRepo.createRental(r);

        // Opdater bilens status
        carRepo.updateStatus(car.getCar_id(), "Udlejet");
    }


    private Car getCarByReg(String reg) {
        try {
            return carRepo.findByReg(reg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Bil ikke fundet");
        }
    }

    public void ensureCarIsAvailable(Car car) {
        if (car.getStatus().equals("Udlejet")) {
            throw new IllegalStateException("Bilen er allerede udlejet");
        }
    }

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
