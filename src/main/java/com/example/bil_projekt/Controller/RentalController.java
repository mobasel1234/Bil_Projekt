package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.RentalService;
import com.example.bil_projekt.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private CustomerRepository customerRepo;

    // Vis formular tiil at oprette ny leje aftale
    @GetMapping("/rental/create")
    public String showForm() {
        return "createRental";
    }

    // Opret lejeaftale samt automatisk oprettelse af kunden
    @PostMapping("/rental/create")
    public String createRental(
            @RequestParam String registration_number,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(defaultValue = "false") boolean first_payment_paid,
            @RequestParam String pickup_location,
            Model model
    ) {
        try {
            // Opret kunden i databasen og får ID tilbage
            int customerId = customerRepo.createCustomer(name, email, phone, address);

            // Opret selve lejeaftale med det nye ID
            rentalService.createRental(
                    registration_number,
                    name,
                    email,
                    phone,
                    address,
                    start_date,
                    end_date,
                    first_payment_paid,
                    pickup_location
            );

            model.addAttribute("message", "Lejeaftale oprettet! Kunde-ID: " + customerId);

        } catch (Exception e) {
            //viser eventuelle fejl fra validering eller database
            model.addAttribute("message", " Fejl: " + e.getMessage());
        }




        return "createRental";
    }
}
