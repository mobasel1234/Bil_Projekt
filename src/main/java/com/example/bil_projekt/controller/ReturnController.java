package com.example.bil_projekt.controller;

import com.example.bil_projekt.repository.CarRepository;
import com.example.bil_projekt.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @Autowired
    private CarRepository carRepo;

    @GetMapping("/returns/create")
    public String form(Model model) {
        // Vi viser bil-listen så brugeren kan finde stelnummer i oversigten.
        model.addAttribute("cars", carRepo.findAll());
        return "return-create";
    }

    @PostMapping("/returns/create")
    public String create(@RequestParam String steelNumber) {
        int inspectionId = returnService.registerReturn(steelNumber);

        // Når bilen er returneret, sendes brugeren direkte videre til skadeoprettelse.
        return "redirect:/damages/create?inspectionId=" + inspectionId;
    }
}
