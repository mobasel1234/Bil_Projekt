package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Repository.RentalRepository;
import com.example.bil_projekt.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private RentalRepository rentalRepo;

    // Viser bilstatus, liste over alle biler der er udlejet lige nu
    @GetMapping("/carStatus")
    public String showRentedCars(Model model) {
        model.addAttribute("cars", rentalRepo.findRentedCars());
        return "carStatus";
    }

    // Bilens detaljer
    @GetMapping("/carStatus/{carId}")
    public String showCarDetails(@PathVariable int carId, Model model) {
        model.addAttribute("car", rentalRepo.findCarDetails(carId));
        return "carDetails";
    }

    // Side til at oprette ny bil
    @GetMapping("/cars/create")
    public String showCreateCarForm() {
        return "createCar";
    }

    // Håndterer POST fra opret-bil formularen
    @PostMapping("/cars/create")
    public String createCar(
            @RequestParam String steelNumber,
            @RequestParam String brand,
            @RequestParam String carModel,
            @RequestParam String status,
            Model model
    ) {
        // Gemmer bilen i databasen med den valgte status
        carRepo.addCar(steelNumber, brand, carModel, status);
        model.addAttribute("message", "Bilen blev oprettet!");
        return "createCar";
    }

    // Bruges i tests til at kunne injicere et mock-repository
    public void setCarRepository(CarRepository repo) {
        this.carRepo = repo;
    }
}
