package com.example.bil_projekt.Controller;

import Deniz.Car;
import Deniz.RentalAgreement;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class RentalController {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

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
        Car car;
        try {
            car = carRepo.findByReg(registration_number);
        } catch (Exception e) {
            model.addAttribute("message", "Fejl: Bil ikke fundet");
            return "createRental";
        }

        if (car.getStatus().equals("Udlejet")) {
            model.addAttribute("message", "Fejl: Bilen er allerede udlejet");
            return "createRental";
        }

        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(customer_id);
        r.setStart_date(LocalDate.parse(start_date));
        r.setEnd_date(end_date == null || end_date.isBlank() ? null : LocalDate.parse(end_date));
        r.setFirst_payment_paid(first_payment_paid);
        r.setPickup_location(pickup_location);

        rentalRepo.createRental(r);

        carRepo.updateStatus(car.getCar_id(), "Udlejet");

        model.addAttribute("message", "Lejeaftale oprettet succesfuldt!");

        return "createRental";
    }
}
