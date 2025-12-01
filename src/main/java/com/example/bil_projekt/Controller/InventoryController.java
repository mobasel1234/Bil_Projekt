package com.example.bil_projekt.Controller;

import Deniz.Car;
import com.example.bil_projekt.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private CarRepository carRepo;

    @GetMapping("/lagerstatus")
    public String showInventory(Model model) {

        List<Car> cars = carRepo.findAllCars();

        model.addAttribute("cars", cars);

        return "lagerstatus";
    }
}
