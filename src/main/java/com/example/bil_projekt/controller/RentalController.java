package com.example.bil_projekt.controller;

import com.example.bil_projekt.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals/create")
    public String createForm() {
        return "rental-create";
    }

    @PostMapping("/rentals/create")
    public String create(@RequestParam String steelNumber,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam String address,
                         @RequestParam double monthlyPrice) {

        rentalService.createRental(steelNumber, name, email, phone, address, monthlyPrice);
        return "redirect:/cars";
    }

    @GetMapping("/rentals/details")
    public String rentalDetails(@RequestParam String steelNumber, Model model) {
        // Vi viser kun detaljer for en aktiv lejeaftale (JOIN på car + rental + customer).
        var details = rentalService.getRentalDetails(steelNumber);
        model.addAttribute("details", details);
        return "rental-details";
    }
}
