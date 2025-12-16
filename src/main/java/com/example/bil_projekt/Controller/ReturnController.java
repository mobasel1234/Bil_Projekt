package com.example.bil_projekt.Controller;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.model.RentalAgreement;
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

    // gør at /return også virker
    @GetMapping("/return")
    public String directReturnPage() {
        return "returnView";
    }

    // vis formular
    @GetMapping("/register-return")
    public String showReturnForm() {
        return "returnView";
    }

    // håndter returnering
    @PostMapping("/register-return")
    public String registerReturn(
            @RequestParam String steelNumber,
            @RequestParam String return_date,
            Model model
    ) {
        try {
            // find bil
            Car car = carRepo.findByReg(steelNumber);
            if (car == null) {
                model.addAttribute("message", "Bil blev ikke fundet");
                return "returnView";
            }

            // find aktiv lejeaftale
            RentalAgreement agreement = rentalRepo.findActiveRental(car.getCar_id());
            if (agreement == null) {
                model.addAttribute("message", "Ingen aktiv lejeaftale fundet");
                return "returnView";
            }

            // parse dato
            LocalDate date = LocalDate.parse(return_date);

            // OPRET RETURN INSPECTION  ⭐ VIGTIG ⭐
            int inspectionId = rentalRepo.createReturnInspection(
                    car.getCar_id(),
                    agreement.getRental_id(),
                    date
            );

            // opdater lejeaftale slutdato
            rentalRepo.updateEndDate(agreement.getRental_id(), date);

            // videresend til skade-registrering MED inspectionId
            return "redirect:/damages/register?inspectionId=" + inspectionId;

        } catch (Exception e) {
            model.addAttribute("message", "❌ Fejl: " + e.getMessage());
            return "returnView";
        }
    }
}
