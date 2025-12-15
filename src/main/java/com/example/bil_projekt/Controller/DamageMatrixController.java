package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Repository.DamageMatrixRepository;
import com.example.bil_projekt.Service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageMatrixController {

    @Autowired
    private DamageMatrixRepository matrixRepo;

    @Autowired
    private DamageService damageService;


    @GetMapping("/damage-matrix")
    public String viewMatrix(
            @RequestParam int inspectionId,
            Model model
    ) {
        model.addAttribute("inspectionId", inspectionId);
        model.addAttribute("matrix", matrixRepo.findAll());
        return "damageMatrixView";
    }

    @PostMapping("/damage-matrix/apply")
    public String applyMatrix(
            @RequestParam int inspectionId,
            @RequestParam int matrixId
    ) {
        damageService.applyDamageMatrix(inspectionId, matrixId);
        return "redirect:/lagerstatus";
    }

}


