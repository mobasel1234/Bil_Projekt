package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerRepo.findAll());
        return "customerList";
    }
}


