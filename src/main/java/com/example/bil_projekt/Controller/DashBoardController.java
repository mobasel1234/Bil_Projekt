package com.example.bil_projekt.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/")
    public String home() {
        return "dashBoard"; // loader dashboardView.html
    }

    @GetMapping("/dashBoard")
    public String dashboard() {
        return "dashBoard";
    }
}

