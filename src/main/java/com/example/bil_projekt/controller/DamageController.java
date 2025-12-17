package com.example.bil_projekt.controller;

import com.example.bil_projekt.model.DamageReport;
import com.example.bil_projekt.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DamageController {

    @Autowired
    private DamageService damageService;

    @GetMapping("/damages/create")
    public String form(@RequestParam int inspectionId, Model model) {
        model.addAttribute("inspectionId", inspectionId);
        return "damage-create";
    }

    @PostMapping("/damages/create")
    public String create(@RequestParam int inspectionId,
                         @RequestParam String description,
                         @RequestParam String severity,
                         @RequestParam double costEstimate,
                         @RequestParam String damageType) {

        // Vi gemmer én skade pr. submit i MVP'en (kan udvides senere til flere).
        DamageReport d = new DamageReport(0, inspectionId, description, severity, costEstimate, damageType);
        damageService.addDamages(inspectionId, List.of(d));

        return "redirect:/cars";
    }
}
