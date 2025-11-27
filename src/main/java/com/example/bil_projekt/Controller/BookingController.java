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

        boolean available = carService.canCarBeBooked(carId);

        if (!available) {
            return "❌ Bilen er allerede udlejet — dobbelt-booking forhindret.";
        }

        return "✔ Bilen er ledig og kan bookes.";
    }
}
