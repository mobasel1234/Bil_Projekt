package com.example.bil_projekt.Service;

import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Repository.InventoryEventRepository;
import com.example.bil_projekt.Repository.RentalRepository;
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



    public void createRental(
            String regNum,
            String name,
            String email,
            String phone,
            String address,
            String startDateStr,
            String endDateStr,
            boolean firstPayment,
            String pickup
    ) {
        // 1️⃣ Find bil
        Car car = carRepo.findByReg(regNum);
        if (car == null) {
            throw new IllegalArgumentException("Bilen findes ikke");
        }

        if (car.getStatus().equals("Udlejet")) {
            throw new IllegalStateException("Bilen er allerede udlejet");
        }

        // 2️⃣ Validér kunde-input (IKKE database)
        validateCustomerInput(name, email, phone);

        // 3️⃣ Opret kunde og få ID
        int customerId = customerRepo.createCustomer(
                name, email, phone, address
        );

        // 4️⃣ Parse datoer
        LocalDate start = LocalDate.parse(startDateStr);
        LocalDate end = (endDateStr == null || endDateStr.isBlank())
                ? start.plusMonths(5)
                : LocalDate.parse(endDateStr);

        // 5️⃣ Opret lejeaftale
        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(customerId);
        r.setStart_date(start);
        r.setEnd_date(end);
        r.setFirst_payment_paid(firstPayment);
        r.setPickup_location(pickup);

        rentalRepo.createRental(r);

        // Opdater lagerstatus
        carRepo.updateStatus(car.getCar_id(), "Udlejet");
        inventoryRepo.addEvent(car.getCar_id(), "Udlejet");
    }

    // Input-validering (IKKE DB-tjek)
    private void validateCustomerInput(String name, String email, String phone) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Navn er påkrævet");

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("Ugyldig email");

        if (!phone.matches("\\d{8}"))
            throw new IllegalArgumentException("Telefonnummer skal være 8 cifre");
    }



    // ===== TEST SETTERS (bruges kun i unit tests) =====

    public void setCustomerRepo(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public void setCarRepo(CarRepository repo) {
        this.carRepo = repo;
    }

    public void setRentalRepo(RentalRepository repo) {
        this.rentalRepo = repo;
    }

    public void setInventoryRepo(InventoryEventRepository repo) {
        this.inventoryRepo = repo;
    }

}
