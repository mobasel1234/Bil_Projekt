package com.example.bil_projekt.Controller;

import com.example.bil_projekt.model.Car;
import com.example.bil_projekt.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



    @Controller
    public class CarController {

        @Autowired
        private CarRepository carRepo;

        @GetMapping("/cars/status")
        public String showCarStatus(Model model) {

            List<Car> cars = carRepo.findAll();   // <-- du laver den i CarRepository

            model.addAttribute("cars", cars);

            return "carStatus"; // peger på Thymeleaf HTML siden
        }
        @GetMapping("/cars/create")
        public String showCreateCarForm() {
            return "createCar";
        }

        @PostMapping("/cars/create")
        public String createCar(
                @RequestParam String steelNumber,
                @RequestParam String brand,
                @RequestParam String carModel,
                @RequestParam String status,
                Model model
        ) {
            carRepo.addCar(steelNumber, brand, carModel, status);
            model.addAttribute("message", "Bilen blev oprettet!");
            return "createCar";
        }

        public void setCarRepository(CarRepository repo) {
            this.carRepo = repo;
        }

    }


