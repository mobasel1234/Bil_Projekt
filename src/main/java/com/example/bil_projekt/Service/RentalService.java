package com.example.bil_projekt.Service;

import com.example.bil_projekt.CustomerInfo.Customer;
import com.example.bil_projekt.Deniz.RentalAgreement;
import com.example.bil_projekt.Repository.CustomerRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private RentalRepository rentalRepo;

    public void setCustomerRepo(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    public void createRental(RentalAgreement ra) {

        validateCustomer(ra.getCustomer_id());

        rentalRepo.createRental(ra);
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
