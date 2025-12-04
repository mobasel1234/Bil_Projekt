package com.example.bil_projekt.Controller;

import com.example.bil_projekt.CarBuisness.Car;
import com.example.bil_projekt.CarBuisness.RentalAgreement;
import com.example.bil_projekt.CarBuisness.ReturnRegistration;
import com.example.bil_projekt.Repository.CarRepository;
import com.example.bil_projekt.Repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/register-return")
    public String registerReturn(
            @RequestParam String vin,
            @RequestParam String return_date,
            Model model
    ) {

        Car car = carRepo.findByReg(vin);

        RentalAgreement agreement = rentalRepo.findActiveRental(car.getCar_id());

        LocalDate date = LocalDate.parse(return_date);

        returnReg.registerReturn(car, agreement, date);

        rentalRepo.updateEndDate(agreement.getRental_id(), date);
        carRepo.updateStatus(car.getCar_id(), car.getStatus());

        model.addAttribute("message", "Bilen er returneret!");

        return "returnView";
    }
}
