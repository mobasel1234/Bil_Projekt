package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    @Autowired
    private CarService carService;

    // Eksempel: http://localhost:8080/check-car?carId=2
    @GetMapping("/check-car")
    public String checkCar(@RequestParam int carId) {

        boolean available = carService.CanCarBeBooked(carId);

        if (!available) {
            return "❌ Bilen er allerede udlejet — dobbelt-booking forhindret.";
        }

        return "✔ Bilen er ledig og kan bookes.";
    }


    @GetMapping("/check-active")
    public String CheckActive(@RequestParam int carId) {

        boolean active = carService.CarHasActiveAgreement(carId);

        if (!active) {
            return "❌ Fejl: Bilen har ingen aktiv aftale.";
        }

        return "✔ Bilen har en aktiv aftale.";
    }

}
