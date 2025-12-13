package com.example.bil_projekt.Controller;

import com.example.bil_projekt.model.Car;
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

    // Viser lagerstatus: kun biler der er ledige til udlejning
    @GetMapping("/lagerstatus")
    public String showInventory(Model model) {

        // Her vises kun 'Ledig'
        List<Car> cars = carRepo.findAvailableCars();
        model.addAttribute("cars", cars);
        return "lagerstatus";
    }
}
