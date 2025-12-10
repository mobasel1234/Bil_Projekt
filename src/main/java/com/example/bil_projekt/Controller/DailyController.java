package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @GetMapping("/daily/returns")
    public String showTodayReturns(Model model) {
        model.addAttribute("returns", dailyService.getReturnsToday());
        return "dailyReturns";
    }
}
