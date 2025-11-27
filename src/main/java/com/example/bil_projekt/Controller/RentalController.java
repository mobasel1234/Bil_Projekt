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

    // VIS FORMULAR
    @GetMapping("/rental/create")
    public String showForm() {
        return "createRental";
    }

    // HÅNDTER POST REQUEST
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
        // 1. Henter bilen
        Car car;
        try {
            car = carRepo.findByReg(registration_number);
        } catch (Exception e) {
            model.addAttribute("message", "Fejl: Bil ikke fundet");
            return "createRental";
        }

        // 2. Se om bilen er ledig
        if (car.getStatus().equals("Udlejet")) {
            model.addAttribute("message", "Fejl: Bilen er allerede udlejet");
            return "createRental";
        }

        // 3. Opretter lejeaftale
        RentalAgreement r = new RentalAgreement();
        r.setCar_id(car.getCar_id());
        r.setCustomer_id(customer_id);
        r.setStart_date(LocalDate.parse(start_date));
        r.setEnd_date(end_date == null || end_date.isBlank() ? null : LocalDate.parse(end_date));
        r.setFirst_payment_paid(first_payment_paid);
        r.setPickup_location(pickup_location);

        // 4. Gemmer vores nye aftale
        rentalRepo.createRental(r);



        // 5. Bilen bliver updateret, iforhold til status
        carRepo.updateStatus(car.getCar_id(), "Udlejet");



        // 6. Meddelse bliver givet at aftalene er fuldført
        model.addAttribute("message", "Lejeaftale oprettet succesfuldt!");

        return "createRental";
    }
}

