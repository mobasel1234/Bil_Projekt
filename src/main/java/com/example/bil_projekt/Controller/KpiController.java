package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.KpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KpiController {

    @Autowired
    private KpiService kpiService;

    // Endpoint til KPI: antal udlejede biler
    @GetMapping("/kpi/rented")
    @ResponseBody
    public int getRentedCarsKpi() {

        return kpiService.getRentedCarsCount();
    }

    @GetMapping("/kpi/activePrice")
    @ResponseBody
    public String getActivePrice() {
        double total = kpiService.getActiveRentalTotalPrice();
        return "Samlet pris på aktive aftaler: " + total + " kr";
    }

}
