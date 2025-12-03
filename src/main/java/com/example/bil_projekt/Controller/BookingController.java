package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    @Autowired
    private CarService carService;

    @GetMapping("/check-car")
    public String checkCar(@RequestParam int carId) {

        boolean available = carService.canCarBeBooked(carId);

        return available
                ? "✔ Bilen er ledig og kan bookes."
                : "❌ Bilen er allerede udlejet — dobbelt-booking forhindret.";
    }

    @GetMapping("/check-active")
    public String checkActive(@RequestParam int carId) {

        boolean active = carService.carHasActiveAgreement(carId);

        return active
                ? "✔ Bilen har en aktiv aftale."
                : "❌ Fejl: Bilen har ingen aktiv aftale.";
    }
}

