package com.example.bil_projekt.Controller;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
import com.example.bil_projekt.model.ReturnRegistration;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ReturnController {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    private ReturnRegistration returnReg = new ReturnRegistration();

    // ----------- NYT -----------
    // Dette gør at /return også virker
    @GetMapping("/return")
    public String directReturnPage() {
        return "returnView";
    }
    // ----------------------------

    // Vis formular
    @GetMapping("/register-return")
    public String showReturnForm() {
        return "returnView";
    }

    // Håndter returnering
    @PostMapping("/register-return")
    public String registerReturn(
            @RequestParam String steelNumber,
            @RequestParam String return_date,
            Model model
    ) {
        try {
            Car car = carRepo.findByReg(steelNumber);
            if (car == null) {
                model.addAttribute("message", " Bil blev ikke fundet");
                return "returnView";
            }

            RentalAgreement agreement = rentalRepo.findActiveRental(car.getCar_id());
            if (agreement == null) {
                model.addAttribute("message", " Ingen aktiv lejeaftale fundet");
                return "returnView";
            }

            LocalDate date = LocalDate.parse(return_date);

            returnReg.registerReturn(car, agreement, date);

            rentalRepo.updateEndDate(agreement.getRental_id(), date);
            carRepo.updateStatus(car.getCar_id(), car.getStatus());

            model.addAttribute("message", "✔ Bilen er returneret!");

        } catch (Exception e) {
            model.addAttribute("message", "❌ Fejl: " + e.getMessage());
        }

        return "returnView";
    }
}

