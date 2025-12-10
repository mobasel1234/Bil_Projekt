package com.example.bil_projekt.Controller;

import com.example.bil_projekt.Service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // Manuel trigger til alarm-check
    @GetMapping("/alarm/damage-check")
    @ResponseBody
    public String runDamageAlarmCheck() {
        alarmService.checkDamageAlarms();
        return "Alarm-scan gennemført!";
    }
}
