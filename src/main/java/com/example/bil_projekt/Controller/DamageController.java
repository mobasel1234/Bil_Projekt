package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageController {

    @Autowired
    private DamageService damageService;

    // Vis formularen
    @GetMapping("/damages/register")
    public String showDamageForm(
            @RequestParam(required = false) Integer inspectionId,
            Model model
    ) {
        model.addAttribute("inspectionId", inspectionId);
        return "damageView";
    }


    // Håndter formularen
    @PostMapping("/damages/register")
    public String registerDamage(
            @RequestParam int inspectionId,
            @RequestParam String description,
            @RequestParam String damage_type,
            @RequestParam double severity,
            Model model
    ) {
        try {
            damageService.registerDamage(inspectionId, description, damage_type, severity);
            model.addAttribute("message", "Skade registreret");
        } catch (Exception e) {
            model.addAttribute("message", "Fejl: " + e.getMessage());
        }
        return "redirect:/damage-matrix?inspectionId=" + inspectionId;

    }

}


