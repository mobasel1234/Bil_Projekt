package com.example.bil_projekt.Controller;

import com.example.bil_projekt.CarBuisness.Car;
import com.example.bil_projekt.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        public void setCarRepository(CarRepository repo) {
            this.carRepo = repo;
        }

    }


