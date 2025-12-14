package com.example.bil_projekt.Controller.Rental;

import com.example.bil_projekt.Repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepo;

    @GetMapping("/deliveries")
    public String showDeliveries(Model model) {

        model.addAttribute("deliveries", deliveryRepo.findCarsForDelivery());

        return "deliveryList";
    }
}



