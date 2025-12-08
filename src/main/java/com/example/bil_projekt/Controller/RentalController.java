package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Vis formular
    @GetMapping("/rental/create")
    public String showForm() {
        return "createRental";
    }

    // Opret lejeaftale
    @PostMapping("/rental/create")
    public String createRental(
            @RequestParam String registration_number,
            @RequestParam int customer_id,
            @RequestParam String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(defaultValue = "false") boolean first_payment_paid,
            @RequestParam String pickup_location,
            Model model
    ) {
        try {
            rentalService.createRental(
                    registration_number,
                    customer_id,
                    start_date,
                    end_date,
                    first_payment_paid,
                    pickup_location
            );

            model.addAttribute("message", " Lejeaftale oprettet!");

        } catch (Exception e) {
            model.addAttribute("message", " Fejl: " + e.getMessage());
        }

        return "createRental";
    }
}



