package com.example.bil_projekt.controller;

import com.example.bil_projekt.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {

    // Controller-laget modtager HTTP-requests og sender data til views.
    // Forretningslogik ligger i services.
    @Autowired
    private CarRepository carRepository;

    @GetMapping({"/", "/cars"})
    public String cars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars";
    }
}
