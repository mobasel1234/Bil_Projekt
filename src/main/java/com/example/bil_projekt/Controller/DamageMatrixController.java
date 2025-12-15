package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Repository.DamageMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageMatrixController {

    @Autowired
    private DamageMatrixRepository matrixRepo;

    @GetMapping("/damage-matrix")
    public String viewMatrix(Model model) {

        model.addAttribute("matrix", matrixRepo.findAll());
        return "damageMatrixView";
    }
}


