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

    @GetMapping("/rental/create")
    public String showForm() {
        return "createRental";
    }

    @PostMapping("/rental/create")
    public String createRental(
            @RequestParam String registration_number,
            @RequestParam int customer_id,
            @RequestParam String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam boolean first_payment_paid,
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

            model.addAttribute("message", "✔ Lejeaftale oprettet succesfuldt!");

        } catch (Exception e) {
            model.addAttribute("message", "❌ " + e.getMessage());
        }

        return "createRental";
    }
}


