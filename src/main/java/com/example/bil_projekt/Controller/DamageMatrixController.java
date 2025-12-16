package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Repository.DamageMatrixRepository;
import com.example.bil_projekt.Repository.DamageRepository;
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

    @Autowired
    private DamageRepository damageRepo;


    @GetMapping("/damage-matrix")
    public String viewMatrix(
            @RequestParam(required = false) Integer inspectionId,
            Model model
    ) {
        if (inspectionId == null) {
            return "redirect:/dashBoard";
        }

        model.addAttribute("inspectionId", inspectionId);
        model.addAttribute("matrix", matrixRepo.findAll());
        model.addAttribute("damage", damageRepo.findByInspectionId(inspectionId));

        return "damageMatrixView";
    }


    @PostMapping("/damage-matrix/apply")
    public String applyMatrix(
            @RequestParam(required = false) Integer inspectionId,
            @RequestParam int matrixId,
            Model model
    ) {
        if (inspectionId == null) {
            model.addAttribute("error", "InspectionId mangler – gå tilbage til returnering");
            return "damageMatrixView";
        }

        damageService.applyDamageMatrix(inspectionId, matrixId);
        return "redirect:/lagerstatus";
    }
}




